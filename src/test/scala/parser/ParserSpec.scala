package parser

import environment.{Coordinate, East, Garden, Mower, North, South, West}
import org.scalacheck.Shrink
import org.scalatest.funsuite.AnyFunSuite

class ParserSpec extends AnyFunSuite {

  implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny

  test("Parser processMower should return a Right[mower]") {
    val garden = Garden(Coordinate(10, 10), List())
    val startParameters = List("3", "4", "N")
    val actions1 = List("A", "D", "D", "A", "A", "G", "A")
    val actions2 = List("G", "A", "G", "A", "A", "U", "D")

    assert(
      Parser.processMower(startParameters, actions1, garden) === Right(
        Mower(
          Coordinate(4, 3),
          East,
          Coordinate(3, 4),
          North,
          List("A", "D", "D", "A", "A", "G", "A")
        )
      )
    )
    assert(
      Parser.processMower(startParameters, actions2, garden) === Right(
        Mower(
          Coordinate(2, 2),
          West,
          Coordinate(3, 4),
          North,
          List("G", "A", "G", "A", "A", "U", "D")
        )
      )
    )
  }

  test(
    "Parser processMower should return a Left when starting parameters are not complete"
  ) {
    val garden = Garden(Coordinate(10, 10), List())
    val startParameters = List("3", "N")
    val actions1 = List("A", "A", "A")

    assert(
      Parser.processMower(startParameters, actions1, garden) === Left(
        "Creation parameters for the mowers are invalid"
      )
    )
  }

  test("Parser processMowerList should return a garden with all the mowers") {
    val garden = Garden(Coordinate(100, 100), List())

    val l1 = (List("5", "10", "N"), List("A", "D", "A", "A", "D"))
    val l2 = (List("42", "42", "E"), List("A", "A", "G", "G", "A"))
    val l3 = (List("2", "50", "S"), List("A", "D", "A", "D", "D"))

    val list = List(l1, l2, l3)

    val m1 = Mower(
      Coordinate(7, 11),
      South,
      Coordinate(5, 10),
      North,
      List("A", "D", "A", "A", "D")
    )
    val m2 = Mower(
      Coordinate(43, 42),
      West,
      Coordinate(42, 42),
      East,
      List("A", "A", "G", "G", "A")
    )
    val m3 = Mower(
      Coordinate(1, 49),
      East,
      Coordinate(2, 50),
      South,
      List("A", "D", "A", "D", "D")
    )

    val finalGarden = Garden(Coordinate(100, 100), List(m3, m2, m1))

    assert(Parser.processMowerList(list, garden, 1) === finalGarden)
  }

  test("Parser processMowerList should only add Right(Mower) to the garden") {
    val garden = Garden(Coordinate(100, 100), List())

    val l1 = (List("5", "10"), List("A", "D", "A", "A", "D"))
    val l2 = (List("42", "E"), List("A", "A", "G", "G", "A"))
    val l3 = (List("103", "50", "S"), List("A", "D", "A", "D", "D"))
    val l4 = (List("0", "42", "E"), List("A", "A", "G", "G", "A"))

    val list = List(l1, l2, l3, l4)

    val m = Mower(
      Coordinate(1, 42),
      West,
      Coordinate(0, 42),
      East,
      List("A", "A", "G", "G", "A")
    )

    val finalGarden = Garden(Coordinate(100, 100), List(m))

    assert(Parser.processMowerList(list, garden, 1) === finalGarden)
  }

}
