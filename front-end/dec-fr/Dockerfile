FROM node:12-alpine

RUN mkdir -p /app
WORKDIR /app

RUN \
  apk add --no-cache python make curl g++ && \
  apk add vips-dev fftw-dev --no-cache --repository http://dl-3.alpinelinux.org/alpine/edge/testing --repository http://dl-3.alpinelinux.org/alpine/edge/main && \
  rm -fR /var/cache/apk/*

COPY package* ./
RUN npm install
COPY public ./public
COPY jsconfig.json ./
COPY scripts ./scripts
COPY src ./src

RUN chmod +x ./scripts/*.sh
CMD sh ./scripts/startup.sh