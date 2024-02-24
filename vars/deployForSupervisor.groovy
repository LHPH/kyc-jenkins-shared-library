def call(Map config = [:]){
    sh "cd target"
    loadLinuxScript(name: 'deployForSupervisor.sh')
    sh "pwd"
    sh "./deployForSupervisor.sh ${config.project} ${config.basePath}"
}