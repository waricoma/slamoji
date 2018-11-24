name := "slamoji"

version := "0.1"

scalaVersion := "2.12.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

resolvers += "CodeLibs Repository" at "http://maven.codelibs.org/"

libraryDependencies += "com.github.slack-scala-client" %% "slack-scala-client" % "0.2.4"
libraryDependencies += "org.codelibs" % "lucene-analyzers-kuromoji-ipadic-neologd" % "6.0.0-20160519"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"       % "3.0.2",
  // "org.scalikejdbc" %% "scalikejdbc-interpolation" % "1.7.5",
  "com.h2database"  %  "h2"                % "1.4.196",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3",
  "mysql" % "mysql-connector-java" % "5.1.43",
  "org.xerial" % "sqlite-jdbc" % "3.7.2"
)

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.5"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.12"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.6.2"
