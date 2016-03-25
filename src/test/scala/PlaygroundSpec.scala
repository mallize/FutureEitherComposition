import net.clementmatthew.util.FutureEither
import org.scalatest.WordSpecLike

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class PlaygroundSpec extends org.scalatest.Suite with WordSpecLike {


   "it" should {
      "go" in {
         type Error = String


         def feString(s: String): Future[Either[Error, String]] = Future {
            Right(s)
         }

         def feUnit(s: String): Future[Either[Error, Unit]] = Future {
            Right(Unit)
         }

      }


   }
}
