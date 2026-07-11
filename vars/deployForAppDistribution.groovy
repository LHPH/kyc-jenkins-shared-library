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

    

    sh """
        FILE_PATH=\$(find ${buildPath} -maxdepth 1 -type f -name "${config.project}-*.${format}")
        ls -lta \$FILE_PATH
        echo \$FILE_PATH
    """

    /*sh """
        gradle appDistributionUpload${config.flavour}${buildType} --artifactPath="\$FILE_PATH" --artifactType="${format.toUpperCase()}" --info --stacktrace

        FILE_PATH=\$(find ${buildPath} -maxdepth 1 -type f -name "${config.project}-*.${format}")
        echo \$FILE_PATH
        gradle appDistributionUpload${config.flavour}${buildType} -PfirebaseAppDistribution.apkPath="\$FILE_PATH" -PartifactType="${format.toUpperCase()}" --info --stacktrace
    """*/
}