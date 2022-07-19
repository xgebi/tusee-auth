import sbt.Keys.libraryDependencies

val ScalatraVersion = "2.8.2"

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "app.tusee"

lazy val hello = (project in file("."))
  .settings(
    name := "Tusee Auth",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.2.11" % "runtime",
      "org.eclipse.jetty" % "jetty-webapp" % "11.0.9" % "container",
      "javax.servlet" % "javax.servlet-api" % "4.0.1" % "provided",
      "org.scalatra" %% "scalatra-json" % "2.8.2",
      "org.json4s"   %% "json4s-jackson" % "4.0.5",
      "com.typesafe.slick" %% "slick" % "3.3.3",
      "org.slf4j" % "slf4j-nop" % "1.7.36",
      "org.postgresql" % "postgresql" % "42.4.0",
      "com.mchange" % "c3p0" % "0.9.5.5"
    ),
    libraryDependencies += "org.bitbucket.b_c" % "jose4j" % "0.7.12",
    libraryDependencies += "de.mkammerer" % "argon2-jvm" % "2.11",
    libraryDependencies += "org.scalamock" %% "scalamock" % "5.2.0" % Test,
  )

enablePlugins(SbtTwirl)
enablePlugins(JettyPlugin)

containerPort in Jetty := 5001