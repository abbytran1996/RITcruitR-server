package com.avalanche.tmcs.students;

import java.io.File;

/**
 * @author Max
 * @since 7/5/17
 */
public class Resume {

    private String fileName;
    private byte[] file;

    public String getFileName() {
        return fileName;
    }

    public void setFilename(String newFileName){
        this.fileName = newFileName;
    }

    public byte[] getFile(){
        return file;
    }

    public void setFile(byte[] newFile){
        this.file = newFile;
    }
}
