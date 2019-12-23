package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.data_access.services.StudentRoomService;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;

import java.util.ArrayList;

import retrofit2.Call;

public class StudentRoomDAO implements StudentRoomDataAccess
{

    //TODO: Supprimer
    private static StudentRoomDAO studentRoomDAO;
    private UserDataAccess userDataAccess;
    private LikeDataAccess likeDataAccess;
    private ArrayList<StudentRoom> studentRooms;
    private StudentRoomService studentRoomService;

    private StudentRoomDAO()
    {
        /*OkHttpClient httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StudentRoomService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        studentRoomService = retrofit.create(StudentRoomService.class);*/
        this.studentRoomService = ServicesConfiguration.getInstance().getRetrofit()
                .create(StudentRoomService.class);
    }

    public static StudentRoomDAO getInstance()
    {
        if(studentRoomDAO == null) studentRoomDAO = new StudentRoomDAO();
        return studentRoomDAO;
    }

    public void init()
    {
        /*this.userDataAccess = UserDAO.getInstance();
        this.likeDataAccess = LikeDAO.getInstance();
        if(studentRooms == null)
            this.studentRooms = new ArrayList<>(Arrays.asList(
                new StudentRoom(1, "Kot appartenant à John", 300., userDataAccess.find(1)),
                new StudentRoom(2, "Kot appartenant à Jane", 450., userDataAccess.find(2)),
                new StudentRoom(3, "Kot non liké", 540., userDataAccess.find(1)),
                new StudentRoom(4, "Kot supplémentaire", 540., userDataAccess.find(2))
            ));*/
    }


    @Override
    public StudentRoom add(StudentRoom studentRoom) {
        studentRooms.add(studentRoom);
        return studentRoom;
    }

    @Override
    public Call<PagedResult<StudentRoom>> getAll(Integer pageIndex, Integer pageSize) {
        return studentRoomService.getStudentRooms(pageIndex, pageSize, null, null,
                null, null, null);
    }

    @Override
    public Call<StudentRoom> find(int id) {
/*        for(StudentRoom studentRoom : studentRooms)
            if(studentRoom.getId() == id)
                return studentRoom;*/
        return studentRoomService.getStudentRoomById(id);
    }

    @Override
    public Call<PagedResult<StudentRoom>> where(Predicate<StudentRoom> predicate) {
        ArrayList<StudentRoom> corresponding = new ArrayList<>();

        for(StudentRoom studentRoom : studentRooms)
            if(predicate.verify(studentRoom))
                corresponding.add(studentRoom);

        return null;
    }

    @Override
    public StudentRoom update(StudentRoom object) {
        return null;
    }

    @Override
    public void delete(StudentRoom studentRoom) {
        /*StudentRoom foundStudentRoom = find(studentRoom.getId());*/
        /*studentRooms.remove(foundStudentRoom);*/
    }

    @Override
    public ArrayList<StudentRoom> getAllLikedBy(int userId) {
        return null;
    }

    @Override
    public Call<PagedResult<StudentRoom>> getStudentRooms(Integer pageIndex, Integer pageSize,
                                                          Integer cityId, Integer minPrice,
                                                          Integer maxPrice, Long startDate,
                                                          Long endDate) {
        return studentRoomService.getStudentRooms(pageIndex, pageSize, cityId, minPrice, maxPrice,
                startDate, endDate);
    }


}
