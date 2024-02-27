def call(Map config = [:]){
    def scriptName = 'deployForSupervisor.sh';
    loadLinuxScript(name: "${scriptName}")
     sh """
          pwd
          ./${scriptName} ${config.project} ${config.basePath}
        """
}