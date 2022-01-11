package environment

//import org.scalacheck.Gen
//import org.scalacheck.Prop.forAll
import org.scalatest.funsuite.AnyFunSuite

class GardenSpec extends AnyFunSuite {

  test("Garden addMower should add new valid mowers to the garden") {
    val garden = Garden(Coordinate(10, 10), List())

    val m1 = Mower(
      Coordinate(2, 2),
      North,
      Coordinate(2, 2),
      North,
      "A" :: "G" :: "A" :: Nil
    )
    val m2 = Mower(
      Coordinate(4, 2),
      East,
      Coordinate(4, 2),
      East,
      "A" :: "G" :: "A" :: Nil
    )
    val m3 = Mower(
      Coordinate(0, 2),
      South,
      Coordinate(0, 2),
      South,
      "A" :: "G" :: "A" :: Nil
    )

    val finalGarden =
      garden.addMower(Right(m1)).addMower(Right(m2)).addMower(Right(m3))

    assert(finalGarden.mowers(0) === m3)
    assert(finalGarden.mowers(1) === m2)
    assert(finalGarden.mowers(2) === m1)
  }

  test("Garden addMower should only add valid mowers to the garden") {
    val garden = Garden(Coordinate(10, 10), List())

    val m1 = Mower(
      Coordinate(2, 2),
      North,
      Coordinate(2, 2),
      North,
      "A" :: "G" :: "A" :: Nil
    )
    val m2 = Mower(
      Coordinate(4, 2),
      East,
      Coordinate(4, 2),
      East,
      "A" :: "G" :: "A" :: Nil
    )
    val m3 = Mower(
      Coordinate(0, 2),
      South,
      Coordinate(0, 2),
      South,
      "A" :: "G" :: "A" :: Nil
    )

    val finalGarden = garden
      .addMower(Right(m1))
      .addMower(Left("aaaaaaaaa"))
      .addMower(Right(m2))
      .addMower(Right(m3))
      .addMower(Left("BBBBBBBBB"))

    assert(finalGarden.mowers(0) === m3)
    assert(finalGarden.mowers(1) === m2)
    assert(finalGarden.mowers(2) === m1)
  }
}
