pipeline {
    agent any

    tools {
        maven 'MVN' // Must match the Maven name in Jenkins Global Tool Config
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

        stage('Publish Allure HTML Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'TestRestAssured/target/site/allure-maven-plugin',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report',
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ])
            }
        }
    }

    post {
        success {
            echo '✅ Build completed. Allure report published!'
        }
        failure {
            echo '❌ Build failed. Check console output or test results.'
        }
    }
}
