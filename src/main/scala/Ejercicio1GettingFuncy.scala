class Ejercicio1GettingFuncy {
  println("**** EJERCICIO1:  Getting Funcy 4.1.2****")
  // definir el map: utilizando los métodos existentes, flatMap y pure

  import scala.language.higherKinds
  trait Monad2[F[_]] {
    def pure[A](a: A): F[A]
    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

   //el mapa representa secuenciar un cálculo que no genera un contexto monaico.
    def map   [A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))
  }

  println("def map   [A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))")
}


