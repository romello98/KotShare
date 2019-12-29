package com.example.kotshare.view.nav.my_student_rooms;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotshare.R;
import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.data_access.StudentRoomDAO;
import com.example.kotshare.data_access.StudentRoomDataAccess;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.view.SharedPreferencesAccessor;
import com.example.kotshare.view.activities.EditStudentRoomActivity;
import com.example.kotshare.view.recycler_views.BindLogic;
import com.example.kotshare.view.recycler_views.GenericRecyclerViewAdapter;
import com.example.kotshare.view.recycler_views.StudentRoomsViewHolderTypes;
import com.example.kotshare.view.recycler_views.ViewHolderType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStudentRoomsFragment extends Fragment {

    private View root;

    @BindView(R.id.recyclerView_myStudentRooms)
    public RecyclerView recyclerView_myStudentRooms;

    @BindView(R.id.fab_addStudentRoom)
    FloatingActionButton fab_addStudentRoom;

    private GenericRecyclerViewAdapter<StudentRoom> studentRoomGenericRecyclerViewAdapter;
    private SharedPreferencesAccessor sharedPreferencesAccessor = SharedPreferencesAccessor.getInstance();
    private StudentRoomsViewHolderTypes studentRoomsViewHolderTypes = StudentRoomsViewHolderTypes.getInstance();

    private MyStudentRoomsViewModel myStudentRoomsViewModel;
    private StudentRoomController studentRoomController;

    private ArrayList<StudentRoom> studentRooms;

    public MyStudentRoomsFragment()
    {
        this.studentRoomController = new StudentRoomController();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread retrieveStudentRoomsThread;
        GenericRecyclerViewAdapter.ViewHolderDispatcher<StudentRoom> viewHolderDispatcher =
                item -> ViewHolderType.STUDENT_ROOM_SELF;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        myStudentRoomsViewModel =
                ViewModelProviders.of(this).get(MyStudentRoomsViewModel.class);
        root = inflater.inflate(R.layout.fragment_my_student_rooms, container, false);
        ButterKnife.bind(this, root);
        studentRooms = new ArrayList<>();
        studentRoomGenericRecyclerViewAdapter = new GenericRecyclerViewAdapter<>(studentRooms,
                viewHolderDispatcher, studentRoomsViewHolderTypes.getTypes());

        retrieveStudentRoomsThread = new Thread(() -> {
            Call<PagedResult<StudentRoom>> call = studentRoomController.getOwnStudentRooms(
                    sharedPreferencesAccessor.getUser().getId(), null, 100);
            call.enqueue(new Callback<PagedResult<StudentRoom>>() {
                @Override
                public void onResponse(Call<PagedResult<StudentRoom>> call, Response<PagedResult<StudentRoom>> response) {
                    studentRooms.addAll(response.body().getItems());
                    studentRoomGenericRecyclerViewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<PagedResult<StudentRoom>> call, Throwable t) {

                }
            });
        });

        retrieveStudentRoomsThread.start();
        recyclerView_myStudentRooms.setLayoutManager(layoutManager);
        recyclerView_myStudentRooms.setAdapter(studentRoomGenericRecyclerViewAdapter);

        fab_addStudentRoom.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditStudentRoomActivity.class);
            startActivity(intent);
        });

        return root;
    }
}