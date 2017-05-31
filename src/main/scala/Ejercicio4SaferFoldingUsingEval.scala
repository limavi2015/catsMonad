
class Ejercicio4SaferFoldingUsingEval {
  println("**** EJERCICIO4: Safer Folding using Eval 4.5.5 ****")
  // La implementación de foldRightOld no es apilable. Hacerlo así usando Eval.

  def foldRightOld[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    as match {
      case head :: tail =>
        fn(head, foldRightOld(tail, acc)(fn))
      case Nil =>
        acc
    }

  import cats.Eval
  def foldRightEval[A, B](as: List[A], acc: Eval[B])
                         (fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail => Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
      case Nil =>  acc
    }
}
