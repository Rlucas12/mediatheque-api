package controllers

import play.api.libs.circe.Circe
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class MediathequeController(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) with Circe {

  def index = Action {
    Ok(Json.toJson("Hello from HomeController"))
  }

}
