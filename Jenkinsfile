pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Checkout Source Code') {
            when {
                branch 'main'
            }
            steps {
                echo "GitHub repository çekiliyor..."
                checkout([$class: 'GitSCM', branches: [[name: 'refs/heads/main']], userRemoteConfigs: [[url: 'https://github.com/er3nnz/dersProject']]])
            }
        }

        stage('Run Unit Tests') {
            when {
                branch 'main'
            }
            steps {
                echo "Java unit testleri çalıştırılıyor..."
                sh 'mvn -v'
                sh 'mvn clean test'
            }
        }

        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo "🚀 Deploy aşaması"
                echo "✅ Uygulama deploy edildi (simülasyon)"
            }
        }
    }

    post {
        failure {
            echo "❌ Pipeline başarısız, deploy yapılmadı"
        }
        success {
            echo "✅ Pipeline başarılı (master)"
        }
    }
}

