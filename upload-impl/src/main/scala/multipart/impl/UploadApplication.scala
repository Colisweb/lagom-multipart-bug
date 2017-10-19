package multipart.impl

import java.time.Clock

import com.lightbend.lagom.internal.client.CircuitBreakerMetricsProviderImpl
import com.lightbend.lagom.scaladsl.api.Descriptor
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import multipart.api.UploadService
import play.api.libs.ws.ahc.AhcWSComponents

abstract class UploadApplication(context: LagomApplicationContext)
    extends LagomApplication(context)
    with LagomServerComponents
    with AhcWSComponents {

  implicit val clock: Clock = Clock.systemUTC()

  override lazy val lagomServer: LagomServer = serverFor[UploadService](wire[UploadServiceImpl])

}

class UploadApplicationLoader extends LagomApplicationLoader {
  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new UploadApplication(context) with LagomDevModeComponents

  override def load(context: LagomApplicationContext): LagomApplication =
    new UploadApplication(context) with LagomDevModeComponents {
      override lazy val circuitBreakerMetricsProvider = new CircuitBreakerMetricsProviderImpl(actorSystem)
    }

  override def describeServices: List[Descriptor] = readDescriptor[UploadService] :: Nil

}
