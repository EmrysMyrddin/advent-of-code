import Dependencies._

ThisBuild / scalaVersion     := "2.13.16"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

ThisBuild / watchBeforeCommand := Watch.clearScreen

lazy val root = (project in file("."))
  .settings(
    name := "Advent of Code 2025",
    libraryDependencies += munit % Test,
    libraryDependencies += "org.scala-lang" %% "toolkit" % "0.7.0"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
