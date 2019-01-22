package loader

import com.softwaremill.macwire.wire
import controllers.{AssetsComponents, MediathequeController, ShowController}
import play.api.ApplicationLoader.Context
import play.api.BuiltInComponentsFromContext
import play.api.routing.Router
import router.Routes
import services.impl.ShowServiceImpl

class MediathequeApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with AssetsComponents
  with play.filters.HttpFiltersComponents {

  // Controllers
  lazy val mediathequeController = wire[MediathequeController]
  lazy val showController = wire[ShowController]

  // Services
  lazy val showService = wire[ShowServiceImpl]

  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}