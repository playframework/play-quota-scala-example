/*
 * Copyright © 2016 Lightbend, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form or
 * by any means without the express written permission of Lightbend, Inc.
 */
package demo

import javax.inject._
import play.api._
import play.api.mvc._
import play.quota.sapi._

class DemoController @Inject() (@Named("demo") quota: QuotaAction) extends Controller {

  def index = quota { Ok("Demo Action Result") }

}
