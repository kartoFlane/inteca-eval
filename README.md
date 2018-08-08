## Compiling & building


Build process for this project is automated by Maven (version 3.5.4). To build and compile this project, make sure that Docker is running, and then run the following command in the project's root directory:
```shell
mvnw package
```

If, like me, you're still using Windows 7 and forced to use Docker Toolbox, then you'll also need to set up environment variables to connect to Docker VM. Otherwise, the build process fails. [A script which sets these variables](#set_env.cmd) is provided for convenience (although you'll need to change `DOCKER_CERT_PATH` in the script to point to your user directory).
```shell
set_env && mvnw package
```

After the Maven script is finished, two Docker images will have been created: `tbachminski/inteca-eval-spring` and `tbachminski/inteca-eval-angular`. For the database, the stock `mysql` image maintained by Docker is used, downloaded during deployment.


## Running...


### ...via `compose` / `stack`

To simplify deployment, [`a Compose file`](#compose.yml) is provided, which starts all three containers. To run it, change the Docker terminal's working directory to the project's root directory, and executing the following command:
```shell
docker-compose up -p tb
```

When running Docker Toolbox, the above command will cause the containers to attempt to connect to incorrect addresses.
```shell
docker stack deploy -c docker-compose.yml tb
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
docker stack rm tb
```

After the stack has been deployed, the service will be available at `localhost:8080`, or (in case of Docker Toolbox) at `DOCKER_HOST`: `192.168.99.100:8080`.


### ...each image separately

For future reference, here are commands that can be used to run each image separately if need be:


#### Database image:

```shell
docker run -d \
	--name tbachminski-db \
	-e MYSQL_ROOT_PASSWORD=rootpassword \
	-e MYSQL_DATABASE=sqldb \
	-e MYSQL_USER=dbuser \
	-e MYSQL_PASSWORD=dbpass \
	mysql:latest
```


#### SpringBoot application image:

```shell
docker run -t \
	--name tbachminski-spring \
	--link tbachminski-db:mysql \
	-p 8080:8080 \
	-e DATABASE_HOST=tbachminski-db \
	-e DATABASE_PORT=3306 \
	-e DATABASE_NAME=sqldb \
	-e DATABASE_USER=dbuser \
	-e DATABASE_PASSWORD=dbpass \
	tbachminski/inteca-eval-spring
```


#### Angular application image:

```shell
TODO
```


## REST endpoint documentation


### `/family`

* `GET` - returns a list of all families.
* `POST` - creates a new empty family entity, and returns a response with its location.

### `/family/$id`

* `GET` - returns a view of family with id `$id`.
* `PUT` - modifies the family with id `$id` either by adding a father, or a child. The specific action taken depends on the content of the request body:
	* `{ "fatherId": # }` - adds a father
	* `{ "childId": # }` - adds a child

### `/family/search`

* `GET` - returns a list of all families which contain at least one child which matches any of the specified by arguments:
	* `firstName` - string
	* `secondName` - string
	* `pesel` - string
	* `birthDate` - string, with date format: `yyyy-MM-dd`
	* `sex` - string, values: `M` or `F`


### `/father`

* `GET` - returns a list of all fathers.
* `POST` - creates a new father entity from arguments specified in the request body, and returns a response with its location:
	* `firstName` - string
	* `secondName` - string
	* `pesel` - string
	* `birthDate` - string, with date format: `yyyy-MM-dd`

### `/father/$id`

* `GET` - returns a view of father with id `$id`.

### `/father/search`

* `GET` - returns a list of all fathers which match any of the specified by arguments:
	* `firstName` - string
	* `secondName` - string
	* `pesel` - string
	* `birthDate` - string, with date format: `yyyy-MM-dd`


### `/child`

* `GET` - returns a list of all children.
* `POST` - creates a new child entity from arguments specified in the request body, and returns a response with its location:
	* `firstName` - string
	* `secondName` - string
	* `pesel` - string
	* `birthDate` - string, with date format: `yyyy-MM-dd`
	* `sex` - string, values: `M` or `F`

### `/child/$id`

* `GET` - returns a view of child with id `$id`.

### `/father/search`

* `GET` - returns a list of all children which match any of the specified by arguments:
	* `firstName` - string
	* `secondName` - string
	* `pesel` - string
	* `birthDate` - string, with date format: `yyyy-MM-dd`
	* `sex` - string, values: `M` or `F`
