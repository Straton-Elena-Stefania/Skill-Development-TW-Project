FROM postgres:latest
MAINTAINER test

COPY init.sql /docker-entrypoint-initdb.d/
RUN chmod a+r /docker-entrypoint-initdb.d/*

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD hannahmontana
ENV POSTGRES_DB skivi