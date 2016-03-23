import net.clementmatthew.util.FutureEither
import org.scalatest.WordSpecLike

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class FutureEitherSpec extends org.scalatest.Suite with WordSpecLike {

   "FutureEither" should {
      "compose and yield string" in {



      }
   }

   case class Error(message: String = "Error String")

   def truth(b: Boolean, message: String = ""): Future[Either[Error, Boolean]] = {
      println(s"truth message = $message")
      if (b) {
         Future.successful(Right(true))
      } else {
         Future.successful(Left(Error(message)))
      }
   }

   def truthString(b: Boolean, s: String, message: String = ""): Future[Either[Error, String]] = {
      println(s"truth message = $message")
      if (b) {
         Future.successful(Right(s))
      } else {
         Future.successful(Left(Error(message)))
      }
   }
}
