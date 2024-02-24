def call(Map config = [:]){
    def scriptContent = libraryResource "scripts/${config.name}";
    writeFile: "${config.name}", text: scriptContent
    sh "chmod a+x ./${config.name}"
}