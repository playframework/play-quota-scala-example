package demo

import com.lightbend.correlation._
import play.api.mvc.RequestHeader
import play.quota.sapi.correlation.CorrelationIdExtractor

class DemoCorrelationIdExtractor extends CorrelationIdExtractor {
  def correlationIdForRequest(rh: RequestHeader): CorrelationId = {
    val id: String = "[" + System.nanoTime + " " + rh.method + " " + rh.path + "]"
    new StringCorrelationId(id)
  }
}
