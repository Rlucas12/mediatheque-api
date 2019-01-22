name := "mediatheque-api"
version := "0.1"
scalaVersion := "2.12.8"


val circeVersion = "0.10.0"
val circeValidationVersion = "0.0.5"

libraryDependencies ++= Seq(
  "com.softwaremill.macwire" %% "macros" % "2.3.1" % "provided",

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "io.tabmo" %% "circe-validation-core" % circeValidationVersion,
  "io.tabmo" %% "circe-validation-extra-rules" % circeValidationVersion,
  "io.tabmo" %% "circe-validation-extra-rules-joda" % circeValidationVersion,

  "com.dripower" %% "play-circe" % "2610.0"
)

resolvers ++= Seq(
  Resolver.bintrayRepo("tabmo", "maven")
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)