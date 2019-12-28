package com.example.kotshare.view.nav.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotshare.R;
import com.example.kotshare.controller.CityController;
import com.example.kotshare.controller.LikeController;
import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.model.City;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.view.SharedPreferencesAccessor;
import com.example.kotshare.view.recycler_views.GenericRecyclerViewAdapter;
import com.example.kotshare.view.recycler_views.StudentRoomsViewHolderTypes;
import com.example.kotshare.view.recycler_views.Util;
import com.example.kotshare.view.recycler_views.ViewHolderType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @BindView(R.id.recyclerView_allStudentRooms)
    RecyclerView recyclerView_allStudentRooms;

    @BindView(R.id.scrollView_home)
    ScrollView scrollView_home;

    @BindView(R.id.innerLayout)
    ConstraintLayout innerLayout;

    /* FORMULAIRE DE RECHERCHE */

    @BindView(R.id.spinner_searchFormPlace)
    Spinner spinner_searchFormPlace;

    @BindView(R.id.editText_searchFormMinPrice)
    EditText editText_searchFormMinPrice;

    @BindView(R.id.editText_searchFormMaxPrice)
    EditText editText_searchFormMaxPrice;

    @BindView(R.id.editText_searchFormDateMin)
    EditText editText_searchFormDateMin;

    @BindView(R.id.editText_searchFormDateMax)
    EditText editText_searchFormDateMax;

    @BindView(R.id.button_searchForm)
    Button button_searchForm;

    private boolean isCurrentlyLoading = false;
    private static final int PAGE_SIZE = 3;
    private static final int HEIGHT_REMAINING_FOR_LOADING = 800;

    private HomeViewModel homeViewModel;
    private StudentRoomController studentRoomController;
    private CityController cityController;
    private LikeController likeController;

    private GenericRecyclerViewAdapter<StudentRoom> studentRoomGenericRecyclerViewAdapter;
    private View.OnScrollChangeListener onScrollChangeListener;
    private SharedPreferencesAccessor sharedPreferencesAccessor;

    private ArrayList<StudentRoom> studentRooms;
    private List<Integer> studentRoomsIdsLiked;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GenericRecyclerViewAdapter.ViewHolderDispatcher<StudentRoom> viewHolderDispatcher = item -> {
            if (sharedPreferencesAccessor.isCurrentUser(item.getUser()))
                return ViewHolderType.STUDENT_ROOM_SELF;
            return ViewHolderType.STUDENT_ROOM_ELSE;
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        StudentRoomsViewHolderTypes studentRoomsViewHolderTypes = StudentRoomsViewHolderTypes.getInstance();

        this.studentRooms = new ArrayList<>();
        this.studentRoomsIdsLiked = new ArrayList<>();
        this.studentRoomController = new StudentRoomController();
        this.cityController = new CityController();
        this.likeController = new LikeController();
        this.sharedPreferencesAccessor = SharedPreferencesAccessor.getInstance();

        ButterKnife.bind(this, root);
        studentRoomGenericRecyclerViewAdapter = new GenericRecyclerViewAdapter<>(studentRooms,
                viewHolderDispatcher, studentRoomsViewHolderTypes.getTypes());
        recyclerView_allStudentRooms.setLayoutManager(layoutManager);
        recyclerView_allStudentRooms.setAdapter(studentRoomGenericRecyclerViewAdapter);

        Thread initLikesThread = Util.getCurrentUserLikesThread(studentRoomsIdsLiked);
        initLikesThread.start();

        try {
            initLikesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(this::initSpinner).start();
        loadNextItems();

        onScrollChangeListener = (view, i, i1, i2, i3) ->
        {
            int scrollBottomPosition = view.getScrollY() + view.getHeight();
            int scrollViewHeight = innerLayout.getHeight();
            boolean mustLoadPositionReached = !isCurrentlyLoading && scrollBottomPosition
                    >= scrollViewHeight - HEIGHT_REMAINING_FOR_LOADING;
            if (mustLoadPositionReached) loadNextItems();
        };

        scrollView_home.setOnScrollChangeListener(onScrollChangeListener);
        button_searchForm.setOnClickListener(view -> {
            initRecyclerView();
            loadNextItems();
        });

        return root;
    }

    private void initRecyclerView()
    {
        studentRoomGenericRecyclerViewAdapter.initPageNumber();
        studentRooms.clear();
        studentRoomGenericRecyclerViewAdapter.notifyDataSetChanged();
        scrollView_home.setOnScrollChangeListener(onScrollChangeListener);
    }

    private void loadNextItems() {
        final int pageIndexToLoad = studentRoomGenericRecyclerViewAdapter.getPageNumber();
        isCurrentlyLoading = true;
        studentRoomGenericRecyclerViewAdapter.loadNext();

        new Thread(() ->
        {
            Call<PagedResult<StudentRoom>> call = studentRoomController.getStudentRooms(
                    pageIndexToLoad, PAGE_SIZE,
                    getCityChoice(spinner_searchFormPlace),
                    getInteger(editText_searchFormMinPrice),
                    getInteger(editText_searchFormMaxPrice),
                    getDateMilliseconds(editText_searchFormDateMin),
                    getDateMilliseconds(editText_searchFormDateMax));
            call.enqueue(new Callback<PagedResult<StudentRoom>>() {
                @Override
                public void onResponse(Call<PagedResult<StudentRoom>> call, Response<PagedResult<StudentRoom>> response) {
                    if (response.isSuccessful()) {
                        PagedResult<StudentRoom> pagedResult = response.body();
                        boolean isMaximumCountReached;

                        Util.setLikes(pagedResult.getItems(), studentRoomsIdsLiked);

                        studentRooms.addAll(pagedResult.getItems());
                        studentRoomGenericRecyclerViewAdapter
                                .notifyItemRangeInserted(pageIndexToLoad * PAGE_SIZE, PAGE_SIZE);
                        isCurrentlyLoading = false;
                        isMaximumCountReached = pageIndexToLoad * (PAGE_SIZE + 1) >= pagedResult.getTotalCount();
                        if (isMaximumCountReached) scrollView_home.setOnScrollChangeListener(null);
                    } else Toast.makeText(getContext(), "Une erreur est survenue",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<PagedResult<StudentRoom>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Une erreur est survenue",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }



    private void initSpinner()
    {
        Call<List<City>> citiesCall = cityController.getAll();
        citiesCall.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if(response.isSuccessful()) {
                    List<City> cities = new ArrayList<>();
                    City allCities = new City();
                    ArrayAdapter<City> adapter;

                    allCities.setName("Toutes les villes");
                    cities.add(allCities);
                    cities.addAll(response.body());
                    adapter = new ArrayAdapter<>(HomeFragment.this.getContext(),
                            android.R.layout.simple_spinner_item, cities.toArray(new City[0]));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_searchFormPlace.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(HomeFragment.this.getContext(),
                            "Un erreur est survenue", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Toast.makeText(HomeFragment.this.getContext(),
                        "Un erreur est survenue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getString(EditText editText) {
        String string = editText.getText().toString();
        if (string.isEmpty()) return null;
        return string;
    }

    private Integer getCityChoice(Spinner spinner)
    {
        if(spinner.getSelectedItem() == null || spinner.getSelectedItemPosition() == 0) return null;
        City selectedCity = (City)spinner.getSelectedItem();
        return selectedCity.getId();
    }

    private Integer getInteger(EditText editText) {
        String string = editText.getText().toString();
        if (string.isEmpty()) return null;
        return Integer.parseInt(string);
    }

    private Long getDateMilliseconds(EditText editText) {
        String string = editText.getText().toString();
        if (string.isEmpty()) return null;
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).parse(string);
            Long dateTime = date.getTime();
            return dateTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}