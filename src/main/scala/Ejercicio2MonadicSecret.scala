
class Ejercicio2MonadicSecret {
  println("**** EJERCICIO2: Monadic Secret Identities 4.3.1 ****")
  //Implementar pure, mapa y flatMap para Id


  import cats.Id

  def pure[A](value: A): Id[A] = value:Id[A]
  def flatMap[A, B](value: Id[A])(func: A => Id[B]): Id[B] = func(value)
  def map[A, B](value: Id[A])(func: A => Id[B]): Id[B] = func(value)

/* solucion del libro
  def pure[A](value: A): Id[A] = value
  def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] =  func(initial)
  def map[A, B]    (initial: Id[A])(func: A => B):     Id[B] =  func(initial)
*/

  println(pure(44))
  println(map(44)(_ * 3))
  println(flatMap(44)(_ * 3))
}


//La operación pura es un constructor: crea un Id [A] a partir de un valor inicial de tipo A. Pero A e Id [A] son del mismo , lo que tenemos que hacer es devolver el valor inicial
//El método de map aplica una función de tipo A => B a un Id [A], creando un Id [B]. Pero Id [A] es simplemente A e Id [B] es simplemente B, lo que tenemos que hacer es llamar a la función.