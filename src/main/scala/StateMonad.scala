import cats.data.State

class StateMonad {

  val a: State[Int, String] = State[Int, String] { state =>
    (state, s"The state is $state")
  }

  val (state, result) = a.run(10).value //Get the state and the result
  println(s"state: $state, result: $result")
  println("runS: " +  a.runS(10).value) //Get the state, ignore the result
  println("runA: " +  a.runA(10).value) //Get the result, ignore the state

  //Composicion y transformacion de estados
  val step1 = State[Int, String] { num =>
    val ans = num + 1
    (ans, s"Result of step1: $ans")
  }

  val step2 = State[Int, String] { num =>
    val ans = num * 2
    (ans, s"Result of step2: $ans")
  }

  val both = for {
    a <- step1
    b <- step2
  } yield (a, b)

  val (stateboth, resultboth) = both.run(20).value
  println(s"state: $stateboth, result: $resultboth")


}
