package parser

import com.typesafe.scalalogging.Logger
import environment.{Coordinate, Direction, Garden, Mower}

import scala.util.{Failure, Success, Try}

object Parser {

  val logger = Logger("Parser Log")

  def parseGarden(args: List[String]): Option[Garden] = {
    val optGarden = Try(
      Garden(Coordinate(args(0).toInt, args(1).toInt), List())
    ).toOption
    optGarden match {
      case Some(g) if g.topRightPosition.x >= 0 && g.topRightPosition.y >= 0 =>
        Some(g)
      case _ => None
    }
  }

  def parseMower(args: List[String], actions: List[String]): Option[Mower] = {
    val direction = Try(Direction(args(2))) match {
      case Success(d) => d
      case Failure(_) => None
    }
    direction match {
      case Some(x) =>
        Try(
          Mower(
            Coordinate(args(0).toInt, args(1).toInt),
            x,
            Coordinate(args(0).toInt, args(1).toInt),
            x,
            actions
          )
        ).toOption
      case _ => None
    }
  }

  def processMower(
      startParameters: List[String],
      actions: List[String],
      garden: Garden
  ): Either[String, Mower] = {
    val mower = parseMower(startParameters, actions)
    mower match {
      case Some(m) if garden.isValidCoordinate(m.position) =>
        Right(Mower.processUpdates(actions, garden, m))
      case Some(_) => Left("Mower's position is outside of the garden")
      case _       => Left("Creation parameters for the mowers are invalid")
    }
  }

  def processMowerList(
      list: List[(List[String], List[String])],
      accumulator: Garden,
      step: Int
  ): Garden = {
    list match {
      case x :: xs =>
        logger.info("Processing mower number " + step.toString)
        processMowerList(
          xs,
          accumulator.addMower(processMower(x._1, x._2, accumulator)),
          step + 1
        )
      case Nil => accumulator
    }
  }
}
