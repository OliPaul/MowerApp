package environment

//import org.scalacheck.Gen
//import org.scalacheck.Prop.forAll
import org.scalatest.funsuite.AnyFunSuite

class MowerSpec extends AnyFunSuite {

  test("Mower advance should return a new Mower when the coordinates are valid") {
    val garden = Garden(Coordinate(3, 3), List())

    val mowerNorth = Mower(
      Coordinate(0, 0),
      North,
      Coordinate(0, 0),
      North,
      "A" :: "G" :: "A" :: Nil
    )
    val mowerEast = Mower(
      Coordinate(0, 3),
      East,
      Coordinate(0, 3),
      East,
      "A" :: "G" :: "A" :: Nil
    )
    val mowerSouth = Mower(
      Coordinate(3, 3),
      South,
      Coordinate(3, 3),
      South,
      "A" :: "G" :: "A" :: Nil
    )
    val mowerWest = Mower(
      Coordinate(3, 0),
      West,
      Coordinate(3, 0),
      West,
      "A" :: "G" :: "A" :: Nil
    )

    assert(
      mowerNorth.advance(garden) === Mower(
        Coordinate(0, 1),
        North,
        Coordinate(0, 0),
        North,
        "A" :: "G" :: "A" :: Nil
      )
    )
    assert(
      mowerEast.advance(garden) === Mower(
        Coordinate(1, 3),
        East,
        Coordinate(0, 3),
        East,
        "A" :: "G" :: "A" :: Nil
      )
    )
    assert(
      mowerSouth.advance(garden) === Mower(
        Coordinate(3, 2),
        South,
        Coordinate(3, 3),
        South,
        "A" :: "G" :: "A" :: Nil
      )
    )
    assert(
      mowerWest.advance(garden) === Mower(
        Coordinate(2, 0),
        West,
        Coordinate(3, 0),
        West,
        "A" :: "G" :: "A" :: Nil
      )
    )
  }

  test(
    "Mower advance should return the same mower when the coordinates are invalid"
  ) {
    val garden = Garden(Coordinate(3, 3), List())

    val mowerNorth = Mower(
      Coordinate(0, 3),
      North,
      Coordinate(0, 3),
      North,
      "A" :: "G" :: "A" :: Nil
    )
    val mowerEast = Mower(
      Coordinate(3, 3),
      East,
      Coordinate(3, 3),
      East,
      "A" :: "G" :: "A" :: Nil
    )
    val mowerSouth = Mower(
      Coordinate(3, 0),
      South,
      Coordinate(3, 0),
      South,
      "A" :: "G" :: "A" :: Nil
    )
    val mowerWest = Mower(
      Coordinate(0, 0),
      West,
      Coordinate(0, 0),
      West,
      "A" :: "G" :: "A" :: Nil
    )

    assert(mowerNorth.advance(garden) === mowerNorth)
    assert(mowerEast.advance(garden) === mowerEast)
    assert(mowerSouth.advance(garden) === mowerSouth)
    assert(mowerWest.advance(garden) === mowerWest)
  }

  test("Mower multipleUpdates should return a new mower correctly updated") {
    val garden = Garden(Coordinate(2, 2), List())
    val mower = Mower(
      Coordinate(1, 1),
      North,
      Coordinate(1, 1),
      North,
      List("A", "G", "A", "D", "D", "A", "G", "A")
    )

    val m1 = Mower.processUpdates(
      List("A", "G", "A", "D", "D", "A", "G", "A"),
      garden,
      mower
    )
    assert(
      m1 === Mower(
        Coordinate(1, 2),
        North,
        Coordinate(1, 1),
        North,
        List("A", "G", "A", "D", "D", "A", "G", "A")
      )
    )

    val m2 = Mower.processUpdates(
      List("D", "A", "A", "F", "X", "G", "A", "A", "D"),
      garden,
      mower
    )
    assert(
      m2 === Mower(
        Coordinate(2, 2),
        East,
        Coordinate(1, 1),
        North,
        List("A", "G", "A", "D", "D", "A", "G", "A")
      )
    )
  }

}
