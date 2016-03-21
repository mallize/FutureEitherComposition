import net.clementmatthew.util.FutureEither
import org.scalatest.WordSpecLike

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

class FutureEitherSpec extends org.scalatest.Suite with WordSpecLike {

   "FutureEither" should {
      "compose and yield string" in {
         val res: FutureEither[Error, String] =
            for {
               a <- truth(true, "a = success")
               b <- truth(a, "b = success")
               c <- truth(b, "c = success")
               d <- truthString(c, "my string", "d = success")
            } yield d

         res.fold(
            error => assert(false),
            success => assert(success == "my string")
         )
      }

      "compose and yield error" in {
         val res: FutureEither[Error, String] =
            for {
               a <- truth(true, "a = success")
               b <- truth(false, "b = failed")
               c <- truth(b, "c = success")
               d <- truthString(c, "my string", "d = success")
            } yield d

         res.fold(
            (error: Error) => assert(true),
            (success: String) => assert(false)
         )
      }

      "compose with flatmap and yield boolean" in {
         val result: FutureEither[Error, Boolean] =
            truth(true, "a = success")
                  .flatMap(truth(_, "b = success"))
                  .flatMap(truth(_, "c = success"))
                  .flatMap(truth(_, "d = success"))

         result.fold(
            (error: Error) => assert(true),
            (success: Boolean) => assert(success)
         )
      }
   }

   case class Error(message: String = "Error String")

   def truth(b: Boolean, message: String = ""): FutureEither[Error, Boolean] = {
      println(s"truth message = $message")
      if (b) {
         FutureEither.point(true)
      } else {
         FutureEither.failure(Error(message))
      }
   }

   def truthString(b: Boolean, s: String, message: String = ""): FutureEither[Error, String] = {
      println(s"truth message = $message")
      if (b) {
         FutureEither.point(s)
      } else {
         FutureEither.failure(Error(message))
      }
   }
}
