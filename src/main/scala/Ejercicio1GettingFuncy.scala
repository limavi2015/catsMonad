import cats.Monad

class Ejercicio1GettingFuncy {
  println("**** EJERCICIO1:  Getting Funcy 4.1.2****")
  // definir el map: utilizando los métodos existentes, flatMap y pure

  /*
  Every monad is also a functor.  If
  flatMap represents sequencing a computation that introduces a new monadic context,
  map represents sequencing a computation that does not.
  We can define map in the same way for every monad using the existing methods, flatMap and pure
  Try defining map yourself now.
  */

  import scala.language.higherKinds
  trait Monad[F[_]] {
    def pure[A](a: A): F[A]
    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

    /* Lo que yo pensaba que era
    def map[A, B](value: F[A])(func: A => F[B]): F[B] = func(flatMap(value) )
    */

    //El mapa representa secuenciar un cálculo que no genera un contexto monaico.
    def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))

  }
}


