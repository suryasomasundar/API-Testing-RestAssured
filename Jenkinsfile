pipeline {
    agent any

    tools {
        maven 'MVN'
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
                    commandline: 'Allure',
                    includeProperties: false,
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
