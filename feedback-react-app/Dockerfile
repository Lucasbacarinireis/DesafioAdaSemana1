FROM node:18 as build

WORKDIR /app

COPY package.json yarn.lock ./

ENV NODE_OPTIONS="--openssl-legacy-provider"

RUN yarn install

COPY . .

RUN yarn build

FROM nginx:alpine

COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
