import org.scalatest.WordSpecLike

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class FutureEitherSpec extends org.scalatest.Suite with WordSpecLike {

   case class Error(message: String = "Error String")

   "FutureEither" should {
      "compose and yield true" in {

         val result: Future[Either[Error, Boolean]] =
         for {
            a <- truth(true, "a = success")
            b <- truth(unbox(a), "b = success")
            c <- truth(unbox(b), "c = success")
         } yield c

         result.map{ result =>
            assert(result.isRight)
            assert(result.right.asInstanceOf[Boolean])
         }
      }

      "compose and yield false" in {
         println("\n************\n\n")

         val result: Future[Either[Error, Boolean]] =
         for {
            a <- truth(true, "a = success")
            b <- truth(unbox(a), "b = success")
            c <- truth(false, "c = failure")
            d <- truth(unbox(c), "d = success")
         } yield c

         result.map{ result =>
            assert(result.isLeft)
            assert(result.left.isInstanceOf[Error])
         }
      }

      "compose and yield true w/ mixed types" in {
         println("\n************\n\n")

         val result: Future[Either[Error, Boolean]] =
         for {
            a <- truth(true, "a = success")
            b <- truthString(unbox(a), "blah", "b = success")
            c <- truth(b != null, "c = failure")
            d <- truth(unbox(c), "d = success")
         } yield c

         result.map{ result =>
            assert(result.isLeft)
            assert(result.left.isInstanceOf[Error])
         }
      }
   }

   def unbox[A, B](e: Either[A, B]): B = e.right.get

   def truth(b: Boolean, message: String = ""): Future[Either[Error, Boolean]] = {
      println(s"truth message = $message")
      if (b) {
         Future.successful(Right(true))
      } else {
         Future.successful(Left(Error(message)))
      }
   }

   def truthString(b: Boolean, s: String, message: String = ""): Future[Either[Error, String]] = {
      println(s"truth message = $message and $s")
      if (b) {
         Future.successful(Right(s))
      } else {
         Future.successful(Left(Error(message)))
      }
   }
}
