def call(Map config = [:]){
    
    def skipTest = '';
    if(config.skipTest == true){
        skipTest = '-x test -x jacocoTestCoverageVerification';
    }

    sh "$GRADLE_HOME/bin/gradle clean build ${skipTest} --info --stacktrace".trim();
}