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

            stage('Sign'){
                steps {
                    withCredentials([
                        string(credentialsId: '440307a6-0399-45e3-9782-928731eb8932', variable: 'KEYSTORE_BASE64'),
                        string(credentialsId: 'f24f173a-1c11-438a-8929-1f2611bf0875', variable: 'KEYSTORE_PASSWORD'),                            
                        string(credentialsId: '9b5fc393-b883-4aad-ba05-ff255a81fc26', variable: 'KEY_ALIAS'),
                        string(credentialsId: '157247d7-583b-453b-a6d3-b1f0c48274c2', variable: 'KEY_PASSWORD')
                    ]){
                        signApp(project: config.project, flavour: params.FLAVOUR, branch: params.BRANCH, format: params.FORMAT);
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