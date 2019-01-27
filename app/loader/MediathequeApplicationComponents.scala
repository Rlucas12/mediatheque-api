package loader

import com.softwaremill.macwire.wire
import controllers.{AssetsComponents, MediathequeController, ShowController}
import core.{DbExecutorFromDatabase, PostgresProfile}
import daos.slick.ShowDAOSlick
import play.api.BuiltInComponentsFromContext
import play.api.db.evolutions.{DynamicEvolutions, EvolutionsComponents}
import play.api.db.slick.{DbName, DefaultSlickApi, SlickApi }
import play.api.db.slick.evolutions.SlickEvolutionsComponents
import play.api.routing.Router
import router.Routes
import services.impl.ShowServiceImpl

trait MediathequeApplicationComponents extends BuiltInComponentsFromContext
  with AssetsComponents
  with SlickEvolutionsComponents
  with EvolutionsComponents
  with play.filters.HttpFiltersComponents {


  // Database
  override lazy val api: SlickApi = new DefaultSlickApi(environment, configuration, applicationLifecycle)
  implicit lazy val dbExecutor = new DbExecutorFromDatabase(api.dbConfig[PostgresProfile](DbName("default")))
  override lazy val dynamicEvolutions: DynamicEvolutions = new DynamicEvolutions
  applicationEvolutions.start()

  // Controllers
  lazy val mediathequeController = wire[MediathequeController]
  lazy val showController = wire[ShowController]

  // Services
  lazy val showService = wire[ShowServiceImpl]

  // DAOs
  lazy val showDAO = wire[ShowDAOSlick]

  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}