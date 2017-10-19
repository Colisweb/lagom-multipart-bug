package multipart.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

object UploadServiceTopics {
  val FIRST_TOPIC = "first_topic"

  final case class FirstTopicMessage(id: String)
  object FirstTopicMessage {
    implicit val format: Format[FirstTopicMessage] = Json.format[FirstTopicMessage]
  }
}

trait UploadService extends Service {

  import UploadServiceTopics._
  import Service._

  def uploadFile(): ServiceCall[NotUsed, Done]

  def firstTopic(): Topic[FirstTopicMessage]

  override def descriptor: Descriptor = {
    named("upload-service")
      .withCalls(
        restCall(Method.POST, "/api/files", uploadFile _)
      )
      .withTopics(
        topic(FIRST_TOPIC, firstTopic())
      )
      .withAutoAcl(true)
  }

}
