package environment

import com.typesafe.scalalogging.Logger
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsValue, Json, Writes}
import typeclasses.CsvPrint

case class Garden(topRightPosition: Coordinate, mowers: List[Mower]) {

  val logger = Logger("Garden Log")

  def isValidCoordinate(coord: Coordinate): Boolean = {
    val isOccupied = this.mowers.exists(m => m.position == coord)
    if (isOccupied) {
      logger.info(
        "Collision detected on position (" + CsvPrint.print(coord) + ")"
      )
      false
    } else {
      coord.x >= 0 && coord.y >= 0 && coord.x <= this.topRightPosition.x && coord.y <= topRightPosition.y
    }
  }

  def addMower(mower: Either[String, Mower]): Garden = {
    mower match {
      case Right(m) => Garden(this.topRightPosition, m :: this.mowers)
      case Left(msg) =>
        logger.warn(msg)
        this
    }
  }
}

object WriteGarden extends Writes[Garden] {
  override def writes(value: Garden): JsValue = {
    Json.toJson(
      Map(
        "limite" -> WriteJsonCoordinates.writes(value.topRightPosition),
        "tondeuses" -> toJson(
          value.mowers.reverse.map(mower => WriteJsonMower.writes(mower))
        )
      )
    )
  }
}
