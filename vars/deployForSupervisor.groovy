def call(Map config = [:]){
    loadLinuxScript(name: 'deployForSupervisor.sh')
    if(config.tool == 'maven'){
        sh """
            mv deployForSupervisor.sh target/deployForSupervisor.sh
            cd target
            pwd
            ./deployForSupervisor.sh ${config.project} ${config.basePath}
        """
    }
}