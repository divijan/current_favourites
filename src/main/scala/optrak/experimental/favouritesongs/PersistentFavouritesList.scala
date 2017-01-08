package optrak.experimental.favouritesongs

import akka.persistence.PersistentActor
import optrak.experimental.favouritesongs.model.Artist
import optrak.experimental.favouritesongs.model.Song

sealed trait FavouritesListCommand

case class AddSong(song: Song) extends FavouritesListCommand

case class RemoveSongs(names: Set[String]) extends FavouritesListCommand

case object RemoveAll extends FavouritesListCommand

case object GetSongs extends FavouritesListCommand

class PersistentFavouritesList extends PersistentActor {
  override val persistenceId = "favs"

  var favList = List.empty[Song]

  def updateState(cmd: FavouritesListCommand) = cmd match {
    case AddSong(song) => favList = song :: favList
    case RemoveSongs(names) => favList = favList.filterNot(names contains _.name)
    case RemoveAll => favList = List.empty[Song]
    case GetSongs =>
  }

  override def receiveCommand: Receive = {
    case as @ AddSong(song) => if (!favList.contains(song)) persist(as)(updateState)
    case rs @ RemoveSongs(names) => persist(RemoveSongs(names intersect (favList.map(_.name).toSet)))(updateState)
    case RemoveAll => if (favList.nonEmpty) persist(RemoveAll)(updateState)
    case GetSongs => sender ! favList
  }

  override def receiveRecover: Receive = {
    case x: FavouritesListCommand => updateState(x)
  }
}
