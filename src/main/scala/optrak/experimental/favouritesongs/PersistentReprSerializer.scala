package com.optrak.experimental.favouritesongs

import java.nio.ByteBuffer

import akka.actor.ExtendedActorSystem
import akka.persistence.PersistentRepr
import akka.util.ByteString
import java.nio.charset.Charset
import java.nio.ByteBuffer

import akka.serialization.{Serializer, SerializerWithStringManifest}
import optrak.scalautils.validating.ErrorReports._
import optrak.scalautils.json._
import grizzled.slf4j.Logging
import org.json4s.JValue
import cats.data.Validated._
import optrak.experimental.favouritesongs.FavouritesListCommand


object PersistentReprSerializer extends SimpleJsonParsers with SimpleJsonWriters {
  val UTF8 = Charset.forName("UTF-8")

  val Identifier: Int = ByteBuffer.wrap("PersistentRepr".getBytes(UTF8)).getInt

  case class Mapping(payload: FavouritesListCommand,
                     sequenceNr: Long,
                     persistenceId: String,
                     manifest: String,
                     deleted: Boolean,
                     //senderName: String,
                     writerUuid: String)

  val mappingParser = JsonParser[Mapping]
  val mappingWriter = JsonWriter[Mapping]

  /* todo: write PersistentReprSerializer that would resolve payload serializers using akka serialization
   mechanism */
  class PersistentReprJsonRW(val system: ExtendedActorSystem) {
    val persistentReprParser = new JsonParser[PersistentRepr] {
      def parse(n: JValue)(implicit ctx: ValidationContext) = {
        mappingParser.parse(n).map { x =>
          //val sender = system.provider.resolveActorRef(x.senderName)
          PersistentRepr(
            x.payload,
            x.sequenceNr,
            x.persistenceId,
            x.manifest,
            x.deleted,
            null,
            x.writerUuid)
        }
      }
    }

    val persistentReprWriter = new JsonWriter[PersistentRepr] {
      def write(nameOpt: Option[String], pr: PersistentRepr) = {
        val mapping = Mapping(pr.payload.asInstanceOf[FavouritesListCommand], pr.sequenceNr, pr.persistenceId,
          pr.manifest, pr.deleted, pr.writerUuid)
        logger.debug(s"mapping: $mapping")
        mappingWriter.write(None, mapping)
      }
    }

  }

}


class PersistentReprSerializer(val system: ExtendedActorSystem) extends Serializer with Logging {
  import PersistentReprSerializer._
  import org.json4s.jackson.JsonMethods._

  def identifier = Identifier

  def includeManifest = false

  val prs = new PersistentReprJsonRW(system)

  def fromBinary(bytes: Array[Byte], manifest: Option[Class[_]]): AnyRef = {
    logger.debug("deserializing event")

    val json = parse(new String(bytes, UTF8))
    implicit val headCtx = HeadContext("parsing PersistentRepr")
    prs.persistentReprParser.parse(json) match {
      case Valid(v) => v
      case Invalid(f) =>
        logger.error(f.toString)
        throw new Exception(f.toString)
    }
  }

  def toBinary(o: AnyRef): Array[Byte] = {
    (render _ andThen pretty _)(
      prs.persistentReprWriter.write(None, o.asInstanceOf[PersistentRepr])
    ).getBytes(UTF8)
  }

}