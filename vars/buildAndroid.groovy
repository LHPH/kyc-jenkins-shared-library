def call(Map config = [:]){
    
   def flavourVersion = "${config.flavour}" 
   def buildType = 'Debug'
   if(config.branch == 'master'){
      buildType = 'Release'
   }

    sh "gradle -v"
    sh "gradle assemble${flavourVersion}${buildType} --info --stacktrace".trim();
    sh """
        cd app/build/outputs/apk/${flavourVersion.uncapitalize()}/${buildType.toLowerCase()}
        ls -lta
        for file in app-*-${buildType.toLowerCase()}.apk; do 
            mv "\$file" "\${file%.apk}.${env.BUILD_NUMBER}.apk"
        done
        ls -lta
    """
}