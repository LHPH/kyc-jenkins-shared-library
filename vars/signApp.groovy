import com.kyc.jenkins.dto.AppPipelineContext

def call(AppPipelineContext ctx){

    def project = ctx.project
    def format = ctx.format
    def flavourVersion = ctx.flavour
    def buildType = ctx.getBuildType().capitalize()
    def buildFormat = ctx.getBuildFormat()
    def targetPath = ctx.getTargetPath()

    if(buildType == 'Release'){

        if(format == 'apk'){

            def scriptName = 'signApk.sh';
            loadLinuxScript(name: "${scriptName}")

            sh """
                FILE_PATH=\$(find ${targetPath} -maxdepth 1 -type f -name "${project}-*.${format}")
                printf '%s' '${KEYSTORE_BASE64}' | base64 -d > \$WORKSPACE/release-key.jks

                ./${scriptName} \$FILE_PATH \$FILE_PATH \$WORKSPACE/release-key.jks
            """
        }

        if(format == 'aab'){

            def scriptName = 'signJar.sh';
            loadLinuxScript(name: "${scriptName}")

            sh """
                FILE_PATH=\$(find ${targetPath} -maxdepth 1 -type f -name "${project}-*.${format}")
                printf '%s' '${KEYSTORE_BASE64}' | base64 -d > \$WORKSPACE/release-key.jks

                ./${scriptName} \$FILE_PATH \$WORKSPACE/release-key.jks
            """
        }
    }
    else{
        sh 'echo "Artifact does not require sign"'
    }
}