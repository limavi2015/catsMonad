import cats.{Id, Monad}
import cats.implicits._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class IdentidadID {
  println("**** The Identity Monad ****") // cats.Id

  val x: Id[Int] = 1
  x.flatMap(d => d*2)

  def sumSquare[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] ={  //Funcion generica
    for {
      x <- a
      y <- b
    } yield x*x + y*y
  }
  val resultOption: Option[Int] = sumSquare(Option(3), Option(4))
  val resultID: Id[Int] = sumSquare(3 : Id[Int], 4 : Id[Int])


  val a: Id[Int] = Monad[Id].pure(3)
  val resultAsincrono: Int     = Await.result(sumSquare(Future(3), Future(4)), 1.seconds)
  val resultSincrono : Id[Int] = sumSquare(a, a)
  println(resultAsincrono)
  println(resultSincrono)

  val idInt2: Id[Int] = Monad[Id].pure(3) //Cats proporciona instancias de varias clases de tipo para Id
  println(idInt2)
}
