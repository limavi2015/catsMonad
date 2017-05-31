import cats.data.Writer
import cats.syntax.applicative._
import cats.syntax.writer._
import cats.instances.vector._

class Ejercicio5ShowYouWorking {
  println("****  EJERCICIO5:Show Your Working 4.6.3 ****")
  //Reescribir factorial para que capture los mensajes de registro en un Writer.
  //Demostrar que esto nos permite separar los registros de manera confiable para los cálculos concurrentes.

  def slowly[A](body: => A) = try body finally Thread.sleep(100)
  private val writerEjemplo = Writer(Vector("the best",  "the worst"), 123)
//  private val d = w.mapWritten(_.map(_.toUpperCase))


  def factorialOld(n: Int): Int = {
    val ans = slowly(
      if(n == 0) {
        1
      }
      else {
        n * factorialOld(n - 1)
      }
    )
    println(s"fact $n $ans")
    ans
  }

  type Logged[A] = Writer[Vector[String], A]
  def factorial(n: Int): Logged[Int] = {
    if(n == 0) {
      1.pure[Logged]
    } else {
      for {
        a <- slowly(factorial(n - 1))
        _ <- Vector(s"fact $n ${a*n}").tell
      } yield a * n
    }
  }

  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  Await.result(Future.sequence(Vector(
    Future(factorial(3)),
    Future(factorial(3))
  )), Duration.Inf)

  val (log, result) = factorial(5).run
  println(s"$log  $result")
}
