docker-remove:
	docker rmi registry.gitlab.com/shurupov/homeowners/ui:1.0
	docker rmi registry.gitlab.com/shurupov/homeowners/ui

docker-build:
	docker build -t registry.gitlab.com/shurupov/homeowners/ui:1.0 -t registry.gitlab.com/shurupov/homeowners/ui:latest .

docker-login:
	cat dockerpassword | docker login --username shurupov --password-stdin registry.gitlab.com/shurupov/homeowners

docker-push:
	docker push --all-tags registry.gitlab.com/shurupov/homeowners/ui

docker-login-build-push: docker-login docker-build docker-push

