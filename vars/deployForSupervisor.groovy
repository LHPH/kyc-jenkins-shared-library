def call(Map config = [:]){
    def scriptName = 'deployForSupervisor.sh';
    loadLinuxScript(name: "${scriptName}")
    if(config.tool == 'maven'){
        sh """
            mv ${scriptName} target/${scriptName}
            cd target
            pwd
            ./${scriptName} ${config.project} ${config.basePath}
        """
    }
}