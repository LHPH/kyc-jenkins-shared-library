def call(Map config = [:]){
    
    def skipTest = '';
    if(config.skipTest == true){
        skipTest = '-x test -x jacocoTestCoverageVerification';
    }

    sh """
       echo $GRADLE_HOME
       gradle -v
       node -v
    """
    sh "gradle clean build ${skipTest} --info --stacktrace".trim();
}