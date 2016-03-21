name := "FutureEitherComposition"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
   "org.scalatest" %% "scalatest" % "3.0.0-M15",
   "org.scalactic" %% "scalactic" % "3.0.0-M15"
)


resolvers ++= Seq(
   "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
   "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)


//fork in Test := false
