def CONTAINER_NAME = "jenkins-pipeline"
def CONTAINER_TAG = "latest"

pipeline {
    agent any
    stages {
        stage('Initialize') {
            steps {
               script {
                     def dockerHome = tool 'myDocker'
                     def mavenHome  = tool 'myMaven'
                     env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
               }
            }
        }

        stage('Build') {
             when {
                branch 'develop'
             }
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Image Build') {
            when {
                branch 'develop'
            }
            steps {
                imageBuild(CONTAINER_NAME, CONTAINER_TAG)
            }
        }

        stage('Run App'){
            runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT)
        }
    }
}

def imageBuild(containerName, tag) {
    sh "docker build -t $containerName:$tag -t $containerName --pull --no-cache ."
    echo "build ended successfully !"
}

def runApp(containerName, tag, dockerHubUser, httpPort){
    sh "docker-compose up"
    echo "Application started on port: ${httpPort} (http)"
}

