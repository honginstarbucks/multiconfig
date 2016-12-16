import sbt.Keys._
import sbt._

object Version {
  lazy val akkaVersion = "2.4.11"
  lazy val akkaCirceVersion = "1.10.0"
  lazy val catsVersion = "0.7.2"
  lazy val shapelessVersion = "2.3.2"
  lazy val refinedVersion = "0.5.0"
  lazy val monixVersion = "2.0.0"
  lazy val circeVersion = "0.6.0"
  lazy val phantomVersion = "2.0.6"
  lazy val logbackVersion = "1.1.3"
  lazy val scalaParserVersion = "1.0.4"
  lazy val scalatestVersion = "3.0.1"
  lazy val scalacheckVersion = "1.13.4"
  lazy val scalamockVersion = "3.4.2"
}

// ---------------------------------------------------------
// shareable dependencies
// ---------------------------------------------------------
object Dependencies {

  import Version._
  // ---------------------------------------------------------
  // shareable dependencies
  // ---------------------------------------------------------
  lazy val commonDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,

    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.scala-lang.modules" %% "scala-parser-combinators" % scalaParserVersion,

    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,

    // for testing
    "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    "org.scalactic" %% "scalactic" % scalatestVersion,
    "org.scalamock" %% "scalamock-scalatest-support" % scalamockVersion % "test",
    "org.scalacheck" %% "scalacheck" % scalacheckVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  )

  lazy val phantomDependencies = Seq(
    "com.outworkers" %% "phantom-dsl" % phantomVersion,
    "com.outworkers" %% "phantom-reactivestreams" % phantomVersion,
    "com.outworkers" %% "phantom-jdk8" % phantomVersion,
    "com.outworkers" %% "util-testing" % "0.18.2" % Test
  )

  lazy val httpDependencies = Seq(
    "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-circe" % "1.10.1"
  )

  lazy val akkaStreamDependencies = Seq(
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-kafka" % "0.13",

    "org.apache.kafka" %% "kafka" % "0.10.0.1",
    "org.apache.kafka" % "kafka-clients" % "0.10.0.1"
  )
}

object Common {
  import Dependencies._

  lazy val compileOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-language:_"
  )

  // -----------------------------
  // resolvers (source repositories)
  // -----------------------------
  val sharedSettings: Seq[Def.Setting[_]] = Defaults.coreDefaultSettings ++ Seq(
    organization := "starbucks",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.10.6", "2.11.8"),
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.sonatypeRepo("releases"),
      Resolver.bintrayRepo("outworkers", "oss-releases"),
      Resolver.sonatypeRepo("snapshots")
    ),
    scalacOptions in ThisBuild ++= Seq(
      "-language:_",
      "-Yinline-warnings",
      "-Xlint",
      "-deprecation",
      "-feature",
      "-unchecked"
    ),
    logLevel in ThisBuild := Level.Info,
    libraryDependencies ++= commonDependencies,
    fork in Test := true,
    javaOptions ++= Seq(
      "-Xmx1G",
      "-Djava.net.preferIPv4Stack=true",
      "-Dio.netty.resourceLeakDetection"
    ),
    //testFrameworks in PerformanceTest := Seq(new TestFramework("org.scalameter.ScalaMeterFramework")),
    //testOptions in Test := Seq(Tests.Filter(x => !performanceFilter(x))),
    //testOptions in PerformanceTest := Seq(Tests.Filter(x => performanceFilter(x))),
    //fork in PerformanceTest := false,
    parallelExecution in ThisBuild := false
  )

}