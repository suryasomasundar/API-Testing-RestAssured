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

        stage('Generate Allure HTML Report') {
            steps {
                dir('TestRestAssured') {
                    sh 'mvn allure:report'
                }
            }
        }

        stage('Publish HTML Report') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'TestRestAssured/target/site/allure-maven-plugin',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report'
                ])
            }
        }
    }

    post {
        success {
            echo '✅ Allure HTML report generated successfully.'
        }
        failure {
            echo '❌ Something went wrong. Check Maven or paths.'
        }
    }
}
