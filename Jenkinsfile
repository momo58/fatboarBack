def CONTAINER_NAME = "fatboar-back"
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

        stage('Build Fatboar-back & Unit tests') {
            steps {
                sh 'mvn clean verify -DskipITs=true -DargLine="-Dspring.profiles.active=test"'
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('Run SonarQube analysis'){
            steps {
                sh "mvn sonar:sonar \
                      -Dsonar.host.url=https://sonarqube.fatboar.tk \
                      -Dsonar.login=edfd9a0780ea6d7f9a83e3d1c177e5e44feb70ac"
            }
        }

        stage("Image Prune"){
            steps {
                imagePrune(CONTAINER_NAME)
            }
        }

        stage('Build Fatboar image') {
            steps {
                imageBuild(CONTAINER_NAME, CONTAINER_TAG)
            }
        }

        stage('Pushing image to nexus repo') {
            steps {
                pushImageToNexusRegistry(CONTAINER_NAME, CONTAINER_TAG)
            }
        }

        stage('Deploy Fatboar on development') {
            when {
               branch 'develop'
            }
            steps {
                 sh "docker-compose up -d"
                 echo "Application started"
            }
        }

        stage('Deploy Fatboar on Qualification') {
            when {
               branch 'release'
            }
            steps {
               pullImageFromNexus(CONTAINER_NAME, CONTAINER_TAG)
               sh "docker-compose -f docker-compose.yml -f docker-compose.qa.yml up -d"
            }
        }
    }
}

def imagePrune(containerName) {
    sh "docker image prune -f"
}

def imageBuild(containerName, tag) {
    sh "docker build -t $containerName:$tag -t $containerName --pull --no-cache ."
    echo "The image build ended successfully !"
}

def pushImageToNexusRegistry(containerName, tag) {
    sh "docker login nexus.fatboar.tk:8123 -u admin -p admin"
    sh "docker tag $containerName:$tag nexus.fatboar.tk:8123/$containerName:$tag"
    sh "docker push nexus.fatboar.tk:8123/$containerName:$tag"
    echo "Image push complete"
}

def pullImageFromNexus(containerName, tag) {
    sh "docker pull nexus.fatboar.tk:8123/$containerName:$tag"
}

