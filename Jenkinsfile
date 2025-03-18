pipeline {
    agent any

    tools {
        maven 'MVN' // Only valid tool here
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

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'TestRestAssured/target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo '📊 Allure report should now be available in the Jenkins build.'
        }
    }
}
