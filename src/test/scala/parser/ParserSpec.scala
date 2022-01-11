package parser

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Shrink}
import org.scalatest.funsuite.AnyFunSuite

class PrintSpec extends AnyFunSuite {

  implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny

  test("Parser parserGarden should return Some(Garden)") {
    val posNum1 = Gen.choose(0, Integer.MAX_VALUE)
    val posNum2 = Gen.choose(0, Integer.MAX_VALUE)

    forAll(posNum1, posNum2) { (x, y) =>
      val l = List(x.toString, y.toString)
      Parser.parseGarden(l) should be(Some(Garden(Coordinate(x, y), List())))
    }
  }

}
