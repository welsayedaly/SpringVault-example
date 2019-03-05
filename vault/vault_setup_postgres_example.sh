#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
VAULT_BIN="/usr/local/bin/vault"

 ${VAULT_BIN}  secrets enable database

if [[ $? == 0 ]] ; then

    echo "###########################################################################"
    echo "# Setup PostgreSQL integration                                                 #"
    echo "###########################################################################"

    echo "vault secrets enable postgresql"
    ${VAULT_BIN} secrets enable postgresql

    echo 'vault write postgresql/config/connection connection_url="walid:jdk@localhost:5432/postgres?sslmode=disable"'
    ${VAULT_BIN} write database/config/postgres \
    plugin_name=postgresql-database-plugin \
    allowed_roles="my-role" \
    connection_url="postgresql://{{username}}:{{password}}@localhost:5432/postgres?sslmode=disable" \
    username="walid" \
    password="jdk"

    echo 'vault write postgresql/roles/readonly sql'
    ${VAULT_BIN} write database/roles/my-role \
    db_name=postgres \
    creation_statements="CREATE ROLE \"{{name}}\" WITH LOGIN PASSWORD '{{password}}' VALID UNTIL '{{expiration}}'; \
        GRANT SELECT ON ALL TABLES IN SCHEMA public TO \"{{name}}\";" \
    default_ttl="1h" \
    max_ttl="24h"
else
    echo "###########################################################################"
    echo "# PostgresSQL not running, skip PostgresSQL integration setup             #"
    echo "###########################################################################"
fi


