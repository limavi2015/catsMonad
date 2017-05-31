import cats.Monad
import cats.implicits._  //en vez del cats.syntax  y cats.instances

/*
import cats.instances.option._
import cats.instances.list._
import cats.instances.vector._
import cats.instances.future._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.applicative._
*/

class MonadsInCats {
  println("**** The Monad Type Class ****") // cats.Monad
  val opt1: Option[Int] = Monad[Option].pure(3)
  val opt2: Option[Int] = Monad[Option].flatMap(opt1)(a => Some(a + 2))
  val opt3: Option[Int] = Monad[Option].map(opt2)(a => 100 * a)
  val list1: List[Int]  = Monad[List].pure(3)
  val list2: List[Int]  = Monad[List].flatMap(List(1, 2, 3))(x => List(x, x*10))
  val list3: List[Int]  = Monad[List].map(list2)(_ + 123)
  println(opt1)

  println("**** Default Instances ****") // cats.instances._
  val option1: Option[Int] = Monad[Option].flatMap(Option(1))(x => Option(x*2))
  val list:    List[Int]   = Monad[List].flatMap(List(1, 2, 3))(x => List(x, x*10))
  val vector1: Vector[Int] = Monad[Vector].flatMap(Vector(1, 2, 3))(x => Vector(x, x*10))
  println(option1)

  println("**** Monad Syntax ****") // cats.instances._ y cats.syntax
  val OptionInt: Option[Int] = 3.pure[Option]
  val listInt:   List[Int]   = 1.pure[List]
  println(OptionInt)

}
