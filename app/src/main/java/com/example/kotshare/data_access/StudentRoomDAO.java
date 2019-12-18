package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class StudentRoomDAO implements StudentRoomDataAccess
{

    //TODO: Supprimer
    private UserDataAccess userDataAccess;
    private ArrayList<StudentRoom> studentRooms;

    public StudentRoomDAO()
    {
        // TODO : À supprimer, simulation de données
        this.userDataAccess = new UserDAO();
        this.studentRooms = new ArrayList<>(Arrays.asList(
                new StudentRoom(1, "Kot appartenant à moi-même", 300., userDataAccess.find(1)),
                new StudentRoom(2, "Kot appartenant à autrui", 450., userDataAccess.find(2)),
                new StudentRoom(3, "Kot non liké", 540., userDataAccess.find(1))
        ));
    }

    @Override
    public StudentRoom add(StudentRoom studentRoom) {
        studentRooms.add(studentRoom);
        return studentRoom;
    }

    @Override
    public ArrayList<StudentRoom> getAll() {
        return studentRooms;
    }

    @Override
    public StudentRoom find(int id) {
        for(StudentRoom studentRoom : studentRooms)
            if(studentRoom.getId() == id)
                return studentRoom;
        return null;
    }

    @Override
    public ArrayList<StudentRoom> where(Predicate<StudentRoom> predicate) {
        ArrayList<StudentRoom> corresponding = new ArrayList<>();

        for(StudentRoom studentRoom : studentRooms)
            if(predicate.verify(studentRoom))
                corresponding.add(studentRoom);

        return corresponding;
    }

    @Override
    public StudentRoom update(StudentRoom object) {
        return null;
    }

    @Override
    public void delete(StudentRoom studentRoom) {
        StudentRoom foundStudentRoom = find(studentRoom.getId());
        studentRooms.remove(foundStudentRoom);
    }

    @Override
    public ArrayList<StudentRoom> getAllLikedBy(User user) {
        ArrayList<StudentRoom> likedStudentRooms = new ArrayList<>();
        for(StudentRoom studentRoom : studentRooms)
            for(Like like : studentRoom.getLikes())
                if(user.getLikes().contains(like))
                    likedStudentRooms.add(studentRoom);
        return likedStudentRooms;
    }

    @Override
    public boolean isLikedBy(StudentRoom studentRoom, User user) {
        return getAllLikedBy(user).contains(studentRoom);
    }


}
