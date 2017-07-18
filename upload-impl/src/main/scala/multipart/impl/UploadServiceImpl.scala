package multipart.impl

import akka.stream.Materializer
import akka.stream.scaladsl.Sink
import akka.util.ByteString
import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.server.PlayServiceCall
import multipart.api.UploadService
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.streams.Accumulator
import play.api.mvc.EssentialAction

import scala.concurrent.{ExecutionContext, Future}

final class UploadServiceImpl(implicit ec: ExecutionContext, materializer: Materializer) extends UploadService {

  private final val log: Logger = LoggerFactory.getLogger(classOf[UploadServiceImpl])

  /**
    * The code come from a James Roper example given here:
    *   https://groups.google.com/forum/#!topic/lagom-framework/HR5RbbqsJr8
    *
    * It does nothing with the files, just answers `Done`
    *
    * @return
    */
  override def uploadFile(): ServiceCall[NotUsed, Done] = {
    log.debug("DEBUG 0")
    PlayServiceCall { (wrapCall) =>
      log.debug("DEBUG 1")
      val res = EssentialAction { requestHeader =>
        log.debug("DEBUG 2")
        val sink: Sink[ByteString, Future[Done]] = Sink.ignore
        log.debug("DEBUG 3")
        val accumulator: Accumulator[ByteString, Done] = Accumulator.apply(sink)
        log.debug("DEBUG 4")
        val res = accumulator.mapFuture { (done: Done) =>
          log.debug("DEBUG 5")
          val wrappedAction: EssentialAction = wrapCall(ServiceCall { notUsed =>
            log.debug("DEBUG 6")
            Future.successful(Done)
          })
          log.debug("DEBUG 7")
          val res = wrappedAction(requestHeader).run()
          log.debug("DEBUG 8")
          res
        }
        log.debug("DEBUG 9")
        res
      }
      log.debug("DEBUG 10")
      res
    }
  }

}
