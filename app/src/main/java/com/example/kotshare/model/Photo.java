package com.example.kotshare.model;

public class Photo
{
    private Integer id;
    private Integer version;
    private String name;
    private String format;
    private StudentRoom studentRoom;

    public Photo(){}

    public Photo(Integer id, Integer version, String name, String format,
                 StudentRoom studentRoom) {
        setId(id);
        setVersion(version);
        setName(name);
        setFormat(format);
        setStudentRoom(studentRoom);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public StudentRoom getStudentRoom() {
        return studentRoom;
    }

    public void setStudentRoom(StudentRoom studentRoom) {
        this.studentRoom = studentRoom;
    }
}
