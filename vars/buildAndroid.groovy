def call(Map config = [:]){
    
   def flavourVersion = "${config.flavour}Version" 
   def buildType = 'Debug'
   if(config.branch == 'master'){
      buildType = 'Release'
   }

    sh "gradle -v"
    sh "gradle assemble${flavourVersion}${buildType} --info --stacktrace".trim();
    sh """
        cd app/build/outputs/apk/${flavourVersion}/${buildType.toLowerCase()}
        for file in app-*-${buildType.toLowerCase()}; do 
            mv "\$file" "\${file%.apk}-${BUILD_VERSION}.apk"
        done
        ls -lta
    """
}