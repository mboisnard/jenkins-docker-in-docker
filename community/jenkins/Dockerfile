FROM jenkins:latest

ENV JENKINS_HOME /var/jenkins_home
ENV DOCKER_REMOTE_API_PORT 4243
ENV TERM linux

USER root

# Install Docker
RUN apt-get update && \
	apt-get install -y --no-install-recommends \
		apt-transport-https ca-certificates curl nano software-properties-common

RUN add-apt-repository "deb https://apt.dockerproject.org/repo/ debian-jessie main" && \
	curl -fsSL https://apt.dockerproject.org/gpg | apt-key add -

RUN apt-get update && \
	apt-get -y install docker-engine && \
	rm -rf /var/lib/apt/lists/*

RUN echo 'DOCKER_OPTS="-H tcp://0.0.0.0:$DOCKER_REMOTE_API_PORT -H unix:///var/run/docker.sock"' >> /etc/default/docker
RUN usermod -G docker jenkins

# Configure Proxy, Executors, JDK, ..
COPY config/scripts/*.groovy /usr/share/jenkins/ref/init.groovy.d/

# Disable the upgrade wizard
RUN echo -n 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state  && \
	echo -n 2.0 > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

# Install plugins from txt file
COPY config/plugins/plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh $(cat /usr/share/jenkins/plugins.txt | tr '\n' ' ')

# Copy custom plugins
# COPY config/plugins/*.hpi /usr/share/jenkins/ref/plugins/

# Copy default properties
COPY config/properties/ /usr/share/jenkins/ref/

RUN chown -R jenkins /usr/share/jenkins/ref

# Add docker-compose
RUN mv /usr/share/jenkins/ref/docker-compose /usr/local/bin/docker-compose

USER jenkins