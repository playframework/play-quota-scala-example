/*
 * Copyright © 2016 Lightbend, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form or
 * by any means without the express written permission of Lightbend, Inc.
 */
package demo

import java.time.{Duration, Instant}

import com.lightbend.correlation.CorrelationId
import com.lightbend.quota.sapi.judge.{Access, AccessDenied, AccessGranted}
import com.lightbend.quota.sapi._
import play.api.mvc._
import play.quota.sapi.format.ResultFormatter

import scala.concurrent.Future

class DemoResultFormatter extends ResultFormatter {

  override def formatGranted(now: Instant, user: User, requestCost: Int, balance: Option[Int], quota: Quota, result: Result)(implicit correlationId: CorrelationId): Future[Result] = {
    display(AccessGranted, now, user, requestCost, balance, quota, correlationId)
  }

  override def formatDenied(now: Instant, user: User, requestCost: Int, balance: Option[Int], quota: Quota)(implicit correlationId: CorrelationId): Future[Result] = {
    display(AccessDenied, now, user, requestCost, balance, quota, correlationId)
  }

  private def display(access: Access, now: Instant, user: User, requestCost: Int, balance: Option[Int], quota: Quota, correlationId: CorrelationId): Future[Result] = {
    import DemoResultFormatter._
    val quotaDisplay: QuotaDisplay = quota match {
      case Zero => ZeroDisplay
      case Unlimited => UnlimitedDisplay
      case rl: RateLimited =>
        assert(rl.maxBalance == rl.refillAmount)
        val tickSeconds = rl.tickSize / 1000 // Convert millis to seconds
        val nextRefill: Instant = rl.nextRefillTime(now)
        val secondsUntilRefill = Duration.between(now, nextRefill).getSeconds
        RateLimitedDisplay(secondsUntilRefill, tickSeconds, balance.get, rl.maxBalance)

    }
    Future.successful(Results.Ok(views.html.demo(access, user, requestCost, quotaDisplay, correlationId)))
  }

}

object DemoResultFormatter {

  sealed trait QuotaDisplay
  final case object ZeroDisplay extends QuotaDisplay
  final case object UnlimitedDisplay extends QuotaDisplay
  final case class RateLimitedDisplay(
    secondsUntilRefill: Long,
    tickSeconds: Long,
    balance: Int,
    maxBalance: Int
  ) extends QuotaDisplay

}
