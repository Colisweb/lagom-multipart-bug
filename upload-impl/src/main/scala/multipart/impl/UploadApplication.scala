package multipart.impl

import java.time.Clock

import com.lightbend.lagom.internal.client.CircuitBreakerMetricsProviderImpl
import com.lightbend.lagom.scaladsl.api.Descriptor
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import multipart.api.UploadService
import play.api.libs.ws.ahc.AhcWSComponents

import scala.collection.immutable.Seq

abstract class UploadApplication(context: LagomApplicationContext)
    extends LagomApplication(context)
    with LagomServerComponents
    with AhcWSComponents
    with CassandraPersistenceComponents
    with LagomKafkaComponents {

  implicit val clock: Clock = Clock.systemUTC()

  override lazy val lagomServer: LagomServer = serverFor[UploadService](wire[UploadServiceImpl])

  lazy val jsonSerializerRegistry: JsonSerializerRegistry = new JsonSerializerRegistry {
    override def serializers: Seq[JsonSerializer[_]] = Seq.empty[JsonSerializer[_]]
  }

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
