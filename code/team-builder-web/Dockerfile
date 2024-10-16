# Stage 1: Build the Angular app and run tests
FROM node:18 AS build
WORKDIR /app

# Install app dependencies
COPY package*.json ./
RUN npm install

# Install Chrome for running tests in headless mode
RUN apt-get update && apt-get install -y wget gnupg2 \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*

# Set environment variable for Karma to find Chrome
ENV CHROME_BIN=/usr/bin/google-chrome
ENV NODE_OPTIONS=--openssl-legacy-provider

# copy app source code
COPY . .

# Run tests and generate coverage report using Chrome Headless
# RUN npm run test -- --watch=false --code-coverage --browsers=ChromeHeadlessNoSandbox

# Build the app for production
RUN npm run build --prod

# Stage 2: Serve the Angular app using Nginx
FROM nginx:alpine
COPY --from=build /app/dist/team-builder-web/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
