def call(Map config = [:]){
    
    def flavourVersion = "${config.flavour}" 
    def buildType = 'Debug'
    def format = config.format.uncapitalize();

    if(config.branch == 'master'){
        buildType = 'Release'
    }
    buildType= 'Release'

    def buildFormat = 'assemble';
    def buildPath = "app/build/outputs/${format}/${flavourVersion.uncapitalize()}/${buildType.uncapitalize()}/"
    if(format == 'aab'){
        buildFormat = 'bundle';
        buildPath = "app/build/outputs/${buildFormat}/${flavourVersion.uncapitalize()}${buildType}/"
    }

    sh """
        gradle -v
        gradle ${buildFormat}${flavourVersion}${buildType} --info --stacktrace
        cd ${buildPath}
        ls -lta
    """
}