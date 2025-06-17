def call(Map config = [:]){
    
    def skipTest = '';
    if(config.skipTest == true){
        skipTest = '-x test -x jacocoTestCoverageVerification';
    }

    sh "gradle -v"
    sh "gradle clean build ${skipTest} --info --stacktrace".trim();
}