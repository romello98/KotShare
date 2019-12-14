package com.example.kotshare.data_access;

import com.example.kotshare.model.StudentRoom;

import java.util.ArrayList;
import java.util.Arrays;

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
                new StudentRoom(1, "Kot appartenant à moi-même", "Exemple de description", userDataAccess.find(1)),
                new StudentRoom(2, "Kot appartenant à autrui", "Exemple de description", userDataAccess.find(2))
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
}
