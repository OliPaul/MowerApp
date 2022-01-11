package environment

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalatest.funsuite.AnyFunSuite

class DirectionSpec extends AnyFunSuite {
  test("Direction apply should return the correct Direction") {
    assert(Direction("N") === Some(North))
    assert(Direction("E") === Some(East))
    assert(Direction("W") === Some(West))
    assert(Direction("S") === Some(South))

    val rightDirections = List("N", "E", "W", "S")
    val wrongDirections = Gen.alphaStr suchThat (
        s => !rightDirections.contains(s)
    )

    forAll(wrongDirections) { x =>
      Direction(x) === None
    }
  }

  test("Direction nextFromLeft for North should return West") {
    assert(North.nextFromLeft() === West)
  }

  test("Direction nextFromRight for North should return East") {
    assert(North.nextFromRight() === East)
  }

  test("Direction nextFromLeft for East should return North") {
    assert(East.nextFromLeft() === North)
  }

  test("Direction nextFromRight for East should return South") {
    assert(East.nextFromRight() === South)
  }

  test("Direction nextFromLeft for South should return East") {
    assert(South.nextFromLeft() === East)
  }

  test("Direction nextFromRight for South should return West") {
    assert(South.nextFromRight() === West)
  }

  test("Direction nextFromLeft for West should return South") {
    assert(West.nextFromLeft() === South)
  }

  test("Direction nextFromRight for West should return North") {
    assert(West.nextFromRight() === North)
  }
}
