
lazy val root = (project in file(".")).enablePlugins(ScalaJSPlugin)

workbenchSettings

name := "Soap bubbles"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq("org.scala-js" %%% "scalajs-dom" % "0.8.1")

bootSnippet := "ScalaJSExample().main(document.getElementById('canvas'));"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

// Workbench has to know how to restart your application.
bootSnippet := "example.ScalaJSExample().doDynContent();"
