def call(Map config = [:]){
    sh "pwd"
    sh "cd target"
    sh "pwd"
    loadLinuxScript(name: 'deployForSupervisor.sh')
    sh "./deployForSupervisor.sh ${config.project} ${config.basePath}"
}