package multipart.impl

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

object UploadEvents {

  sealed trait UploadEvent extends AggregateEvent[UploadEvent] {
    override def aggregateTag: AggregateEventTagger[UploadEvent] = UploadEvent.Tag
  }

  object UploadEvent {
    val Tag: AggregateEventTag[UploadEvent] = AggregateEventTag[UploadEvent]
  }

  final case class UploadCreated(id: String)
  object UploadCreated {
    implicit val format: Format[UploadCreated] = Json.format[UploadCreated]
  }

}
