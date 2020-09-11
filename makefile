psql:
	docker exec -ti -u postgres codechallenge_postgres_1 psql -U codechallengeapp codechallenge

updb:
	docker-compose -f docker-compose-db-only.yml up -d postgres

run:
	scripts/run.sh

init:
	scripts/init.sh

build:
	docker build -t codechallenge .

runall:
	docker-compose up
