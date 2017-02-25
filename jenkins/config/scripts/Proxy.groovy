import jenkins.model.*

def proxyUrl 	= "proxy.company.com"
def proxyPort 	= 8080

def instance = Jenkins.getInstance()

instance.proxy = new hudson.ProxyConfiguration(proxyUrl, proxyPort)
instance.save()