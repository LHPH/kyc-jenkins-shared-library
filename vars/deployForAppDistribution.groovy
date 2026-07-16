import com.kyc.jenkins.dto.AppPipelineContext

def call(AppPipelineContext ctx){

    def project = ctx.project
    def format = ctx.format
    def flavourVersion = ctx.flavour
    def buildType = ctx.getBuildType().capitalize()
    def buildFormat = ctx.getBuildFormat()
    def targetPath = ctx.getTargetPath("${env.WORKSPACE}")
    
    //FOR AAB ARTIFACTS REQUIRES FIREBASE ACCOUNT TO LINKED TO PLAYSTORE ACCOUNT
    sh """
        FILE_PATH=\$(find ${targetPath} -maxdepth 1 -type f -name "${project}-*.${format}")
        gradle appDistributionUpload${flavourVersion}${buildType} --artifactPath="\$FILE_PATH" --artifactType="${format.toUpperCase()}" --info --stacktrace
    """
}