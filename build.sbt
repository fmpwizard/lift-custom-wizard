name := "custom wizard"

version := "0.1"

scalaVersion := "2.9.1"

seq(webSettings: _*)

resolvers ++= Seq(
  "Scala Tools Releases" at "http://scala-tools.org/repo-releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= {
  val liftVersion = "2.4"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-testkit" % liftVersion % "compile->default"
    )
}

libraryDependencies ++= Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.0.RC4" %   "container; test",
    "ch.qos.logback" % "logback-classic" % "0.9.26"
)

