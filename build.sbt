name := "PiMonteCarlo"

version := "0.1"

scalaVersion := "2.12.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

lazy val akkaVersion = "2.5.3"

libraryDependencies  ++= Seq(
  "org.scalanlp" %% "breeze" % "0.13",
  "org.scalanlp" %% "breeze-natives" % "0.13",
  "org.ddahl" %% "rscala" % "2.5.0",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
