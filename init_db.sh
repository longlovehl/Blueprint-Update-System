#!/bin/bash

HOST="localhost"
USER=postgres
PORT=5432
PASSWD=12345678
DB="long"

echo "initializing database";

psql -U $USER -h $HOST -p $PORT -f create_db.sql;

psql -U $USER -h $HOST -p $PORT -d $DB -f long.sql;