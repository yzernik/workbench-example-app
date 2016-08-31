name := "Soap bubbles"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(ScalaJSPlugin)


libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

workbenchSettings
updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

// Workbench has to know how to restart your application.
bootSnippet := "example.ScalaJSExample().doDynContent();"