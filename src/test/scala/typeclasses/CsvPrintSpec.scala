package typeclasses

import environment.{Coordinate, Garden, Mower, North, South}
import org.scalatest.funsuite.AnyFunSuite

class CsvPrintSpec extends AnyFunSuite {

  test("Print Garden should print the garden with all its mowers in csv format") {
    val garden = Garden(Coordinate(10, 10), List())

    val m1 = Mower(
      Coordinate(9, 9),
      North,
      Coordinate(9, 9),
      North,
      List("D", "A", "A", "F", "X", "G", "A", "A", "D")
    )
    val m2 = Mower(
      Coordinate(5, 0),
      South,
      Coordinate(5, 0),
      South,
      List("D", "A", "A", "F", "X", "G", "A", "A", "D")
    )

    val finalGarden = garden
      .addMower(Right(m1))
      .addMower(Right(m2))

    val expectedStr = "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions\n1;" + CsvPrint
      .print(m1) + "\n2;" + CsvPrint.print(m2)
    assert(CsvPrint.print(finalGarden) === expectedStr)
  }

}
