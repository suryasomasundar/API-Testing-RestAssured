pipeline {
    agent any

    tools {
        maven 'MVN'
    }

    environment {
        PATH = "/usr/local/bin:/opt/homebrew/bin:/usr/bin:/bin:$PATH"
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

        stage('Install AWS CLI if Missing') {
            steps {
                sh '''
                    if ! command -v aws &> /dev/null; then
                        echo "🔧 AWS CLI not found. Installing..."
                        curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"
                        sudo installer -pkg AWSCLIV2.pkg -target /
                        echo "✅ AWS CLI installed."
                    else
                        echo "✅ AWS CLI already installed: $(aws --version)"
                    fi
                '''
            }
        }

        stage('Upload Allure Report to S3') {
            steps {
                sh '''
                    echo "🔼 Uploading Allure report to S3..."

                    aws s3 sync TestRestAssured/target/site/allure-maven-plugin \
                        s3://allure-report-restassured/$BUILD_NUMBER/ --region us-east-1 --delete

                    aws s3 sync TestRestAssured/target/site/allure-maven-plugin \
                        s3://allure-report-restassured/latest/ --region us-east-1 --delete
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build succeeded. Allure report uploaded!'
            echo "🌐 Report (Build): http://allure-report-restassured.s3-website-us-east-1.amazonaws.com/$BUILD_NUMBER/index.html"
            echo "🌐 Report (Latest): http://allure-report-restassured.s3-website-us-east-1.amazonaws.com/latest/index.html"

            post {
    success {
        echo '✅ Build succeeded. Allure report uploaded!'
        echo "🌐 Report (Build): http://allure-report-restassured.s3-website-us-east-1.amazonaws.com/$BUILD_NUMBER/index.html"
        echo "🌐 Report (Latest): http://allure-report-restassured.s3-website-us-east-1.amazonaws.com/latest/index.html"

        // 📧 OPTIONAL: Send email notification with report link
        // mail to: 'team@example.com',
        //      subject: "✅ API Tests Passed: Build #$BUILD_NUMBER",
        //      body: """
        //          Hello Team,
        //
        //          The API automation tests for build #$BUILD_NUMBER completed successfully.
        //          You can view the full Allure report here:
        //
        //          http://allure-report-restassured.s3-website-us-east-1.amazonaws.com/$BUILD_NUMBER/index.html
        //
        //          - Jenkins
        //      """

        // 🧹 OPTIONAL: Auto-delete S3 folders older than 60 days
        // echo "🧹 Checking for S3 report folders older than 60 days..."
        // You can use `aws s3 ls` and `aws s3 rm` combined with `date` in a shell script
        // or use a Lambda lifecycle policy if fully automated is preferred
    }

    failure {
        echo '❌ Build or upload failed. Please check logs.'
    }
}

        }
        failure {
            echo '❌ Build or upload failed. Please check logs.'
        }
    }
}
