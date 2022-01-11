package environment

import play.api.libs.json.{JsValue, Json, Writes}

/** A trait used to represent the four cardinal directions: North, East, South and West
 *
 */
sealed trait Direction {

  /** Gets the next cardinal direction from the right
   *
   * @return   the next cardinal direction from the right
   */
  def nextFromRight(): Direction

  /** Gets the next cardinal direction from the left
   *
   * @return   the next cardinal direction from the left
   */
  def nextFromLeft(): Direction
}

case object North extends Direction {
  override def nextFromLeft(): Direction = West
  override def nextFromRight(): Direction = East
}

case object East extends Direction {
  override def nextFromLeft(): Direction = North
  override def nextFromRight(): Direction = South
}

case object South extends Direction {
  override def nextFromLeft(): Direction = East
  override def nextFromRight(): Direction = West
}

case object West extends Direction {
  override def nextFromLeft(): Direction = South
  override def nextFromRight(): Direction = North
}

object Direction {

  def apply(s: String): Option[Direction] = s match {
    case "N" => Some(North);
    case "E" => Some(East);
    case "S" => Some(South);
    case "W" => Some(West);
    case _   => None
  }
}

object WriteJsonDirection extends Writes[Direction] {
  override def writes(value: Direction): JsValue = value match {
    case North => Json.toJson("N")
    case East  => Json.toJson("E")
    case South => Json.toJson("S")
    case _     => Json.toJson("W")
  }
}

object WriteCsvDirection {
  def writes(value: Direction): String = value match {
    case North => "N;"
    case East  => "E;"
    case South => "S;"
    case _     => "W;"
  }
}
