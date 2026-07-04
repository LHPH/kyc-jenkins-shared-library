def call(Map config = [:]){
    
   def flavourVersion = "${config.flavour}" 
   def buildType = 'Debug'
   if(config.branch == 'master'){
      buildType = 'Release'
   }

    def buildApkPath = "app/build/outputs/apk/${flavourVersion.uncapitalize()}/${buildType.toLowerCase()}/"

    sh "gradle -v"
    sh "gradle assemble${flavourVersion}${buildType} --info --stacktrace".trim();
    sh """
        cd ${buildApkPath}
        ls -lta
        for file in ${config.project}-*.apk; do 
            mv "\$file" "\${file%.apk}.${env.BUILD_NUMBER}.apk"
        done
        ls -lta
        cd ${env.WORKSPACE}
    """
}