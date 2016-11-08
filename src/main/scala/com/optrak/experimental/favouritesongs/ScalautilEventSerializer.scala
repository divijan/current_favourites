package com.optrak.experimental.favouritesongs

import java.nio.charset.StandardCharsets

import akka.serialization.SerializerWithStringManifest
import com.optrak.scalautil.json._
import com.optrak.scalautil.CatsBits.{HeadContext, ValidationContext, stackSafeSequence}
import org.json4s.{JsonWriter => _, _}
import org.json4s.jackson.JsonMethods._

object ScalautilEventSerializer extends SimpleParsers with SimpleWriters {

  val eventWriter = JsonWriter[FavouritesListCommand]
  val testEventParser = JsonParser[FavouritesListCommand]
}

class ScalautilEventSerializer extends SerializerWithStringManifest {
  import ScalautilEventSerializer._

  val AddSongManifest = "addSong"
  val RemoveSongsManifest = "removeSongs"
  val RemoveAllManifest = "removeAll"
  val UTF_8 = StandardCharsets.UTF_8.name()

  // Pick a unique identifier for your Serializer,
  // you've got a couple of billions to choose from,
  // 0 - 16 is reserved by Akka itself
  def identifier = 1234567

  // The manifest (type hint) that will be provided in the fromBinary method
  // Use `""` if manifest is not needed.
  def manifest(obj: AnyRef): String =
    obj match {
      case _: AddSong     => AddSongManifest
      case _: RemoveSongs => RemoveSongsManifest
      case RemoveAll      => RemoveAllManifest
    }

  // "toBinary" serializes the given object to an Array of Bytes
  def toBinary(obj: AnyRef): Array[Byte] = {
    val ast = obj match {
      case as: AddSong => JsonWriter[AddSong].write(None, as)
      case rs: RemoveSongs => JsonWriter[RemoveSongs].write(None, rs)
      case RemoveAll => JObject()
    }
    compact(render(ast)).getBytes(UTF_8)
  }

  // "fromBinary" deserializes the given array,
  // using the type hint
  def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = {
    // Put the real code that deserializes here
    val jsonAst = parse(new String(bytes, UTF_8))
    manifest match {
      case AddSongManifest => JsonParser[AddSong].parse(jsonAst)(HeadContext("Parsing AddSong from persistence"))
      case RemoveSongsManifest => JsonParser[RemoveSongs].parse(jsonAst)(HeadContext("Parsing AddSong from persistence"))
      case RemoveAllManifest => RemoveAll
    }
  }
}

