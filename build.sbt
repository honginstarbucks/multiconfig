import Common._
import Dependencies._

lazy val seeder = Project(id = "simple-seeder", base = file("framework/seeder"))
  .settings(sharedSettings: _*)
  .dependsOn(common)
  .settings(
    name := "seeder"
  )

lazy val common = Project(id = "common-framework", base = file("framework/common"))
  .settings(sharedSettings: _*)
  .settings(
    name := "common framework"
  )

lazy val root = Project(id = "root", base = file("."))
  .aggregate(common, seeder)
  .dependsOn(seeder)
  .settings(sharedSettings: _*)
  .settings(
    name := "Multi Config Toy",
    version := "0.1"
  )

val startSeeder = TaskKey[Unit]("start-seeder")
startSeeder := {
  (runMain in Compile).toTask(" sbux.ucp.membership.history.producerApp").value
}