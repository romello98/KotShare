package com.example.kotshare.model.data_model;

public class PhotoDataModel
{
    private Integer studentRoomId;
    private Integer version;
    private String name;
    private String format;

    public Integer getStudentRoomId() {
        return studentRoomId;
    }

    public void setStudentRoomId(Integer studentRoomId) {
        this.studentRoomId = studentRoomId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
