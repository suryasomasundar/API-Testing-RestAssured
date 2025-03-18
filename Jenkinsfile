pipeline {
    agent any

    tools {
        maven 'MVN' // This must match the name in Jenkins Global Tool Configuration
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'git@github.com:suryasomasundar/API-Testing-RestAssured.git'
            }
        }

        stage('Run API Tests') {
            steps {
                dir('TestRestAssured') {
                    sh 'mvn clean test'
                }
            }
        }

        stage('Publish TestNG Results') {
            steps {
                step([
                    $class: 'Publisher',
                    reportFilenamePattern: 'TestRestAssured/test-output/testng-results.xml',
                    escapeTestDescription: false,
                    escapeExceptionMessages: false,
                    showFailedBuilds: true
                ])
            }
        }
    }

    post {
        success {
            echo '✅ Build & API Tests completed successfully.'
        }
        failure {
            echo '❌ Build failed. Check the logs and test results.'
        }
    }
}
