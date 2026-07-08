def call(Map config = [:]){

    pipeline {
        agent any
        
        options{
            buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '5'))
        }
        
        parameters{
            gitParameter branchFilter: 'origin.*/(.*)', defaultValue: 'develop', name: 'BRANCH', type: 'PT_BRANCH'
            choice(name: 'FLAVOUR', choices: ['Actual', 'Mock'])
            choice(name: 'FORMAT', choices: ['apk', 'aab'])
        }
        
        stages {
            
            stage('Git Checkout') {
                steps {
                    checkoutProject(project: config.project, branch: "${params.BRANCH}", credentials: "${config.credentials}");
                }
            }
            
            stage('Build'){
                steps {
                    withEnv(["JENKINS_BUILD_NUMBER=${env.BUILD_NUMBER}"]){
                         buildAndroid(project: config.project, flavour: params.FLAVOUR, branch: params.BRANCH, format: params.FORMAT);
                    }
                }
            }

            stage('Deploy App Distribution'){
                steps{
                    withCredentials([file(credentialsId: config.firebaseCredentials,variable: 'GOOGLE_APPLICATION_CREDENTIALS')]){
                        deployForAppDistribution(project: config.project, flavour: params.FLAVOUR, branch: params.BRANCH, format: params.FORMAT);
                    }
                }
            }
        }
        post { 
            always { 
                deleteDir();
            }
        }
    }
}