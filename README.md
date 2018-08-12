## Requirements

- JDK 8 or higher
- Docker (built on Windows 7 with Docker Toolbox, but *should* work with regular Docker too)
- Node.js
- Maven


## Compiling & building


Build process for this project is automated by Maven. To build and compile this project, make sure that Docker is running, and then run the following command in the project's root directory:
```shell
mvnw package
```

`mvnw` is a Maven wrapper batch script, which allows running the build script without having Maven installed. If you have Maven installed on your machine, you can substitute `mvnw` with the regular `mvn`.


If, like me, you're still using Windows 7 and forced to use Docker Toolbox, then you'll also need to set up environment variables to connect to Docker VM. Otherwise, the build process fails. [A script which sets these variables](#set_env.cmd) is provided for convenience (although you'll need to change `DOCKER_CERT_PATH` in the script to point to your user directory).
```shell
set_env && mvnw package
```

Once the Maven script is finished, two Docker images will have been created: `tbachminski/inteca-eval-spring` and `tbachminski/inteca-eval-angular`. For the database, the stock `mysql` image maintained by Docker is used, downloaded during deployment if it's not available locally.


## Running


To simplify deployment, [`a Compose file`](#docker-compose.yml) is provided, which starts all three containers. To run it, change the Docker terminal's working directory to the project's root directory, and execute the following command:
```shell
docker-compose up
```

When deploying the containers for the first time, the SpringBoot app container will fail and restart a few times while database is being initialized, until it finally succeeds. This process can take up to 2 minutes. My attempts to make the app gracefully wait for the database to become available have been unsuccessful thus far.

Once all containers have finished starting up, the Angular app will be available at `localhost:80`. SpringBoot app serving the REST API will be available at `localhost:8080`. In case of Docker Toolbox, replace `localhost` with `DOCKER_HOST` (`192.168.99.100`).

To gracefully shut down the containers, run:
```shell
docker-compose down
```

When running `docker-compose` in interactive mode, trying to break out via Ctrl+C will cause the containers to be stopped, *but not removed*. To clean them up, run `docker-compose down`.
