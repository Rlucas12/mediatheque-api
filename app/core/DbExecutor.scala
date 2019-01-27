package core

import slick.basic.DatabaseConfig
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig, HasDatabaseConfigProvider}

import scala.concurrent.Future

class DbExecutorFromProvider()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[PostgresProfile] with DbExecutor

class DbExecutorFromDatabase(protected val dbConfig: DatabaseConfig[PostgresProfile])
  extends HasDatabaseConfig[PostgresProfile] with DbExecutor

trait DbExecutor {
  protected def db: PostgresProfile#Backend#Database

  import PostgresProfile.api._

  def commit[E <: Effect, R, S <: NoStream](dbio: DBIOAction[R, S, E]): Future[R] = db.run(dbio)

  def commitInTx[E <: Effect, R, S <: NoStream](dbio: DBIOAction[R, S, E]): Future[R] = db.run(dbio.transactionally)

  def database = db
}
