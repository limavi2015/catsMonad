import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

class FlatmapExample {

  //nos permite secuenciar estas operaciones sin tener que comprobar constantemente si devuelven Some
  println("**** FlatMap-Option **** ")
  def parseInt(str: String): Option[Int] = scala.util.Try(str.toInt).toOption
  println(parseInt("3").flatMap(numOp=> Some(numOp * 2)).flatMap(numOp2 => Some(numOp2 * 2)))
  println(parseInt("b").flatMap(numOp=> Some(numOp * 2)).flatMap(numOp2 => Some(numOp2 * 2)))


  // Aplica una función que devuelve una List para cada item de la List aplanando los resultados en la List original.
  println("**** FlatMap-List ****")
  println("liliana".flatMap(_ + "-"))
  val listaInt: List[Int] = List(4, 8, 16)
  def g(v:Int)= List(v-1, v+1)
  println(listaInt.flatMap(g(_)))
  val listaString: List[String] = List("2","3","ab")
  println(listaString.flatMap(parseInt(_)))
  val listaOption= List(Some(1), None, Some(3))
  println(listaOption.flatMap(List(_)))


  //permite secuenciar órdenes sin preocuparnos de que sean asíncronas
  println("**** FlatMap-Future ****")
  val futuro3: Future[Int] = Future(3)
  val futuro5: Future[Int] = Future(5)
  println(Await.result(futuro3.flatMap(int3 => futuro5.flatMap(int5=> Future(int3 + int5) )),1.seconds))
  val futuro3Map    : Future[Future[String]] = futuro3.map(n => Future(n.toString))
  val futuro3FlatMap: Future[String]         = futuro3.flatMap(n => Future(n.toString))

}
