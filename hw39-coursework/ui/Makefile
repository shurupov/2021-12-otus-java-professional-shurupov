docker-build:
	docker build -t registry.gitlab.com/shurupov/homeowners/ui:1.0 .

docker-login:
	docker login -u $GITLAB_USERNAME -p $GITLAB_PASSWORD registry.gitlab.com/shurupov/homeowners

docker-push:
	docker push registry.gitlab.com/shurupov/homeowners/ui:1.0

