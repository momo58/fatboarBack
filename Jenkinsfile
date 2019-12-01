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
             when {
                branch 'develop'
             }
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

        stage('Build Fatboar image') {
            when {
                branch 'develop'
            }
            steps {
                imageBuild(CONTAINER_NAME, CONTAINER_TAG)
            }
        }

        stage('Pushing image to nexus repo') {
            when {
                branch 'develop'
            }
            steps {
                pushImageToNexusRegistry(CONTAINER_NAME, CONTAINER_TAG)
            }
        }

        stage('Run Fatboar == Deploy on development'){
            steps {
                runApp(CONTAINER_NAME, CONTAINER_TAG)
            }
        }
    }
}

def imageBuild(containerName, tag) {
    sh "docker build -t $containerName:$tag -t $containerName --pull --no-cache ."
    echo "The image build ended successfully !"
}

def pushImageToNexusRegistry(containerName, tag) {
    sh "docker login Nexus:8123 -u admin -p admin"
    sh "docker tag $containerName:$tag Nexus:8123/$containerName:$tag"
    sh "docker push Nexus:8123/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag){
    sh "./run.sh"
    echo "Application started"
}

