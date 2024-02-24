def call(Map config = [:]){

    pipeline {
        agent any
        
        options{
            buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '5'))
        }
        environment{
            PATH = "/usr/share/maven:$PATH"
        }
        
        parameters{
            gitParameter branchFilter: 'origin.*/(.*)', defaultValue: 'develop', name: 'BRANCH', type: 'PT_BRANCH'
        }
        
        stages {
            
            stage('Git Checkout') {
                steps {
                    checkoutProject(project: "${config.project}", branch: "${params.BRANCH}", credentials: "${config.credentials}");
                }
            }
            
            stage('Build'){
                steps {
                    buildMaven(install: false, skipTest: false);
                }
            }

            stage('Deploy for Batch'){
                steps{
                    deployForBatch(basePath: "/opt/kyc/apps",project: "${config.project}");
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