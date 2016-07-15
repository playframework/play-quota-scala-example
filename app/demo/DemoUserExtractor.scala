/*
 * Copyright © 2016 Lightbend, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form or
 * by any means without the express written permission of Lightbend, Inc.
 */
package demo

import com.lightbend.correlation.CorrelationId
import com.lightbend.quota.sapi.User
import play.api.mvc.RequestHeader
import play.quota.sapi.user.UserExtractor

import scala.concurrent.Future

final class DemoUserExtractor extends UserExtractor {
  def userForRequest(rh: RequestHeader)(implicit correlationId: CorrelationId): Future[Option[User]] = {
    val user = rh.queryString.get("username").flatMap(_.headOption) match {
      case Some(username) => User("username:"+username)
      case None => User("unknown")
    }
    println(s"$correlationId -- user: $user")
    Future.successful(Some(user))
  }
}
