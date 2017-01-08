package optrak.experimental.favouritesongs

import akka.actor.{ActorSystem, PoisonPill, Props}
import optrak.experimental.favouritesongs.model.{Band, Musician}
import optrak.scalautils.test.TestKitSpec
import org.specs2.mutable.SpecificationLike
import cats.implicits._
import grizzled.slf4j.Logging
import optrak.experimental.favouritesongs.model.Song

class PersistentFavouritesListSpec extends TestKitSpec(ActorSystem("FavListTestSystem")) with SpecificationLike with Logging {
  val oasis = Band("Oasis", Set(Musician("Noel", "Gallagher".some), Musician("Liam", "Gallagher".some), Musician("Bonehead", None)))
  val tomWaits = Musician("Tom", "Waits".some)

  val whatever = Song("Whatever", oasis)
  val wonderwall = Song("Wonderwall", oasis)
  val chocolateJesus = Song("Chocolate Jesus", tomWaits)


  "Favourites list" should {
    "work" in {
      val favListActor = system.actorOf(Props[PersistentFavouritesList])

      favListActor ! AddSong(whatever)
      favListActor ! AddSong(chocolateJesus)
      favListActor ! GetSongs
      expectMsg(List(chocolateJesus, whatever))

      favListActor ! PoisonPill

      val newFavListActor = system.actorOf(Props[PersistentFavouritesList])
      newFavListActor ! GetSongs
      expectMsg(List(chocolateJesus, whatever))

      newFavListActor ! RemoveSongs(Set("Chocolate Jesus", "Can't Stop"))
      newFavListActor ! AddSong(wonderwall)
      newFavListActor ! GetSongs
      expectMsg(List(wonderwall, whatever))

      newFavListActor ! RemoveAll
      newFavListActor ! GetSongs
      expectMsg(List.empty[Song])

      ok
    }
  }

}
