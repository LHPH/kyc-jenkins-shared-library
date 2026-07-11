def call(Map config = [:]){

    def flavourVersion = "${config.flavour}" 
    def buildType = 'Debug'
    def format = config.format.uncapitalize();

    if(config.branch == 'master'){
        buildType = 'Release'
    }
    buildType = 'Release'

    def buildPath = "${env.WORKSPACE}/app/build/outputs/${format}/${flavourVersion.uncapitalize()}/${buildType.uncapitalize()}/"
    if(format == 'aab'){
        buildFormat = 'bundle';
        buildPath = "${env.WORKSPACE}/app/build/outputs/${buildFormat}/${flavourVersion.uncapitalize()}${buildType}/"
    }
    
    //FOR AAB ARTIFACTS REQUIRES FIREBASE ACCOUNT TO LINKED TO PLAYSTORE ACCOUNT
    /*sh """
        FILE_PATH=\$(find ${buildPath} -maxdepth 1 -type f -name "${config.project}-*.${format}")
        gradle appDistributionUpload${config.flavour}${buildType} --artifactPath="\$FILE_PATH" --artifactType="${format.toUpperCase()}" --info --stacktrace
    """
    */
}