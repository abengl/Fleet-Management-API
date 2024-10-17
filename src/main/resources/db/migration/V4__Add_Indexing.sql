-- This index improves the performance of queries filtering by taxi_id and date.
-- This file needs to be moved into db/migration after all data has been loaded.
CREATE INDEX idx_taxi_id_date ON api.trajectories (taxi_id, date DESC);