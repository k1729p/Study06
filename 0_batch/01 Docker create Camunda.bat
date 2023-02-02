@echo off
::set COMPOSE_FILE=docker-config\docker-compose.yaml
set COMPOSE_FILE=docker-config\docker-compose-core.yaml
cd ..
docker compose --file %COMPOSE_FILE% up --detach
pause