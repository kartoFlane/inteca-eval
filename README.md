## Compiling & building


Build process for this project is automated by Maven (version 3.5.4). To build and compile this project, make sure that Docker is running, and then run the following command in the project's root directory:
```shell
mvnw install dockerfile:build
```

If, like me, you're still using Windows 7 and forced to use Docker Toolbox, then you'll also need to set up environment variables to connect to Docker VM. Otherwise, the build process fails. [A script which sets these variables](#set_env.cmd) is provided for convenience (although you'll need to change `DOCKER_CERT_PATH` in the script to point to your user directory).
```shell
set_env && mvnw install dockerfile:build
```

After the Maven script is finished, two Docker images will have been created: `kartoflane/inteca-eval-spring` and `kartoflane/inteca-eval-angular`. For the database, the stock `mysql` image maintained by Docker is used, downloaded during deployment.


## Running...


### ...via `compose` / `stack`

To simplify deployment, [`a Compose file`](#stack.yml) is provided, which starts all three containers. To run it, change the Docker terminal's working directory to the project's root directory, and executing the following command:
```shell
docker-compose ....
```
-- TODO

...or when running Docker Toolbox, which doesn't have `docker compose`, instead use:
```shell
docker stack deploy -c stack.yml tbachminski-stack
```

If the Docker node isn't part of a swarm yet, you might need to initialize one first:
```shell
docker swarm init
```

...when running Docker Toolbox, you'll also need to specify `--advertise-addr`, set to the address of `DOCKER_HOST`:
```shell
docker swarm init --advertise-addr:192.168.99.100:2376
```

To stop the entire stack, run:
```shell
docker stack rm tbachminski-stack
```

After the stack has been deployed, the service will be available at `localhost:8080`, or (in case of Docker Toolbox) at `DOCKER_HOST`: `192.168.99.100:8080`.

#### Note:

The DB container takes some to initialize, during which the SpringBoot app container will try to connect to the database, fail, and thus crash. It will keep restarting and retrying until it succeeds, which on my PC usually takes about 5 minutes from the moment the DB container has been created.


### ...each image separately

For future reference, here are commands that can be used to run each image separately if need be:


#### Database image:

```shell
docker run -d \
	--name kartoflane-db \
	-e MYSQL_ROOT_PASSWORD=rootpassword \
	-e MYSQL_DATABASE=sqldb \
	-e MYSQL_USER=dbuser \
	-e MYSQL_PASSWORD=dbpass \
	mysql:latest
```


#### SpringBoot application image:

```shell
docker run -t \
	--name kartoflane-spring \
	--link kartoflane-db:mysql \
	-p 8080:8080 \
	-e DATABASE_HOST=kartoflane-db \
	-e DATABASE_PORT=3306 \
	-e DATABASE_NAME=sqldb \
	-e DATABASE_USER=dbuser \
	-e DATABASE_PASSWORD=dbpass \
	kartoflane/inteca-eval-spring
```


#### Angular application image:

```shell
TODO
```