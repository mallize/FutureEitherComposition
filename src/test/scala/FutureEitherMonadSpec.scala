import net.clementmatthew.util.FutureEither
import org.scalatest.WordSpecLike

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

class FutureEitherMonadSpec extends org.scalatest.Suite with WordSpecLike {


   "FutureEither" should {
      "compose and yield string" in {
         val res: FutureEither[Error, String] =
            for {
               a <- truth(true, "a = success")
               b <- truth(a, "b = success")
               c <- truth(b, "c = success")
               d <- truthString(c, "my string", "d = success")
               e <- if(d.isInstanceOf[String]) truth(true, "e = success") else truth(false, "e = failed")
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
            error => assert(true),
            success => assert(false)
         )
      }

      "compose with flatmap and yield boolean" in {
         val result: FutureEither[Error, Boolean] =
            truth(true, "a = success")
               .flatMap(truth(_, "b = success"))
               .flatMap(truth(_, "c = success"))
               .flatMap(truth(_, "d = success"))

         result.fold(
            error => assert(true),
            success => assert(success)
         )
      }

      "compose with future(s) and yield boolean" in {
         val result =
         futE("a fut")
            .flatMap(truth(true, _))
            .flatMap(truth(_, "c"))
            .flatMap(truth(_, "d"))
            .flatMap(truth(_, "e"))

         result.fold(
            error => assert(true),
            success => assert(success)
         )
      }

      "compose with future(s) for comp and yield boolean" in {
         val result =
            for {
               a <- futE("a fut")
               b <- truth(true, a)
               c <- truth(b, "c = success")
            } yield c

         result.fold(
            error => assert(true),
            success => assert(success)
         )
      }
   }

   case class Error(message: String = "Error String")

   def futE(s: String): FutureEither[Error, String] = {
      if (s == null) FutureEither.failure(Error("no string"))
      else {
         val it: Future[FutureEither[Error, String]] =
            fut(s).map(r =>
               if (r == null) FutureEither.failure(Error(""))
               else FutureEither.success(r))

         FutureEither.fromFuture(it)
      }
   }

   def fut(s: String): Future[String] = Future { s }

   def truth(b: Boolean, message: String = ""): FutureEither[Error, Boolean] = {
      println(s"truth message = $message")
      if (b) {
         FutureEither.success(true)
      } else {
         FutureEither.failure(Error(message))
      }
   }

   def truthString(b: Boolean, s: String, message: String = ""): FutureEither[Error, String] = {
      println(s"truth message = $s -> $message")
      if (b) {
         FutureEither.success(s)
      } else {
         FutureEither.failure(Error(message))
      }
   }
}
