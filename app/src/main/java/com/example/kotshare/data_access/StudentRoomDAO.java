package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.data_access.services.StudentRoomService;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.form.StudentRoomForm;

import java.util.ArrayList;

import retrofit2.Call;

public class StudentRoomDAO implements StudentRoomDataAccess
{

    //TODO: Supprimer
    private static StudentRoomDAO studentRoomDAO;
    private StudentRoomService studentRoomService;

    private StudentRoomDAO()
    {
        this.studentRoomService = ServicesConfiguration.getInstance().getRetrofit()
                .create(StudentRoomService.class);
    }

    public static StudentRoomDAO getInstance()
    {
        if(studentRoomDAO == null) studentRoomDAO = new StudentRoomDAO();
        return studentRoomDAO;
    }

    @Override
    public StudentRoom add(StudentRoom studentRoom) {
        return null;
    }

    @Override
    public Call<PagedResult<StudentRoom>> getAll(Integer pageIndex, Integer pageSize) {
        return studentRoomService.getStudentRooms(pageIndex, pageSize, null, null,
                null, null, null);
    }

    @Override
    public Call<StudentRoom> find(int id) {
        return studentRoomService.getStudentRoomById(id);
    }

    @Override
    public Call<PagedResult<StudentRoom>> where(Predicate<StudentRoom> predicate) {
        return null;
    }

    @Override
    public StudentRoom update(StudentRoom object) {
        return null;
    }

    @Override
    public void delete(StudentRoom studentRoom) {
    }

    @Override
    public ArrayList<StudentRoom> getAllLikedBy(int userId) {
        return null;
    }

    @Override
    public Call<StudentRoom> addStudentRoom(StudentRoomForm studentRoomForm) {
        return studentRoomService.addStudentRoom(studentRoomForm);
    }

    @Override
    public Call<StudentRoom> updateStudentRoom(int id, StudentRoomForm studentRoomForm) {
        return studentRoomService.updateStudentRoom(id, studentRoomForm);
    }

    @Override
    public Call<PagedResult<StudentRoom>> getStudentRooms(Integer pageIndex, Integer pageSize,
                                                          Integer cityId, Integer minPrice,
                                                          Integer maxPrice, Long startDate,
                                                          Long endDate) {
        return studentRoomService.getStudentRooms(pageIndex, pageSize, cityId, minPrice, maxPrice,
                startDate, endDate);
    }

    @Override
    public Call<PagedResult<StudentRoom>> getOwnStudentRooms(Integer userId, Integer pageIndex,
                                                             Integer pageSize) {
        return studentRoomService.getOwnStudentRooms(userId, pageIndex, pageSize);
    }

    @Override
    public Call<PagedResult<StudentRoom>> getFavoriteStudentRooms(Integer userId, Integer pageIndex,
                                                                  Integer pageSize) {
        return studentRoomService.getFavoriteStudentRooms(userId, pageIndex, pageSize);
    }


}
