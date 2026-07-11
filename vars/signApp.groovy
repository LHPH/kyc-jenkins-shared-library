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

    if(buildType == 'Release'){

        if(format == 'apk'){

            def scriptName = 'signApk.sh';
            loadLinuxScript(name: "${scriptName}")

            sh """
                FILE_PATH=\$(find ${buildPath} -maxdepth 1 -type f -name "${config.project}-*.${format}")
                printf '%s' '${KEYSTORE_BASE64}' | base64 -d > \$WORKSPACE/release-key.jks

                ./${scriptName} \$FILE_PATH \$FILE_PATH \$WORKSPACE/release-key.jks
            """
        }

        if(format == 'aab'){

            def scriptName = 'signJar.sh';
            loadLinuxScript(name: "${scriptName}")

            sh """
                FILE_PATH=\$(find ${buildPath} -maxdepth 1 -type f -name "${config.project}-*.${format}")
                printf '%s' '${KEYSTORE_BASE64}' | base64 -d > \$WORKSPACE/release-key.jks

                ./${scriptName} \$FILE_PATH \$WORKSPACE/release-key.jks
            """
        }
    }
    else{
        sh 'echo "Artifact does not require sign"'
    }
}