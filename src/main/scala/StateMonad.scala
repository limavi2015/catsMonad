import cats.data.State

class StateMonad {

  val a: State[Int, String] = State[Int, String] { state => (state + 1, s"This is the result")}
  println("runS,  state: " +  a.runS(10).value) //Get the state, ignore the result
  println("runA, result: " +  a.runA(10).value) //Get the result, ignore the state
  val (state, result) = a.run(10).value //Get the state and the result
  println(s"run, state: $state, result: $result")

  //Composicion y transformacion de estados, La salida del primero fuera la entrada del segundo.
  val step1: State[Int, String] = State[Int, String] { num =>  (num + 1, s"Result of step1: $num + 1")}
  val step2: State[Int, String] = State[Int, String] { num =>  (num * 2, s"Result of step2: $num * 2")}
  val both = for {
    a <- step1
    b <- step2
  } yield (a, b)
  val (stateboth, resultboth) = both.run(10).value
  println(s"both.run(10),  Stateboth: $stateboth, Resultboth: $resultboth")




  //constructores de conveniencia para crear pasos primitivos
  val getDemo = State.get[Int]                     //Extrae el estado como resultado
  println(s"GET ${getDemo.run(10).value} ")

  val setDemo = State.set[Int](30)                 //Actualiza el estado y devuelve unit como resultado
  println(s"SET ${setDemo.run(10).value} ")

  val pureDemo = State.pure[Int, String]("Result") //Ignora el estado y devuelve un resultado suministrado
  println(s"PURE ${pureDemo.run(10).value} ")

  val inspectDemo = State.inspect[Int, String](_ + "!!!") //Extrae el estado mediante una función de transformación
  println(s"INSPECT ${inspectDemo.run(10).value} ")

  val modifyDemo = State.modify[Int](_ + 1)        //Actualiza el estado utilizando una función de actualización.
  println(s" MODIFY ${modifyDemo.run(10).value} ")

  import State._
  val program: State[Int, (Int, Int, Int)] = for {
    a <- get[Int]
    _ <- set[Int](a + 1)
    b <- get[Int]
    _ <- modify[Int](_ + 1)
    c <- inspect[Int, Int](_ * 1000)
  } yield (a, b, c)
  val (stateProgram, resultProgram) = program.run(1).value
  println(s"program.run(1),  stateProgram: $stateProgram, resultProgram: $resultProgram")

}
