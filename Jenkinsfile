pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build & Unit tests') { 
            steps {
                sh 'mvn clean verify -DskipITs=true' 
            }
        }
    }
}