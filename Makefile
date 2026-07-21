.PHONY: help up down clean reset logs ps

.DEFAULT_GOAL := help

help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-12s\033[0m %s\n", $$1, $$2}'

up: ## Start all services (docker compose up -d)
	docker compose up -d
	@echo "All services started."

down: ## Stop all services
	docker compose down
	@echo "All services stopped."

clean: down ## Stop services and delete all volumes
	docker volume rm rakawikchat_postgres_data rakawikchat_mongo_data rakawikchat_redis_data rakawikchat_minio_data 2>/dev/null || true
	@echo "Volumes deleted."

reset: clean up ## Full reset: clean volumes then restart all services

logs: ## Tail logs of all services
	docker compose logs -f

ps: ## Show running services
	docker compose ps

boot: ## Start Spring Boot backend
	./gradlew bootRun

build: ## Compile backend
	./gradlew compileJava
