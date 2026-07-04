def call(Map config = [:]){

    pipeline {
        agent any
        
        options{
            buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '5'))
        }
        
        parameters{
            gitParameter branchFilter: 'origin.*/(.*)', defaultValue: 'develop', name: 'BRANCH', type: 'PT_BRANCH'
            choice(name: 'FLAVOUR', choices: ['Actual', 'Mock'])
        }
        
        stages {
            
            stage('Git Checkout') {
                steps {
                    checkoutProject(project: config.project, branch: "${params.BRANCH}", credentials: "${config.credentials}");
                }
            }
            
            stage('Build'){
                steps {
                    buildAndroid(project: config.project, flavour: params.FLAVOUR, branch: params.BRANCH);
                }
            }

            stage('Deploy App Distribution'){
                steps{
                    withCredentials([file(credentialsId: config.firebaseCredentials,variable: 'GOOGLE_APPLICATION_CREDENTIALS')]){
                        deployForAppDistribution();
                    }
                }
            }
        }
        /*post { 
            always { 
                deleteDir();
            }
        }*/
    }
}