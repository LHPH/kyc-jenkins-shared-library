def call(Map config = [:]){
    loadLinuxScript(name: 'deployWithSupervisor.sh')
    sh "./deployWithSupervisor.sh ${config.project} ${config.basePath}"
}