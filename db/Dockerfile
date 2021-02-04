FROM postgres:13-alpine
COPY 1_init.sql /docker-entrypoint-initdb.d/
COPY 2_insert_data.sql /docker-entrypoint-initdb.d/
