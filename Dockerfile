FROM dbpostgresql

ADD scripts/initdb.sql /docker-entrypoint-initdb.d
