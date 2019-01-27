package core

import slick.dbio.{DBIOAction, NoStream}

trait DbContext {

  /**
    * DBExecutor used to commit the DBIO
    * Provides thought DI
    */
  protected val dbExecutor: DbExecutor

  /**
    * Convenient shortcut for commiting a DBIO
    */
  implicit class DBIOActionExecutor[R](dbio: DBIOAction[R, NoStream, Nothing]) {
    def commit() = dbExecutor.commit(dbio)
    def commitInTx() = dbExecutor.commitInTx(dbio)
  }

}