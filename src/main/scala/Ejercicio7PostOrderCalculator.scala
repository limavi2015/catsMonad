import cats.data.State
import cats.syntax.applicative._

class Ejercicio7PostOrderCalculator {
  println("****  EJERCICIO7:Post-Order Calculator 4.8.3 ****")

  //implementación de una calculadora para las expresiones aritméticas de enteros posteriores al orden.
  //expresiones post-orden son una nota matemática en donde escribimos el operador a sus operandos.
  // Así, por ejemplo, en vez de escribir 1 + 2 escribiríamos:  1 2 +

  //Comience por ejecutar una función en evalOne que analiza un solo símbolo en una instancia de Estado.
  type CalcState[A] = State[List[Int], A]
  def evalOne(sym: String): CalcState[Int] = //**
    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case num => operand(num.toInt)
    }

  def operator(func: (Int, Int) => Int): CalcState[Int] =
  State[List[Int], Int] {
    case a :: b :: tail =>
      val ans = func(a, b)
      (ans :: tail, ans)
    case _ =>
      sys.error("Fail!")
  }

  def operand(num: Int): CalcState[Int] =
    State[List[Int], Int] { stack =>
      (num :: stack, num)
    }

  def evalAll(input: List[String]): CalcState[Int] = { //**
    input.foldLeft(0.pure[CalcState]) { (a, b) =>
      a flatMap (_ => evalOne(b))
    }
  }

  val program = evalAll(List("1", "2", "+", "3", "*"))
  program.runA(Nil).value

  val program2 = for {
    _ <- evalAll(List("1", "2", "+"))
    _ <- evalAll(List("3", "4", "+"))
    ans <- evalOne("*")
  } yield ans



}
