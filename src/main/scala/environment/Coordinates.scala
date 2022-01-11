package environment

import play.api.libs.json.{JsObject, JsValue, Writes}

case class Coordinate(x: Int, y: Int)

object WriteJsonCoordinates extends Writes[Coordinate] {
  override def writes(value: Coordinate): JsValue = {
    JsObject(
      Map(
        "x" -> Writes.of[Int].writes(value.x),
        "y" -> Writes.of[Int].writes(value.y)
      )
    )
  }
}

object WriteCsvCoordinates {
  def writes(value: Coordinate): String = {
    value.x.toString + ";" + value.y.toString + ";"
  }
}
