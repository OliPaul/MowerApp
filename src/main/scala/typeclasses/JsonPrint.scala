package typeclasses

import environment.{
  Coordinate,
  Direction,
  Garden,
  Mower,
  WriteGarden,
  WriteJsonCoordinates,
  WriteJsonDirection,
  WriteJsonMower
}
import play.api.libs.json.JsValue

object JsonPrint {
  trait Show[T] {
    def show(t: T): JsValue
  }

  object Show {

    implicit val CoordinateShow = new Show[Coordinate] {
      def show(
          c: Coordinate
      ): JsValue = WriteJsonCoordinates.writes(c)
    }

    implicit val DirectionShow = new Show[Direction] {
      def show(
          d: Direction
      ): JsValue = WriteJsonDirection.writes(d)
    }

    implicit val MowerShow = new Show[Mower] {
      def show(
          mower: Mower
      ): JsValue = WriteJsonMower.writes(mower)
    }

    implicit val GardenShow = new Show[Garden] {
      def show(
          garden: Garden
      ): JsValue = WriteGarden.writes(garden)
    }
  }

  def print[T](
      t: T
  )(
      implicit ev: Show[T]
  ): JsValue = ev.show(t)
}
