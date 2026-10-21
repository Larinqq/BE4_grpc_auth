PROJECT_NAME = be4
COMPOSE = sudo docker compose

up:
	$(COMPOSE) -p $(PROJECT_NAME) up
up-build:
	$(COMPOSE) -p $(PROJECT_NAME) up --build
down:
	$(COMPOSE) -p $(PROJECT_NAME) down
down-clean:
	$(COMPOSE) -p $(PROJECT_NAME) down -v
status:
	$(COMPOSE) -p $(PROJECT_NAME) ps
logs-%:
	$(COMPOSE) -p $(PROJECT_NAME) logs -f --tail=50 $*
restart-%:
	$(COMPOSE) -p $(PROJECT_NAME) restart $*
	$(COMPOSE) -p $(PROJECT_NAME) logs -f --tail=50 $*
rebuild-%:
	$(COMPOSE) -p $(PROJECT_NAME) up --build $*
	$(COMPOSE) -p $(PROJECT_NAME) logs -f --tail=50 $*

.PHONY: up up-build down down-clean status logs-% restart-% rebuild-%
