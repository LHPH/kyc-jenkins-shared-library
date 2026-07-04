def call(Map config = [:]){

    def flavourVersion = "${config.flavour}" 
    def buildType = 'Debug'
    if(config.branch == 'master'){
       buildType = 'Release'
    }

    def buildApkPath = "app/build/outputs/apk/${flavourVersion.uncapitalize()}/${buildType.toLowerCase()}/"

    sh """
        echo \$(find ${buildApkPath} -maxdepth 1 -type f -name "${config.project}-*.apk")
    """
    //gradle appDistributionUpload${config.flavour}${buildType} -PfirebaseAppDistribution.apkPath="/path/to/your/RENAMED_APP.apk"
}