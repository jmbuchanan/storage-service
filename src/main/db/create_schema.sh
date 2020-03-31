#!/bin/bash

psql -U ${POSTGRES_USER} -d storage_site -f schema.sql