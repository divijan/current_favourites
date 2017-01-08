/*
package com.optrak.experimental.persistence

import akka.NotUsed
import akka.persistence.{AtomicWrite, PersistentRepr}
import akka.persistence.jdbc.journal.dao.JournalDao
import akka.stream.scaladsl.{Flow, Source}

import scala.util.Try
import scala.concurrent.Future

class JsonJournalDao extends JournalDao {
  /**
    * Deletes all persistent messages up to toSequenceNr (inclusive) for the persistenceId
    */
  def delete(persistenceId: String, toSequenceNr: Long): Future[Unit]
  
  /**
    * Returns the highest sequence number for the events that are stored for that `persistenceId`. When no events are
    * found for the `persistenceId`, 0L will be the highest sequence number
    */
  def highestSequenceNr(persistenceId: String, fromSequenceNr: Long): Future[Long]
  
  /**
    * Returns a Source of PersistentRepr for a certain persistenceId
    */
  def messages(persistenceId: String, fromSequenceNr: Long, toSequenceNr: Long, max: Long): Source[Try[PersistentRepr], NotUsed]
  
  /**
    * Writes serialized messages
    */
  def writeFlow: Flow[AtomicWrite, Try[Unit], NotUsed]
}
*/
