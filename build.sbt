import java.io.File

import BuildUtils._

lazy val jsFileNameToken = settingKey[String]("Token in HTML that should be replaced in output HTML files.")

lazy val mainSourceDirectory = settingKey[File]("Defines the main source directory.")

lazy val htmlSourceDirectory = settingKey[File]("Defines the directory in which HTML sources will be kept.")

lazy val assetsSourceDirectory = settingKey[File]("Defines the directory in which non-html static web assets will be kept.")

lazy val devPublicHtml = taskKey[File]("Creates a dev_public_html directory unifying HTML and quickly generated javascript files.")

lazy val publicHtml = taskKey[File]("Creates the public_html directory unifying HTML and javascript files.")

enablePlugins(ScalaJSPlugin)

name := "casaleon.ro-play"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.lihaoyi" %%% "scalatags" % "0.5.2",
  "org.scala-js" %%% "scalajs-dom" % "0.8.0"
)

jsFileNameToken := "@jsFileName@"

mainSourceDirectory := new File( sourceDirectory.value, "main" ) 

assetsSourceDirectory := new File( mainSourceDirectory.value, "assets" ) 

htmlSourceDirectory := new File( mainSourceDirectory.value, "html" ) 

devPublicHtml := createPublicHtmlDirectory( 
  crossTarget.value,
  "dev_public_html",
  jsFileNameToken.value,
  htmlSourceDirectory.value,
  (fastOptJS in Compile).value.data,
  "javascript",
  assetsSourceDirectory.value
)

publicHtml := createPublicHtmlDirectory( 
  crossTarget.value,
  "public_html",
  jsFileNameToken.value,
  htmlSourceDirectory.value,
  (fullOptJS in Compile).value.data,
  "javascript",
  assetsSourceDirectory.value
)



//persistLauncher in Compile := true

//persistLauncher in Test := false



