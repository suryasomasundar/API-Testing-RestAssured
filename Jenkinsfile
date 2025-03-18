pipeline {
    agent any

    tools {
        maven 'MVN' // This must match the Maven name in Global Tool Config
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
                    showFailedBuilds: true
                ])
            }
        }

        stage('Generate Allure Report') {
            steps {
                allure([
                    commandline: 'Allure', // 👈 Must match name in Global Tool Config
                    includeProperties: false,
                    results: [[path: 'TestRestAssured/target/allure-results']]
                ])
            }
        }
    }

    post {
        success {
            echo '✅ Build and tests passed. Allure report generated.'
        }
        failure {
            echo '❌ Build failed. Check logs, test results, or Allure configuration.'
        }
    }
}
