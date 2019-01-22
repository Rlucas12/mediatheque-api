package loader

import play.api.ApplicationLoader.Context
import play.api._

class MediathequeApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }
    new MediathequeApplicationComponents(context)
  }.application
}
