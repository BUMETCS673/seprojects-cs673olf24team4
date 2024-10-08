# TeamBuilderWeb

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 18.2.4.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build and run using Docker

### Build the Docker Image

Run the following command in the root directory of project `code/team-builder-web`:

```
docker build -t team-builder-app .
```
- `-t team-builder-app`: This tags the Docker image with the name team-builder-app

### Run the Docker Container

Run the following command to start a container from the team-builder-app image:

```
docker run -d -p 8080:80 --name team-builder-container team-builder-app
```
- `-d`: Runs the container in detached mode (in the background)
- `-p 8080:80`: Maps port 8080 on your host machine to port 80 in the container (where Nginx is serving the app)
- `--name team-builder-container`: Assigns a name to the running container for easier management

### Access the application

Once the container is running, you can access the TeamBuilder app by opening your web browser and navigating to:

```
http://localhost:8080
```

### Customizing the port

If you want to run the application on a different port (e.g., port 4000), you can modify the port mapping:

```
docker run -d -p 4000:80 --name team-builder-container team-builder-app
```
Now you can access the application at:
```
http://localhost:4000
```

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
