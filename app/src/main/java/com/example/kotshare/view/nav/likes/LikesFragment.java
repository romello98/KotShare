package com.example.kotshare.view.nav.likes;

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
import com.example.kotshare.controller.LikeController;
import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.data_access.StudentRoomDAO;
import com.example.kotshare.data_access.StudentRoomDataAccess;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;
import com.example.kotshare.view.SharedPreferencesAccessor;
import com.example.kotshare.view.nav.my_student_rooms.MyStudentRoomsViewModel;
import com.example.kotshare.view.recycler_views.BindLogic;
import com.example.kotshare.view.recycler_views.GenericRecyclerViewAdapter;
import com.example.kotshare.view.recycler_views.StudentRoomsViewHolderTypes;
import com.example.kotshare.view.recycler_views.Util;
import com.example.kotshare.view.recycler_views.ViewHolderType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikesFragment extends Fragment {

    private View root;

    @BindView(R.id.recyclerView_myLikedStudentRooms)
    public RecyclerView recyclerView_myStudentRooms;

    private GenericRecyclerViewAdapter<StudentRoom> studentRoomGenericRecyclerViewAdapter;
    private SharedPreferencesAccessor sharedPreferencesAccessor = SharedPreferencesAccessor.getInstance();
    private StudentRoomsViewHolderTypes studentRoomsViewHolderTypes = StudentRoomsViewHolderTypes.getInstance();

    private StudentRoomController studentRoomController;
    private LikeController likeController;

    private ArrayList<StudentRoom> studentRooms;
    private List<Integer> likedIds;
    private LikesViewModel likesViewModel;

    public LikesFragment()
    {
        this.studentRoomController = new StudentRoomController();
        this.likeController = new LikeController();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        GenericRecyclerViewAdapter.ViewHolderDispatcher<StudentRoom> viewHolderDispatcher =
                item -> ViewHolderType.STUDENT_ROOM_ELSE;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        Thread retrieveStudentRoomsThread;
        Thread likesThread;
        likesViewModel =
                ViewModelProviders.of(this).get(LikesViewModel.class);
        root = inflater.inflate(R.layout.fragment_likes, container, false);
        studentRoomController = new StudentRoomController();
        studentRooms = new ArrayList<>();
        likedIds = new ArrayList<>();
        ButterKnife.bind(this, root);
        studentRoomGenericRecyclerViewAdapter = new GenericRecyclerViewAdapter<>(studentRooms,
                viewHolderDispatcher, studentRoomsViewHolderTypes.getTypes());

        retrieveStudentRoomsThread = new Thread(() -> {
            Call<PagedResult<StudentRoom>> call = studentRoomController.getFavoriteStudentRooms
                    (sharedPreferencesAccessor.getUser().getId(), null, 100);
            call.enqueue(new Callback<PagedResult<StudentRoom>>() {
                @Override
                public void onResponse(Call<PagedResult<StudentRoom>> call,
                                       Response<PagedResult<StudentRoom>> response) {
                    Util.setLikes(response.body().getItems(), likedIds);
                    studentRooms.addAll(response.body().getItems());
                    studentRoomGenericRecyclerViewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<PagedResult<StudentRoom>> call,
                                      Throwable t) {

                }
            });
        });
        likesThread = Util.getCurrentUserLikesThread(likedIds);
        likesThread.start();

        try {
            likesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        retrieveStudentRoomsThread.start();

        recyclerView_myStudentRooms.setLayoutManager(layoutManager);
        recyclerView_myStudentRooms.setAdapter(studentRoomGenericRecyclerViewAdapter);

        return root;
    }
}