package com.kyc.jenkins.dto

class AppPipelineContext implements Serializable{

    String project = ''
    String format = 'apk'
    String flavour = ''
    String branch = ''

    public String getBuildType(){

        return branch == 'master' ? 'release' : 'debug';
    }

    public String getBuildFormat(){
        return format == 'aab' ? 'bundle' : 'assemble';
    }

    public String getTargetPath(){

        if(format == 'aab'){
            return "${env.WORKSPACE}/app/build/outputs/bundle/${flavour.uncapitalize()}${getBuildType().capitalize()}/"
        }
        else if(format == 'apk'){
            return "${env.WORKSPACE}/app/build/outputs/apk/${flavour.uncapitalize()}/${getBuildType()}/"
        }
        throw UnsupportedOperationException('Unsupported operation');
    }
}