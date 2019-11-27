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
                sh 'mvn clean verify -DskipITs=true'
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

         stage('Run SonarQube analysis'){
            steps {
                sh "mvn sonar:sonar -Dsonar.host.url=https://sonarqube.fatboar.tk -Dsonar.login=6c64fc4c493b12f3c9ee9fb3667068495dc6e360"
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

def pushImageToPrivateRegistry(containerName, tag) {
    sh "docker login registry.fatboar.tk -u admin -p admin"
    sh "docker tag $containerName:$tag admin/$containerName:$tag"
    sh "docker push admin/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag){
    sh "./run.sh"
    echo "Application started"
}

