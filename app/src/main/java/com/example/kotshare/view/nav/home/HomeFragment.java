package com.example.kotshare.view.nav.home;

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
import com.example.kotshare.data_access.StudentRoomDAO;
import com.example.kotshare.data_access.StudentRoomDataAccess;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.view.SharedPreferencesAccessor;
import com.example.kotshare.view.recycler_views.BindLogic;
import com.example.kotshare.view.recycler_views.GenericRecyclerViewAdapter;
import com.example.kotshare.view.recycler_views.StudentRoomsViewHolderTypes;
import com.example.kotshare.view.recycler_views.ViewHolderType;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.recyclerView_allStudentRooms)
    RecyclerView recyclerView_allStudentRooms;

    private HomeViewModel homeViewModel;
    private StudentRoomDataAccess studentRoomDataAccess = new StudentRoomDAO();

    private GenericRecyclerViewAdapter<StudentRoom> studentRoomGenericRecyclerViewAdapter;
    private SharedPreferencesAccessor sharedPreferencesAccessor = SharedPreferencesAccessor.getInstance();
    private StudentRoomsViewHolderTypes studentRoomsViewHolderTypes = StudentRoomsViewHolderTypes.getInstance();

    private ArrayList<StudentRoom> studentRooms;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GenericRecyclerViewAdapter.ViewHolderDispatcher<StudentRoom> viewHolderDispatcher = item -> {
            if(sharedPreferencesAccessor.isCurrentUser(item.getHolder()))
                return ViewHolderType.STUDENT_ROOM_SELF;
            return ViewHolderType.STUDENT_ROOM_ELSE;
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        ButterKnife.bind(this, root);
        this.studentRooms = studentRoomDataAccess.getAll();

        studentRoomGenericRecyclerViewAdapter = new GenericRecyclerViewAdapter<>(studentRooms,
                viewHolderDispatcher, studentRoomsViewHolderTypes.getTypes());
        recyclerView_allStudentRooms.setLayoutManager(layoutManager);
        recyclerView_allStudentRooms.setAdapter(studentRoomGenericRecyclerViewAdapter);

        return root;
    }
}