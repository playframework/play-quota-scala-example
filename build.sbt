resolvers += "com-mvn" at "https://repo.lightbend.com/commercial-releases/"
resolvers += Resolver.url(“com-ivy",url("https://lightbend.bintray.com/commercial-releases/"))(Resolver.ivyStylePatterns)

name := "play-quota-scala"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion in ThisBuild := "2.11.8"

libraryDependencies ++= Seq(
  specs2 % Test
)

//#quota-dependency
libraryDependencies += "com.lightbend.quota" %% "play-quota" % "1.0.0"
//#quota-dependency

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

