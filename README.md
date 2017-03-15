# Jenkins Docker In Docker

A ready for use Jenkins Docker image that allows you to build Docker images inside the container.
The image installs a plugins list, prepares the use of the Maven, Sonar, NodeJS tools and executes the defined groovy scripts (JDK, runners, ...).

##### 2 versions

* Community : Jenkins dind for personnal use
* Enterprise : Jenkins dind behind an Enterprise Proxy, Private Maven Repository, Tools Proxy (Jenkins, Maven, Gradle, NPM)

## Instructions

Make sure you have configured the `.env` file.

```sh
$ cd jenkins-docker-in-docker/community
$ docker-compose up -d --build
```
