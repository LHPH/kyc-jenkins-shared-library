def call(Map config = [:]){
    
    def skipTest = '';
    if(config.skipTest == true){
        skipTest = '-x test -x jacocoTestCoverageVerification';
    }

    sh """
    whoami
    ls -lta /opt/gradle/gradle-8.13/bin
    /opt/gradle/gradle-8.13/bin/gradle -v
    gradle -v
    """
    sh "gradle clean build ${skipTest} --info --stacktrace".trim();
}