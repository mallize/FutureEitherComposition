name := "FutureEitherComposition"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
   "org.scalatest" %% "scalatest" % "2.2.1",
   "org.scalatestplus" %% "play" % "1.4.0-M3"
)


resolvers ++= Seq(
   "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
   "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

parallelExecution in Test := false

fork in Test := false
