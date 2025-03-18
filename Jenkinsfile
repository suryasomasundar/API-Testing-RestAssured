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

        stage('Run API Tests via TestNG Suite') {
            steps {
                dir('TestRestAssured') {
                    sh 'mvn clean test -DsuiteXmlFile=testng.xml'
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

        stage('Archive Allure Report') {
            steps {
                archiveArtifacts artifacts: 'TestRestAssured/target/site/allure-maven-plugin/**', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Build completed. Allure report archived!'
        }
        failure {
            echo '❌ Build failed. Check logs and archived files.'
        }
    }
}
