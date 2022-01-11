package fr.esgi.al.funprog

import better.files.File
import environment.Garden
import exceptions.DonneesIncorrectesException
import parser.Parser
import typeclasses.{CsvPrint, JsonPrint}

@SuppressWarnings(Array("org.wartremover.warts.Throw"))
object Main extends App {

  val filePath = if (!args.isEmpty) args(0) else "src/main/resources/mower.txt"

  val tryReadFile = File(filePath)

  val lines = tryReadFile.lines.toList

  if (lines.isEmpty) {
    throw DonneesIncorrectesException(
      "No line inside the file. Exiting the program"
    )
  }

  val gardenTest: Any = Parser.parseGarden(lines(0).split(" ").toList) match {
    case Some(x) => x
    case _ =>
      throw DonneesIncorrectesException(
        "First line describing the garden is not valid. Exiting the program"
      )
  }

  val garden = gardenTest.asInstanceOf[Garden]

  val mowerLines =
    if (lines.length % 2 == 0) lines.drop(1).dropRight(1) else lines.drop(1)
  val linesToProcess = mowerLines
    .sliding(2, 2)
    .toList
    .map(l => (l(0).split(" ").toList, l(1).toList.map(s => s.toString)))

  val endGarden = Parser.processMowerList(linesToProcess, garden, 1)

  val gardenJson = JsonPrint.print(endGarden)
  val gardenCsv = CsvPrint.print(endGarden)
  println("====================================")
  println("             JSON PRINT             ")
  println("====================================")
  println(gardenJson)
  println("====================================")
  println("              CSV PRINT             ")
  println("====================================")
  println(gardenCsv)
}
