                name := "Soap bubbles"

// KEEP THIS normalizedName CONSTANTLY THE SAME, otherwise the outputted JS filename will be changed.
      normalizedName := "main"

// ** Scala dependencies **
scalaVersion in ThisBuild := "2.11.8"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

// ** Scala.js configuration **
// lazy val root = (project in file(".")).
enablePlugins(ScalaJSPlugin)

// If true, a launcher script src="../[normalizedName]-launcher.js will be generated
// that always calls the main def indicated by the used JSApp trait.
persistLauncher := true
persistLauncher in Test := false

workbenchSettings
updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

// Workbench has to know how to restart your application.
bootSnippet := "example.ScalaJSExample().main();"
