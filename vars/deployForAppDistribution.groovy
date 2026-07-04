def call(Map config = [:]){

    def flavourVersion = "${config.flavour}" 
    def buildType = 'Debug'
    if(config.branch == 'master'){
       buildType = 'Release'
    }

    def buildApkPath = "app/build/outputs/apk/${flavourVersion.uncapitalize()}/${buildType.toLowerCase()}/"

    sh """
        FILE_PATH=\$(find ${buildApkPath} -maxdepth 1 -type f -name "${config.project}-*.apk")
        echo \$FILE_PATH
        echo \$FIREBASE_JSON_TEXT > firebase-key.json
        export GOOGLE_APPLICATION_CREDENTIALS="firebase-key.json"
        gradle appDistributionUpload${config.flavour}${buildType} -PfirebaseAppDistribution.apkPath="\$FILE_PATH" --info --stacktrace
        rm firebase-key.json
    """
}