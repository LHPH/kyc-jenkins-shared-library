def call(Map config = [:]){

    pipeline {
        agent any
        
        options{
            buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '5'))
        }
        
        parameters{
            gitParameter branchFilter: 'origin.*/(.*)', defaultValue: 'develop', name: 'BRANCH', type: 'PT_BRANCH'
            booleanParam(name: "CACHE",
                description: "Use cache? (for node_modules)",
                defaultValue: !'master'.equals(params.BRANCH))
        }
        
        stages {
            
            stage('Git Checkout') {
                steps {
                    checkoutProject(project: "${config.project}", branch: "${params.BRANCH}", credentials: "${config.credentials}");
                }
            }
            
            stage('Build'){
                steps {
                    buildNode(cache: params.CACHE, service: true, skipTest: true);
                }
            }

            stage('Deploy for Supervisor'){
                steps{
                    deployForSupervisor(basePath: "/opt/kyc/apps",project: "${config.project}");
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