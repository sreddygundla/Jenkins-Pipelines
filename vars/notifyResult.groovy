#!groovy

def call(String buildStatus = 'STARTED', String jobName, String buildNumber, String blueOceanUrl) {
    // build status of null means successful
    buildStatus =  buildStatus ?: 'SUCCESSFUL'

    def subject = "${jobName} build ${buildNumber} has ${buildStatus}"
    def details = "${jobName} build ${buildNumber} has ${buildStatus} (<a href='\${blueOceanUrl}'>Open</a>)"
    def recipients = 'sampathreddygundla23@gmail.com'

    emailext (
        subject: subject,
        body: details,
        to: recipients
    )
}

