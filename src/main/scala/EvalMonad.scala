import cats.Eval

class EvalMonad {

  val x: Int = {1 + 1 }  //eager and memoized
  def y: Int = {1 + 1 }  // lazy and not memoized
  lazy val z: Int = {1 + 1 }  // lazy and memoized

  val valNow: Eval[Int]   = Eval.now(1 + 2)    // eager and memoized (val)
  val valLater: Eval[Int] = Eval.later(3 + 4)  // lazy and memoized (lazy val)
  val valAlways: Eval[Int]= Eval.always(5 + 6) // lazy and not memoized (def)
  //println("valNow: " + valNow.value)

  //EVAL EN NOMAD  ********************************************************************
  val greeting = Eval.now {
    println("greeting1 1")
    "Hello"
  }.map { str =>
    println("greeting1 2")
    str + " world"
  }
  //println("greeting1 llamado 1: " + greeting.value)
  //println("greeting1 llamado 2: " + greeting.value)


  // MEMOIZE: Los cálculos antes de la llamada a memoize se almacenan en caché,
  // mientras que los cálculos después de la llamada conservan su semántica original:
  val saying: Eval[String] = Eval.always {
    println("saying 1")
    "The cat"
  }.map { str =>
    println("saying 2")
    s"$str sat on"
  }.memoize.map { str =>
    println("saying 3")
    s"$str the mat"
  }
  //println("saying llamado1: " + saying.value)
  //println("saying llamado2: " +saying.value)


  // TRAMPOLINING: Una propiedad útil de Eval es que sus métodos map y flatMap son trampolined.
  // Significa que podemos anidar las llamadas a mapa y flatMap arbitrariamente sin consumir los marcos de pila.
  // Propiedad: stack safety (seguridad de la pila)
  def factorial(n: BigInt): BigInt = if(n == 1) n else n * factorial(n - 1)
  //println(factorial(50000)) falla

  def factorial3(n: BigInt): Eval[BigInt] = if(n == 1) Eval.now(n) else factorial3(n - 1).map(_ * n)
  //factorial3(50000).value falla,

  // Eval.defer, toma una instancia existente de Eval y difiere su evaluación hasta más tarde.
  def factorial2(n: BigInt): Eval[BigInt] = //Eval.defer
    if(n == 1) Eval.now(n) else Eval.defer(factorial2(n - 1).map(_ * n))
  println(factorial2(50000).value)

}
