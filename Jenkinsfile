#!/usr/bin/env groovy

pipeline {
  agent { label 'executor-v2' }

  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '30'))
  }

  triggers {
    cron(getDailyCronString())
  }

  stages {
    stage('Fetch tags') {
      steps {
        withCredentials(
          [usernameColonPassword(credentialsId: 'conjur-jenkins-api', variable: 'GITCREDS')]
        ) {
          sh '''
            git fetch --tags `git remote get-url origin | sed -e "s|https://|https://$GITCREDS@|"`
          '''
        }
      }
    }
    stage('Create and archive the Maven package') {
      steps {
        sh './build.sh'
      }
    }
    stage('Run tests and archive test results') {
      steps {
        lock("api-java-${env.NODE_NAME}") {
          sh './test.sh'
        }

        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Publish the Maven package') {
      steps {
        sh './publish.sh'
      }
    }
  }

  post {
    always {
      cleanupAndNotify(currentBuild.currentResult)
    }
  }
}
