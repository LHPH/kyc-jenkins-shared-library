def call(Map config = [:]){
    
   def buildType = 'Debug' 
   if(config.branch == 'master'){
      buildType = 'Release'
   }

    sh "gradle -v"
    sh "gradle assemble${config.flavour}Version${buildType} --info --stacktrace".trim();
}