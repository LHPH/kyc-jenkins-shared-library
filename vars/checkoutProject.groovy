def call(Map config = [:]){

    checkout([$class: 'GitSCM',
               branches: [[name: "${config.branch}"]],
               doGenerateSubmoduleConfigurations: false,
               extensions: [],
               gitTool: 'Default',
               submoduleCfg: [],
               userRemoteConfigs: [[
                credentialsId: "${config.credentials}", 
                url: "https://github.com/LHPH/${config.project}.git"]]
            ]);
}