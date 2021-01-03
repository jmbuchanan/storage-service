FROM postgres:13-alpine
ENV POSTGRES_DB=storage_site POSTGRES_USER=user POSTGRES_PASSWORD=password
COPY 1_init.sql /docker-entrypoint-initdb.d/
COPY 2_insert_data.sql /docker-entrypoint-initdb.d/
