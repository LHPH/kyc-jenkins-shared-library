def call(Map config = [:]){
    loadLinuxScript(name: 'deployForBatch.sh')
    sh "./deployForBatch.sh ${config.project} ${config.basePath}"
}