package com.example.milamix.wondercal.Models;

public class DataPartModels {
    private String fileName;
    private byte[] content;
    private String type;

    public DataPartModels() {
    }

    public DataPartModels(String name, byte[] data) {
        this.fileName = name;
        this.content = data;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
