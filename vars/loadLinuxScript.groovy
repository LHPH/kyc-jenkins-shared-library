def call(Map config = [:]){
    def scriptContent = libraryResource "scripts/${config.name}";
    writeFile file: "${config.name}", text: scriptContent
    sh "chmod a+x ./${config.name}"
}