package exceptions

case class DonneesIncorrectesException(message: String)
    extends Exception(message)
