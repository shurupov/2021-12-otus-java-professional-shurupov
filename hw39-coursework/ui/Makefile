docker-build:
	docker build -t registry.gitlab.com/shurupov/homeowners/ui:1.1 -t registry.gitlab.com/shurupov/homeowners/ui:latest .

docker-login:
	cat dockerpassword | docker login --username shurupov --password-stdin registry.gitlab.com/shurupov/homeowners
docker-push:
	docker push registry.gitlab.com/shurupov/homeowners/ui:1.1

