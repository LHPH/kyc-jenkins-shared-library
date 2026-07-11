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
    """

    if(buildType == 'Release'){

        sh '''

            FILE_PATH=\$(find ${buildPath} -maxdepth 1 -type f -name "${config.project}-*.${format}")

            echo "'$KEYSTORE_BASE64' | base64 -d > $WORKSPACE/release-key.jks"
            ls -lta $WORKSPACE/release-key.jks

            \$ANDROID_HOME/build-tools/35.0.0/apksigner sign \
            --ks ${env.WORKSPACE}/release-key.jks \
            --ks-pass pass: $KEYSTORE_PASSWORD \
            --ks-key-alias $KEY_ALIAS \
            --key-pass pass: ${KEY_PASSWORD} \
            --out \$FILE_PATH \
            \$FILE_PATH
        '''
    }

    sh """
        cd ${buildPath}
        ls -lta
    """
}