package controllers

//#quota-action
import javax.inject._
import play.api._
import play.api.mvc._
import play.quota.sapi._

class Application @Inject() (quotaAction: QuotaAction) extends Controller {

  def index = quotaAction {
    Ok(views.html.index("Hello world!"))
  }

}
//#quota-action