package typeclasses

import environment.{Coordinate, East, Garden, Mower, North, South, West}
//import org.scalacheck.Prop.forAll
import org.scalatest.funsuite.AnyFunSuite

class CsvPrintSpec extends AnyFunSuite {

  /*test("Print print Coordinate should print the coordinates") {
    forAll { (n: Int, m: Int) =>
      Print.print(Coordinate(n, m)) === n + " " + m
    }
  }*/

  /*test("Print print Direction should print the correct direction") {
    assert(Print.print(North.asInstanceOf[Direction]) === "North")
    assert(Print.print(East.asInstanceOf[Direction]) === "East")
    assert(Print.print(South.asInstanceOf[Direction]) === "South")
    assert(Print.print(West.asInstanceOf[Direction]) === "West")
  }*/

  /*test("Print print Mower should print the mower") {
    forAll { (n : Int, m : Int) =>
      val mower = Mower(Coordinate(n, m), North)
      Print.print(mower) === n + " " + m + " - " + "North"
    }

    forAll { (n : Int, m : Int) =>
      val mower = Mower(Coordinate(n, m), East)
      Print.print(mower) === n + " " + m + " - " + "East"
    }

    forAll { (n : Int, m : Int) =>
      val mower = Mower(Coordinate(n, m), South)
      Print.print(mower) === n + " " + m + " - " + "South"
    }

    forAll { (n : Int, m : Int) =>
      val mower = Mower(Coordinate(n, m), West)
      Print.print(mower) === n + " " + m + " - " + "West"
    }
  }*/

  test("Print print Garden should print the garden with all its mowers") {
    val garden = Garden(Coordinate(10, 10), List())

    val m1 = Mower(
      Coordinate(0, 0),
      North,
      Coordinate(0, 0),
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
    val m3 = Mower(
      Coordinate(2, 8),
      West,
      Coordinate(2, 8),
      West,
      List("D", "A", "A", "F", "X", "G", "A", "A", "D")
    )
    val m4 = Mower(
      Coordinate(9, 9),
      East,
      Coordinate(9, 9),
      East,
      List("D", "A", "A", "F", "X", "G", "A", "A", "D")
    )
    //val m5 = Mower(Coordinate(42, 42), West)

    val finalGarden = garden
      .addMower(Right(m1))
      .addMower(Right(m2))
      .addMower(Left("AAAAAA"))
      .addMower(Right(m3))
      .addMower(Right(m4))

    val expectedStr = "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions\n1;" + CsvPrint.print(m1) + "\n2;" + CsvPrint.print(m2) + "\n3;" + CsvPrint
      .print(m3) + "\n4;" + CsvPrint.print(m4)
    assert(CsvPrint.print(finalGarden) === expectedStr)
  }

}
