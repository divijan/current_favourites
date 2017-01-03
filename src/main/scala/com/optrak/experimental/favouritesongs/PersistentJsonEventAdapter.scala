/*
package com.optrak.experimental.favouritesongs

import java.nio.charset.StandardCharsets

import akka.persistence.journal.{EventAdapter, EventSeq}
import com.optrak.experimental.favouritesongs.model._
import com.optrak.scalautil.CatsBits.HeadContext
import com.optrak.scalautil.json._
import org.json4s.{JObject, JValue}


class PersistentJsonEventAdapter extends EventAdapter {
  val AddSongManifest = "addSong"
  val RemoveSongsManifest = "removeSongs"
  val RemoveAllManifest = "removeAll"
  val UTF_8 = StandardCharsets.UTF_8.name()

  override def manifest(event: Any): String = event match {
    case as: AddSong => AddSongManifest
    case rs: RemoveSongs => RemoveSongsManifest
    case RemoveAll => RemoveAllManifest
  }

  override def toJournal(event: Any): Any = event match {
    case as: AddSong => JsonWriter[AddSong].write(None, as)
    case rs: RemoveSongs => JsonWriter[RemoveSongs].write(None, rs)
    case RemoveAll => JObject()
  }


  override def fromJournal(event: Any, manifest: String): EventSeq ={
    val ast = event.asInstanceOf[JValue]
    val evt = manifest match {
      case AddSongManifest => JsonParser[AddSong].parse(ast)(HeadContext("Parsing AddSong from persistence"))
      case RemoveSongsManifest => JsonParser[RemoveSongs].parse(ast)(HeadContext("Parsing RemoveSongs from persistence"))
      case RemoveAllManifest => RemoveAll
    }
    EventSeq.single(evt)
  }
}

*/
