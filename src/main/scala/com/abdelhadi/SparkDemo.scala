package com.abdelhadi

import com.datastax.spark.connector.{SomeColumns, toRDDFunctions, toSparkContextFunctions}
import org.apache.spark.{SparkConf, SparkContext}

object SparkDemo {
  val cassandraHost = "127.0.0.1"
  val keyspace = "test_ks"
  val table = "employee_by_car_make_and_model"

  def main(args: Array[String]): Unit = {
    //Logger.getRootLogger.setLevel(Level.INFO)
    val sparkConf = new SparkConf()
      .setMaster("spark://abdelhadi:7077")
      .set("spark.executor.memory", "480M")
      .set("spark.driver.cores", "2")
      .set("spark.cassandra.connection.host", cassandraHost)
    sparkConf.setAppName("demo")
    val sc = new SparkContext(sparkConf)
    sc.addJar("./target/scala-2.12/spark-demo-assembly-1.0.jar")
    /*val col = sc.parallelize(Seq(("AAAAAAAA", "DDDD", 8, Seq("555"))))
    col.saveToCassandra(keyspace, table, SomeColumns("car_make","car_model", "id", "phone"))*/
    // Read the table and print its contents:
    val rdd = sc.cassandraTable(keyspace, table)
    rdd.collect().foreach(csr => {
      println(csr.columnValues)
    })/*

    val rawData = sc.textFile(args(0))
    rawData
      .flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .saveAsTextFile(args(1))*/
    sc.stop()
  }
}
