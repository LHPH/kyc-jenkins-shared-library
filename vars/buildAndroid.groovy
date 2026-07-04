def call(Map config = [:]){
    
   def buildType = 'Debug' 
   if(config.branch == 'master'){
      buildType = 'Release'
   }

    sh "pwd"
    sh "gradle -v"
    sh "gradle assemble${config.flavour}${buildType} --info --stacktrace".trim();
}