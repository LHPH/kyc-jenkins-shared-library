def call(Map config = [:]){
    
    def skipTest = '';
    if(config.skipTest == true){
        skipTest = '-DskipTests=true';
    }

    if(config.install == true){
        sh "mvn clean install ${skipTest}".trim();
        echo 'Deploy in M2';
    }
    else{
        sh "mvn clean package ${skipTest}".trim();
    }
}