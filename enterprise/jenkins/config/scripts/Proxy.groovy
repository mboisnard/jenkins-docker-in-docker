import jenkins.model.*

def proxyUrl 	= "{HTTP_PROXY_HOST}"
def proxyPort 	= {HTTP_PROXY_PORT}

def instance = Jenkins.getInstance()

instance.proxy = new hudson.ProxyConfiguration(proxyUrl, proxyPort)
instance.save()