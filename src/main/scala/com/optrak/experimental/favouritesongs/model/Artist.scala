package com.optrak.experimental.favouritesongs.model

sealed trait Artist

case class Musician(firstName: String, lastName: Option[String]) extends Artist

case class Band(name: String, members: Set[Musician]) extends Artist
