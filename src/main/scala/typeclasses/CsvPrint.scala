package typeclasses

import environment.{
  Coordinate,
  Direction,
  Garden,
  Mower,
  WriteCsvCoordinates,
  WriteCsvDirection,
  WriteCsvMower
}

object CsvPrint {

  trait Show[T] {
    def show(t: T): String
  }

  object Show {

    implicit val CoordinateShow = new Show[Coordinate] {
      def show(
          c: Coordinate
      ): String = WriteCsvCoordinates.writes(c)
    }

    implicit val DirectionShow = new Show[Direction] {
      def show(
          d: Direction
      ): String = WriteCsvDirection.writes(d)
    }

    implicit val MowerShow = new Show[Mower] {
      def show(
          mower: Mower
      ): String = WriteCsvMower.writes(mower)
    }

    implicit val GardenShow = new Show[Garden] {
      def show(
          garden: Garden
      ): String = {
        val header =
          "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions\n"
        val mowersStr =
          garden.mowers.reverse.zipWithIndex
            .map {
              case (mower, i) =>
                (i + 1).toString + ";" + CsvPrint.print(mower)
            }
            .mkString("\n")
        header + mowersStr
      }
    }
  }

  def print[T](t: T)(
      implicit ev: Show[T]
  ): String = ev.show(t)
}
