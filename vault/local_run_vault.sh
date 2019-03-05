#!/bin/bash

echo "###########################################################################"
echo "# Start Vault on https://localhost:8200 and http://localhost:8201         #"
echo "###########################################################################"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
VAULT_BIN="/usr/local/bin/vault"

${VAULT_BIN} server \
            -dev
            -config=vault.conf \
            -dev-root-token-id="00000000-0000-0000-0000-000000000000" \
            -dev-listen-address="127.0.0.1:8201"

exit $?
