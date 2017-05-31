import cats._
import cats.data.Writer
import cats.instances.vector._
import cats.syntax.writer._

class WriterMonad {

  //Un escritor [W, A] lleva dos valores: un log de tipo W y un result de tipo A.
  //Podemos crear un Writer a partir de valores de cada tipo de la siguiente manera:
  private val writerEjemplo = Writer(Vector("the best",  "the worst"), 123)
  println(s"writerEjemplo: result: ${writerEjemplo.value}  log: ${writerEjemplo.written}")

  //Si tenemos el result y ningún log. Podemos usar el estandar de sintaxis pure.
  import cats.syntax.applicative._ // `pure` method
  type Logged[A] = Writer[Vector[String], A]
  val x: Logged[Int] = 123.pure[Logged]
  println(s"123.pure[Logged]:  $x")

  //Si tenemos un log y ningún result, podemos crear un Writer [Unit] utilizando la sintaxis Tell
  println("tell: " + Vector("msg1", "msg2", "msg3").tell)

  //si tenemos un log y un result podemos usar: cats.syntax.writer._
  import cats.syntax.writer._
  //private val a: WriterT[Id, Int, Vector[String]] = Writer(444, Vector("msg1", "msg2", "msg3")) // =(
  private val b: Writer[Vector[String], Int] = 333.writer(Vector("msg1", "msg2", "msg3")) //WriterIdSyntax
  println(s"b: result: ${b.value}  log: ${b.written}")
  println(s"b: result: ${b.run._2} log: ${b.run._1}" )

  //con la funcion mapWritten podemos transformar el log.
  private val w = 333.writer(Vector("msg1", "msg2", "msg3"))
  private val d = w.mapWritten(_.map(_.toUpperCase))
  println(s"d: result ${d.run._2} log: ${d.run._1}")

  //con la funcion bimap podemos cambiar el log y el resultado, recibe una funcion para el log y otra para el result.
  private val writer3 = w.bimap(
    log => log.map(_.toUpperCase),
    result => result * 100
  )
  println(s"writer3  result: ${writer3.run._2} log: ${writer3.run._1}")

  // con la funcion mapBoth podemos cambiar el log y el resultado, toma una sola función que acepta dos parámetros
  private val writer4 = w.mapBoth { (log, result) =>
    val log2 = log.map(_ + "!")
    val result2 = result * 1000
    (log2, result2)
  }
  println(s"writer4  result: ${writer4.run._2} log: ${writer4.run._1}")

  //con el método reset podemos limpiar el log y  con método swap podemos borrar el result
  println(s"writer4 reset ${writer4.reset.run}")
  println(s"writer4 swap  ${writer4.swap.run}")

}
