def call(Map config = [:]){
    loadLinuxScript(name: 'deployForSupervisor.sh')
    sh "./deployForSupervisor.sh ${config.project} ${config.basePath}"
}