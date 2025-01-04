ThisBuild / scalaVersion := "2.12.15"
val sparkVersion = "3.2.3"

ThisBuild / version := "0.1.0"
ThisBuild / organization := "com.demoSparkScalaSBT"
ThisBuild / resolvers ++= Seq(
      "Maven Central" at "https://repo1.maven.org/maven2/",
    )

lazy val root = (project in file("."))
  .settings(
    name := "demoSparkScalaSBT",
    Compile / mainClass := Some("com.demoSparkScalaSBT.app.MainApp"),
    run / mainClass := Some("com.demoSparkScalaSBT.app.MainApp"),
    assembly / mainClass := Some("com.demoSparkScalaSBT.app.MainApp"),
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion,
      "org.apache.spark" %% "spark-sql" % sparkVersion,
      "com.typesafe" % "config" % "1.4.2",
      "org.scalatest" %% "scalatest-funsuite" % "3.2.11" % Test,
      "com.holdenkarau" %% "spark-testing-base" % "3.2.3_2.0.1" % Test,
    ),

    testFrameworks += new TestFramework("org.scalatest.tools.Framework")
  )

// Assembly plugin for building a fat JAR
enablePlugins(sbtassembly.AssemblyPlugin)
Compile / unmanagedJars ++= {
  val libDir = baseDirectory.value / "lib"
  (libDir ** "*.jar").classpath
}
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
Test / parallelExecution := false
Test / fork := true
javaOptions ++= Seq("-Xms8G", "-Xmx8G", "-XX:MaxPermSize=4048M", "-XX:+CMSClassUnloadingEnabled")


