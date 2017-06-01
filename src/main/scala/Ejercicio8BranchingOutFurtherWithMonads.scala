class Ejercicio8BranchingOutFurtherWithMonads {
  println("****  EJERCICIO8:Branching out Further with Monads 4.9.1 ****")

  //Aquí está el tipo de nuevo, junto con los constructores inteligentes que usamos para
  // simplificar la selección de tipo de instancia de clase
  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
  def leaf[A](value: A): Tree[A] = Leaf(value)


  //Compruebe que el código funciona en instancias de Branch y Leaf y que la Monad proporciona Functor-como comportamiento de forma gratuita.
  // Compruebe que tener una Mónada en el ámbito nos permite utilizar para comprensiones, a pesar de que no hemos implementado directamente FlatMap o mapa en árbol.


  import cats.Monad
  implicit val treeMonad = new Monad[Tree] {
    def pure[A](value: A): Tree[A] = Leaf(value)

    def flatMap[A, B](tree: Tree[A])(func: A => Tree[B]): Tree[B] =
      tree match {
        case Branch(l, r) =>
          Branch(flatMap(l)(func), flatMap(r)(func))
        case Leaf(value)
        =>
          func(value)
      }

    def tailRecM[A, B](arg: A)(func: A => Tree[Either[A, B]]): Tree[B] =
      func(arg) match {
        case Branch(l, r) =>
          Branch(
            flatMap(l) {
              case Left(l) => tailRecM(l)(func)
              case Right(l) => pure(l)
            },
            flatMap(r) {
              case Left(r)  => tailRecM(r)(func)
              case Right(r) => pure(r)
            }
          )
        case Leaf(Left(value)) =>  tailRecM(value)(func)
        case Leaf(Right(value)) => Leaf(value)
      }
  }

}
