def call(Map config = [:]){

   def scriptName = 'buildNode.sh';
   loadLinuxScript(name: "${scriptName}")
   sh "./${scriptName} ${config.cache} ${config.service} ${config.skipTest}"
}