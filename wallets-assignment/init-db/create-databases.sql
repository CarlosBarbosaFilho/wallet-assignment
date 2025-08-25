-- Remove os bancos se existirem e cria novamente
DROP DATABASE IF EXISTS "ms-wallets";
DROP DATABASE IF EXISTS "ms-transactions";

CREATE DATABASE "ms-wallets";
CREATE DATABASE "ms-transactions";
