FROM jenkins:latest

ARG proxyHost
ARG proxyPort
ARG nexusLogin
ARG nexusPass

#ENV http_proxy "http://$proxyHost:$proxyPort"
#ENV https_proxy "http://$proxyHost:$proxyPort"

ENV JENKINS_HOME /var/jenkins_home
ENV DOCKER_INSTALL_VERSION 1.12.6-0~debian-jessie
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
	apt-get -y install docker-engine=$DOCKER_INSTALL_VERSION && \
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
RUN find /usr/share/jenkins/ref/ -type f -print0 | xargs -0 sed -i 's/{HTTP_PROXY_HOST}/$proxyHost/g; s/{HTTP_PROXY_PORT}/$proxyPort/g; s/{NEXUS_LOGIN}/$nexusLogin/g; s/{NEXUS_PASSWD}/$nexusPass/g'

# Update Jenkins Launcher for Java Proxy Option
RUN mv /usr/share/jenkins/ref/jenkins.sh /usr/local/bin/jenkins.sh

RUN chown jenkins /usr/local/bin/jenkins.sh
RUN chown -R jenkins /usr/share/jenkins/ref

USER jenkins