package multipart.impl

import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import com.lightbend.lagom.scaladsl.testkit.ServiceTest.TestServer
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class UploadServiceImplSpec
    extends AsyncWordSpec
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures
    with MockitoSugar {

  lazy val server: TestServer[UploadApplication with LocalServiceLocator] =
    ServiceTest.startServer(ServiceTest.defaultSetup.withCassandra(true)) { ctx =>
      new UploadApplication(ctx) with LocalServiceLocator
    }

  override protected def beforeAll(): Unit = { val _ = server }
  override protected def afterAll(): Unit  = server.stop()

  "true" should {
    "not be false" in {
      true should not be false
    }
  }
}
