organization := "com.optrak"

name := "current-favourite-songs" 

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  "optrak repo" at "https://office.optrak.com/code/releases/",
  "optrak thirdparty" at "https://office.optrak.com/code/thirdparty/",
  "osgeo" at "http://download.osgeo.org/webdav/geotools/",
  "dnvriend at bintray" at "http://dl.bintray.com/dnvriend/maven",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

val akkaVersion = "2.4.16"

val specs2Version = "3.8.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.optrak" %% "scala_json" % "latest.integration",
  "ch.qos.logback" % "logback-classic" % "1.1.5",
  "org.clapper" %% "grizzled-slf4j" % "1.3.0",
  "com.github.dnvriend" %% "akka-persistence-jdbc" % "2.6.12-3.2.0-M2",
  "org.postgresql" % "postgresql" % "9.4.1212", //jdbc42 for java 8, 41 for java 7. version looks strange
  "com.optrak" %% "scala_testutils" % "latest.integration" % "test",
  "org.specs2" %% "specs2-core" % specs2Version % "test",
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
)

scalacOptions ++= Seq("-feature", "-deprecation")
