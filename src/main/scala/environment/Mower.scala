package environment

import com.typesafe.scalalogging.Logger
import play.api.libs.json.Json.toJson
import play.api.libs.json._

case class Mower(
    position: Coordinate,
    direction: Direction,
    originPosition: Coordinate,
    originDirection: Direction,
    instructions: List[String]
) {

  val logger = Logger("Mower Log")

  def rotateRight(): Mower = {
    Mower(
      this.position,
      this.direction.nextFromRight(),
      originPosition,
      originDirection,
      instructions
    )
  }

  def rotateLeft(): Mower = {
    Mower(
      this.position,
      this.direction.nextFromLeft(),
      originPosition,
      originDirection,
      instructions
    )
  }

  def advance(garden: Garden): Mower = {
    val newCoord = this.direction match {
      case North => Coordinate(this.position.x, this.position.y + 1)
      case East  => Coordinate(this.position.x + 1, this.position.y)
      case South => Coordinate(this.position.x, this.position.y - 1)
      case _     => Coordinate(this.position.x - 1, this.position.y)
    }
    if (garden.isValidCoordinate(newCoord))
      Mower(
        newCoord,
        this.direction,
        originPosition,
        originDirection,
        instructions
      )
    else this
  }

  def update(action: String, garden: Garden): Mower = {
    action match {
      case "A" => this.advance(garden);
      case "D" => this.rotateRight();
      case "G" => this.rotateLeft();
      case x =>
        logger.warn("Unknown action \" " + x + " \" detected")
        this
    }
  }
}

object Mower {

  def processUpdates(
      actions: List[String],
      garden: Garden,
      accumulator: Mower
  ): Mower = {
    actions match {
      case x :: xs => processUpdates(xs, garden, accumulator.update(x, garden))
      case _       => accumulator
    }
  }

}

object WriteJsonMower extends Writes[Mower] {
  override def writes(value: Mower): JsValue =
    toJson(
      Map(
        "debut" -> toJson(
          Map(
            "point"     -> WriteJsonCoordinates.writes(value.originPosition),
            "direction" -> WriteJsonDirection.writes(value.originDirection)
          )
        ),
        "instructions" -> toJson(value.instructions),
        "fin" -> toJson(
          Map(
            "point"     -> WriteJsonCoordinates.writes(value.position),
            "direction" -> WriteJsonDirection.writes(value.direction)
          )
        )
      )
    )
}

object WriteCsvMower {
  def writes(value: Mower): String = {
    WriteCsvCoordinates.writes(value.originPosition) + WriteCsvDirection.writes(
      value.originDirection
    ) + WriteCsvCoordinates.writes(value.position) + WriteCsvDirection.writes(
      value.direction
    ) + value.instructions.mkString("")
  }
}
