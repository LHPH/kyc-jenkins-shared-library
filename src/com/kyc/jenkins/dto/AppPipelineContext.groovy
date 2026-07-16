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

    public String getTargetPath(String workspacePath){

        if(format == 'aab'){
            return "${workspacePath}/app/build/outputs/bundle/${flavour.uncapitalize()}${getBuildType().capitalize()}/"
        }
        else if(format == 'apk'){
            return "${workspacePath}/app/build/outputs/apk/${flavour.uncapitalize()}/${getBuildType()}/"
        }
        throw UnsupportedOperationException('Unsupported operation');
    }
}