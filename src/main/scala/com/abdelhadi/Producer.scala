package com.abdelhadi

import java.util.Properties
import java.util.concurrent.Future

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import scala.util.{Failure, Success, Try}


object Producer {
  val INPUT_TOPIC = "quickstart-events"

  private val props: Properties = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks", "all")

  private val producer = new KafkaProducer[String, String](props)

  def sendMessage(message: String): Try[Future[RecordMetadata]] = {
    try {
      val record = new ProducerRecord[String, String](INPUT_TOPIC, message.hashCode.toString, message)
      Success(producer.send(record))
    } catch {
      case e: Exception => Failure(e)
    }
  }

  def sendMessages(n: Int): Unit = {
    for (i <- 0 to n) {
      val metadata = sendMessage(s"Hello there ! $i")
      metadata match {
        case Success(metadata) => printf(s"sent record at %s meta(partition=%d, offset=%d)\n",
          metadata.get().timestamp(),
          metadata.get().partition(),
          metadata.get().offset())
        case Failure(exception) => println("Sending message failed ! " + exception.getLocalizedMessage)
      }
    }
  }

  def close(): Unit = {
    producer.close();
  }

  def main(args: Array[String]): Unit = {
    sendMessages(10)
    close()
  }
}
