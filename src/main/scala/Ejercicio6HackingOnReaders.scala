import cats.data.Reader
import cats.syntax.applicative._

class Ejercicio6HackingOnReaders {
  println("****  EJERCICIO6:Hacking on Readers 4.7.3 ****")
  //sistema de inicio de sesión simple
  case class Db(usernames: Map[Int, String],passwords: Map[String, String])


  //Comience creando un alias de tipo DataReader para un reader que consume un Db como entrada.
  //val DataReader: Reader[Db, Map[A, B]] = Reader( _ => A) //INTENTO FALLIDO
  type DbReader[A] = Reader[Db, A]


  //Cree métodos que generen DbReaders para buscar el nombre de usuario para un ID de usuario Int y
  // busque la contraseña de un nombre de usuario String.
  def findUsername(userId: Int): DbReader[Option[String]] =  Reader(_.usernames.get(userId))
  def checkPassword(username: String, password: String): DbReader[Boolean] =  Reader(_.passwords.get(username).contains(password))


  //cree un método checkLogin para comprobar la contraseña de un ID de usuario determinado
  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for {
      username
      <- findUsername(userId)
      passwordOk <- username.map { username =>
        checkPassword(username, password)
      }.getOrElse {
        false.pure[DbReader]
      }
    } yield passwordOk


  //finalmente,
  val db = Db( Map(1 -> "dade", 2 -> "kate", 3 -> "margo" ), Map("dade" -> "zerocool", "kate" -> "acidburn","margo" -> "secret"))

  println(checkLogin(1, "zerocool").run(db))
  println(checkLogin(4, "davinci").run(db))


}
