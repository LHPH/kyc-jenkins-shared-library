import com.kyc.jenkins.dto.AppPipelineContext

def call(AppPipelineContext ctx){
    
    def flavourVersion = ctx.flavour
    def buildType = ctx.getBuildType().capitalize()
    def buildFormat = ctx.getBuildFormat()
    def targetPath = ctx.getTargetPath("${env.WORKSPACE}")

    sh """
        gradle -v
        gradle ${buildFormat}${flavourVersion}${buildType} --info --stacktrace
        ls -lta ${targetPath}
    """
}