package typeclasses

import environment._
import play.api.libs.json.Json.toJson
import org.scalatest.funsuite.AnyFunSuite
import play.api.libs.json.Json

@SuppressWarnings(Array("org.wartremover.warts.OptionPartial"))
class JsonPrintSpec extends AnyFunSuite {

  test("Print Garden should print the garden with all its mowers in json") {
    val garden = Garden(Coordinate(10, 10), List())

    val m1 = Mower(
      Coordinate(1, 2),
      North,
      Coordinate(1, 2),
      North,
      List("G", "A", "G", "A", "G", "A", "G", "A", "A")
    )
    val m2 = Mower(
      Coordinate(3, 3),
      East,
      Coordinate(3, 3),
      East,
      List("A", "A", "D", "A", "A", "D", "A", "D", "D", "A")
    )

    val finalGarden = garden
      .addMower(Right(m1))
      .addMower(Right(m2))

    val expectedStr = Json.toJson(
      Map(
        "limite" -> WriteJsonCoordinates.writes(Coordinate(10, 10)),
        "tondeuses" -> toJson(
          List(
            toJson(
              Map(
                "debut" -> toJson(
                  Map(
                    "point"     -> WriteJsonCoordinates.writes(Coordinate(1, 2)),
                    "direction" -> WriteJsonDirection.writes(Direction("N").get)
                  )
                ),
                "instructions" -> toJson(
                  List("G", "A", "G", "A", "G", "A", "G", "A", "A")
                ),
                "fin" -> toJson(
                  Map(
                    "point" -> WriteJsonCoordinates.writes(Coordinate(1, 2)),
                    "direction" -> WriteJsonDirection.writes(
                      Direction("N").get
                    )
                  )
                )
              )
            ),
            toJson(
              Map(
                "debut" -> toJson(
                  Map(
                    "point" -> WriteJsonCoordinates.writes(Coordinate(3, 3)),
                    "direction" -> WriteJsonDirection.writes(
                      Direction("E").get
                    )
                  )
                ),
                "instructions" -> toJson(
                  List("A", "A", "D", "A", "A", "D", "A", "D", "D", "A")
                ),
                "fin" -> toJson(
                  Map(
                    "point" -> WriteJsonCoordinates.writes(Coordinate(3, 3)),
                    "direction" -> WriteJsonDirection.writes(
                      Direction("E").get
                    )
                  )
                )
              )
            )
          )
        )
      )
    )
    assert(JsonPrint.print(finalGarden) === expectedStr)
  }

}
