lazy val root = (project in file(".")).
  settings(
    name := "spark-demo",
    version := "1.0",
    scalaVersion := "2.12.0",
    mainClass in Compile := Some("com.abdelhadi.SparkDemo"),
    mainClass in assembly := Some("com.abdelhadi.SparkDemo")
  )

scalaVersion := "2.12.10"
val sparkVersion = "3.0.1"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "3.0.0"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector-assembly" % "3.0.0"
// https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.6.0"
// https://mvnrepository.com/artifact/com.github.jnr/jnr-posix
libraryDependencies += "com.github.jnr" % "jnr-posix" % "3.1.2"
// https://mvnrepository.com/artifact/joda-time/joda-time
libraryDependencies += "joda-time" % "joda-time" % "2.10.6"


// META-INF discarding
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}