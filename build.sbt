name := "mediatheque-api"
version := "0.1"
scalaVersion := "2.12.8"


libraryDependencies ++= Seq(
  "com.softwaremill.macwire" %% "macros" % "2.3.1" % "provided"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)