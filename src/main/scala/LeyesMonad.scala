import cats.Monad
import cats.implicits._
class LeyesMonad {

//left identity (Monad[F].pure(x) flatMap {f}) === f(x) *** Llamar a "pure" y luego transformar el resultado con una función f es lo mismo que llamar simplemente f:
  def f1(x: Int) = Some(x + 2)
  println(Monad[Option].pure(3).flatMap(f1) === f1(3))

//right identity (m flatMap {Monad[F].pure(_)}) === m   *** Pasar "pure" a flatMap es lo mismo que no hacer nada
  val m1 =Some("valString")
  println(m1.flatMap(Monad[Option].pure(_)) === m1)

//associativity (m flatMap f) flatMap g === m flatMap { x => f(x) flatMap {g} }  *** flatMapping sobre dos funciones f y g es la mismo que flat Mapping sobre f y luego flatMapping sobre g.
  def f2(x: Int) = Some(x + 2)
  def g2(x: Int) = Some(x.toString)
  val m2: Option[Int] = Option(3)
  println(m2.flatMap(f2).flatMap(g2) === m2.flatMap(w =>f2(w).flatMap(g2)))

}


