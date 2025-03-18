pipeline {
    agent any

    tools {
        maven 'MVN' // Must match your configured Maven tool name
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

        stage('Upload Allure Report to S3') {
            steps {
                sh '''
                    echo "🔼 Uploading Allure report to S3..."

                    # Sync versioned report to S3
                    aws s3 sync TestRestAssured/target/site/allure-maven-plugin \
                        s3://allure-report-restassured/$BUILD_NUMBER/ --region us-west-1 --delete

                    # Also update the 'latest' report
                    aws s3 sync TestRestAssured/target/site/allure-maven-plugin \
                        s3://allure-report-restassured/latest/ --region us-west-1 --delete
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build succeeded. Allure report is ready!'
            echo "🌐 Current Build Report: http://allure-report-restassured.s3-website-us-west-1.amazonaws.com/$BUILD_NUMBER/index.html"
            echo "🌐 Latest Report: http://allure-report-restassured.s3-website-us-west-1.amazonaws.com/latest/index.html"
        }
        failure {
            echo '❌ Build or upload failed. Please check logs.'
        }
    }
}
