FROM node:16.15.1-alpine3.15
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./ $PROJECT_DIR/
RUN npm install
RUN NODE_ENV=production npm run build

FROM nginx:1.17.1-alpine
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD default.conf /etc/nginx/conf.d/
COPY --from=0 $PROJECT_DIR/build/* /usr/share/nginx/html/
EXPOSE 80

# https://www.carlrippon.com/creating-react-and-typescript-apps-with-webpack/