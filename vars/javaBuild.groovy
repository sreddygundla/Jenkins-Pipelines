#!groovy
final sampathlib = library('sampathlib@master')

def call(Map pipelineParams) {
    pipeline {
        agent none
        options {
            checkoutToSubdirectory('source')
        }
        tools {
            maven 'Maven-3.3.9'
        }
        stages {
            stage ('Build') {
                agent {
                  label 'build'
                }
                steps {
                    dir ('source') {
                        sh 'mvn -Dmaven.test.failure.ignore=true clean install'
                    }
                }
                post {
                    success {
                        publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'source/target/site/jacoco-both', reportFiles: 'index.html', reportName: 'JaCoCo Report', reportTitles: ''])
                        junit allowEmptyResults: true, keepLongStdio: true, testResults: 'source/target/surefire-reports/*.xml'
                    }
		    always {
                        notifyResult(currentBuild.result, "${env.JOB_NAME}", "${env.BUILD_NUMBER}", "${env.BLUE_OCEAN_URL}")
                    }
                }
            }
        }
    }
}

return this

