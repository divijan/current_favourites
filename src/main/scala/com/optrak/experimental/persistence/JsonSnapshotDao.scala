/*
package com.optrak.experimental.persistence

import akka.persistence.SnapshotMetadata
import akka.persistence.jdbc.snapshot.dao.SnapshotDao

import scala.concurrent.Future

class JsonSnapshotDao extends SnapshotDao {
  def deleteAllSnapshots(persistenceId: String): Future[Unit]
  
  def deleteUpToMaxSequenceNr(persistenceId: String, maxSequenceNr: Long): Future[Unit]
  
  def deleteUpToMaxTimestamp(persistenceId: String, maxTimestamp: Long): Future[Unit]
  
  def deleteUpToMaxSequenceNrAndMaxTimestamp(persistenceId: String, maxSequenceNr: Long, maxTimestamp: Long): Future[Unit]
  
  def snapshotForMaxSequenceNr(persistenceId: String): Future[Option[(SnapshotMetadata, Any)]]
  
  def snapshotForMaxTimestamp(persistenceId: String, timestamp: Long): Future[Option[(SnapshotMetadata, Any)]]
  
  def snapshotForMaxSequenceNr(persistenceId: String, sequenceNr: Long): Future[Option[(SnapshotMetadata, Any)]]
  
  def snapshotForMaxSequenceNrAndMaxTimestamp(persistenceId: String, sequenceNr: Long, timestamp: Long): Future[Option[(SnapshotMetadata, Any)]]
  
  def delete(persistenceId: String, sequenceNr: Long): Future[Unit]
  
  def save(snapshotMetadata: SnapshotMetadata, snapshot: Any): Future[Unit]
}*/
