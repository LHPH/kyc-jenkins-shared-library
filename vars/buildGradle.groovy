def call(Map config = [:]){
    
    def skipTest = '';
    if(config.skipTest == true){
        skipTest = '-x test -x jacocoTestCoverageVerification';
    }

    sh "/opt/gradle/gradle-8.13/bin/gradle clean build ${skipTest} --info --stacktrace".trim();
}