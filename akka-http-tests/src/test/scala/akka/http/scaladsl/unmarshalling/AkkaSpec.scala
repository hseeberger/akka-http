package akka.http.scaladsl.unmarshalling

import akka.actor.ActorSystem
import akka.stream.{ ActorMaterializer, Materializer }
import org.scalatest.{ BeforeAndAfterAll, Suite }
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

trait AkkaSpec extends BeforeAndAfterAll { this: Suite ⇒

  protected implicit val system: ActorSystem =
    ActorSystem()

  protected implicit val mat: Materializer =
    ActorMaterializer()

  override protected def afterAll() = {
    Await.ready(system.terminate(), 42.seconds)
    super.afterAll()
  }
}
