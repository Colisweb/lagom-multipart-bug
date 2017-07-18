package multipart.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.Service.{named, restCall}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait UploadService extends Service {

  def uploadFile(): ServiceCall[NotUsed, Done]

  override def descriptor: Descriptor = {
    named("upload-service")
      .withCalls(
        restCall(Method.POST, "/api/files", uploadFile _)
      )
      .withAutoAcl(true)
  }

}
