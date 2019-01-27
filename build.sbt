name := "mediatheque-api"
version := "0.1"
scalaVersion := "2.12.8"


val circeVersion = "0.10.0"
val circeValidationVersion = "0.0.5"
val slickVersion = "3.0.0"
val tmingleiVersion = "0.17.0"

libraryDependencies ++= Seq(
  jdbc,

  "com.softwaremill.macwire" %% "macros" % "2.3.1" % "provided",

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "io.tabmo" %% "circe-validation-core" % circeValidationVersion,
  "io.tabmo" %% "circe-validation-extra-rules" % circeValidationVersion,
  "io.tabmo" %% "circe-validation-extra-rules-joda" % circeValidationVersion,

  "com.dripower" %% "play-circe" % "2610.0",

  "com.typesafe.play" %% "play-slick" % slickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % slickVersion,
  "org.postgresql" % "postgresql" % "42.2.5",

  "com.github.tminglei" %% "slick-pg" % tmingleiVersion,
  "com.github.tminglei" %% "slick-pg_joda-time" % tmingleiVersion,
  "com.github.tminglei" %% "slick-pg_circe-json" % tmingleiVersion

)

resolvers ++= Seq(
  Resolver.bintrayRepo("tabmo", "maven")
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)