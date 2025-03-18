pipeline {
    agent any

    tools {
        maven 'MVN' // Matches Maven in Global Tool Config
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

        stage('Generate Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '', // Use default JDK
                    results: [[path: 'TestRestAssured/target/allure-results']]
                ])
            }
        }
    }

    post {
        success {
            echo '✅ Build & Tests passed. Allure report published.'
        }
        failure {
            echo '❌ Build failed. Check logs and test reports.'
        }
    }
}
