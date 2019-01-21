import com.softwaremill.macwire.wire
import controllers.{AssetsComponents, HomeController}
import play.api.ApplicationLoader.Context
import play.api.BuiltInComponentsFromContext
import play.api.routing.Router
import router.Routes

class MediathequeApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with AssetsComponents
  with play.filters.HttpFiltersComponents {

  lazy val homeController = wire[HomeController]

  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}