--this needs to correspond to schemaName setting for every table under akka-persistence-jdbc.tables in config
--Run this script on behalf of the user whose credentials will be used when connecting to a DB
CREATE SCHEMA IF NOT EXISTS current_favourites;

DROP TABLE IF EXISTS current_favourites.journal;

CREATE TABLE IF NOT EXISTS current_favourites.journal (
  ordering BIGSERIAL,
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  deleted BOOLEAN DEFAULT FALSE,
  tags VARCHAR(255) DEFAULT NULL,
  message BYTEA NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);

DROP TABLE IF EXISTS current_favourites.snapshot;

CREATE TABLE IF NOT EXISTS current_favourites.snapshot (
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  created BIGINT NOT NULL,
  snapshot BYTEA NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);