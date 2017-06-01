import cats.Eval

class Ejercicio4SaferFoldingUsingEval {
  println("**** EJERCICIO4: Safer Folding using Eval 4.5.5 ****")
  // La implementación de foldRightOld no es apilable. Hacerlo usando Eval.

  val list1=List(1 to 50000:_*) //50000
  val valString="lili"
  val evalString: Eval[String] = Eval.now("lili")
  def funcion(a: Int, b: String):String ={ a.toString + "-"}
  def funcionlili(a: Int, b: Eval[String]):Eval[String] ={ Eval.later(a.toString + "-")}
  def funcionEval(a: Int, b: Eval[String]):Eval[String] ={ Eval.now(a.toString + "-")}

  def foldRightOld[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    as match {
      case head :: tail => fn(head, foldRightOld(tail, acc)(fn))
      case Nil => acc
    }
  //println(foldRightOld(list1,valString)(funcion))


  def foldRightLili[A, B](as: List[A], acc: B)(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail => Eval.defer(fn(head, foldRightLili(tail, acc)(fn)))
      case Nil => Eval.now(acc)
    }
  println(foldRightLili(list1,valString)(funcionlili).value)


  def foldRightEval[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] ={
    as match {
      case head :: tail => Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
      case Nil => acc
    }
  }
  //println("solucion libro" + foldRightEval(list1,evalString)(funcionEval).value)


}
