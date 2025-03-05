def call(Map config = [:]){

    if(config.tool == "maven"){
        buildMaven(install: config.install, skipTest: config.skipTest);
    }
    else if(config.tool == "gradle"){
        buildGradle(skipTest: config.skipTest);
    }
    else{
        echo "Invalid option"
        sh 'exit 1'
    }
}