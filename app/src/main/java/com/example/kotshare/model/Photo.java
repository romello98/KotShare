package com.example.kotshare.model;

public class Photo
{
    private Integer id;
    private Integer versionNumber;
    private String fileName;
    private String extension;
    private StudentRoom studentRoom;

    public Photo(){}

    public Photo(Integer id, Integer versionNumber, String fileName, String extension,
                 StudentRoom studentRoom) {
        setId(id);
        setVersionNumber(versionNumber);
        setFileName(fileName);
        setExtension(extension);
        setStudentRoom(studentRoom);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public StudentRoom getStudentRoom() {
        return studentRoom;
    }

    public void setStudentRoom(StudentRoom studentRoom) {
        this.studentRoom = studentRoom;
    }
}
