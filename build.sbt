name := "spark definite guide"
version := "1.0.0"
scalaVersion := "2.12.10"

val dependencies = Seq(
  "org.twitter4j" % "twitter4j-core" % "3.0.5",
  "org.twitter4j" % "twitter4j-stream" % "3.0.5",
  "org.apache.spark" %% "spark-core" % "2.4.0",
  "org.apache.spark" %% "spark-sql" % "2.4.0",
  "org.apache.spark" %% "spark-mllib" % "2.4.0",
  "org.apache.spark" %% "spark-streaming" % "2.4.0"
)
 
lazy val chapter2 = (project in file("chapter2")).settings(libraryDependencies ++= dependencies)

lazy val chapter3 = (project in file("chapter3")).settings(libraryDependencies ++= dependencies)

