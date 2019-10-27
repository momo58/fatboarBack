def CONTAINER_NAME = "jenkins-pipeline"
def CONTAINER_TAG = "latest"

pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Image Build') {
            imageBuild(CONTAINER_NAME, CONTAINER_TAG)
        }
    }
}

def imageBuild(containerName, tag) {
    sh "docker build -t $containerName:$tag -t $containerName --pull --no-cache ."
    echo "build ended successfully !"
}
