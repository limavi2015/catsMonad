import cats.data.Reader

class ReaderMonad {

  case class Cat(name: String, color: String)

  //Podemos crear readers de funciones y extraer las funciones de nuevo.
  val catName: Reader[Cat, String] = Reader(cat => cat.name)
  println(catName.run(Cat("Garfield", "red")))

  //map simplemente extiende el cálculo en el reader pasando su resultado a través de una función
  val greetKitty: Reader[Cat, String] = catName.map(name => s"Hello ${name}")
  println(greetKitty.run(Cat("Garfield", "blue")))

  //flatmap permite combinar múltiples readers que dependen del mismo tipo de entrada
  val feedKitty: Reader[Cat, String] = Reader(cat => s"Have a nice bowl of ${cat.color}")
  val greetAndFeed: Reader[Cat, String] =
    for {
      msg1 <- greetKitty
      msg2 <- feedKitty
    } yield s"${msg1} ${msg2}"
  println(greetAndFeed(Cat("parker", "pink")))



}
