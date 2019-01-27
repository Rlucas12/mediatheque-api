package core

import com.github.tminglei.slickpg._

trait PostgresProfile extends ExPostgresProfile
  with PgArraySupport
  with PgDateSupportJoda
  with PgEnumSupport {
  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  override val api = MyAPI

  object MyAPI extends API
    with ArrayImplicits
    with DateTimeImplicits {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
  }
}

object PostgresProfile extends PostgresProfile