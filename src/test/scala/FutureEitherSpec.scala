import java.util.concurrent.TimeUnit

import org.scalatest.WordSpecLike

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import net.clementmatthew.util.FutureEitherUtil._


class FutureEitherSpec extends org.scalatest.Suite with WordSpecLike {

   case class Error(message: String = "Error String")

   "FutureEither" should {
      "compose and yield true" in {

         val result: Future[Either[Error, Boolean]] =
         for {
            a <- Truth.truth(true, "a called")
            b <- Truth.applyTruth(a, "b called")
            c <- Truth.applyTruth(b, "c called")
         } yield b

         result recover {
            case e:Throwable => Left(Error(e.getMessage))
         }

         val res: Either[Error, Boolean] = Await.result(result, Duration.create(5, TimeUnit.SECONDS))
         assert(res.isRight)
         assert(res.right.get)
      }

      "compose and yield false" in {

         val result: Future[Either[Error, Boolean]] =
         for {
            a <- Truth.truth(true, "a called")
            b <- Truth.applyTruth(a, "b called")
            c <- Truth.applyTruth(Right(false), "c should fail")
            d <- Truth.applyTruth(c, "d called")
         } yield d

         result recover {
            case e:Throwable => Left(Error(e.getMessage))
         }

         val res: Either[Error, Boolean] = Await.result(result, Duration.create(5, TimeUnit.SECONDS))
         assert(res.isLeft)
         assert(res.left.get.message == "c should fail" )
      }

      "compose and yield true w string" in {

         println("\n************************\n")

         val result: Future[Either[Error, Boolean]] =
         for {
            a <- Truth.truth(true, "a called")
            b <- Truth.applyTruth(a, "b called")
            c <- Truth.applyTruthString(b, "c message", "c called")
            d <- Truth.applyTruth(Right(true), "d called")
         } yield b

         result recover {
            case e:Throwable => Left(Error(e.getMessage))
         }

         val res: Either[Error, Boolean] = Await.result(result, Duration.create(5, TimeUnit.SECONDS))
         assert(res.isRight)
         assert(res.right.get)
      }

   }

  object Truth {
     def applyTruth(e: Either[Error, Boolean], s: String): Future[Either[Error, Boolean]] = applyEither1(Truth.truth, e, s)

     def applyTruthString(e: Either[Error, Boolean], s: String, s2: String) = applyEither2(Truth.truthString, e, s, s2)

     def truth(b: Boolean, message: String = ""): Future[Either[Error, Boolean]] = {
        println(s"truth message = $message")
        if (b) {
           Future.successful(Right(true))
        } else {
           Future.successful(Left(Error(message)))
        }
     }

     def truthString(b: Boolean, s: String, message: String = ""): Future[Either[Error, String]] = {
        println(s"truth string message = $message and $s")
        if (b) {
           Future.successful(Right(s))
        } else {
           Future.successful(Left(Error(message)))
        }
     }
  }
}
