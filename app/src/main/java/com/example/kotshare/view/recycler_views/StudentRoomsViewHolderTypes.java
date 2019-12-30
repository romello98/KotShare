package com.example.kotshare.view.recycler_views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.kotshare.R;
import com.example.kotshare.controller.LikeController;
import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.model.Photo;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;
import com.example.kotshare.view.PhotosManager;
import com.example.kotshare.view.SharedPreferencesAccessor;
import com.example.kotshare.view.activities.EditStudentRoomActivity;
import com.example.kotshare.view.activities.StudentRoomActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRoomsViewHolderTypes
{
    private StudentRoomController studentRoomController;
    private LikeController likeController;
    private User user;
    private static StudentRoomsViewHolderTypes studentRoomsViewHolderTypes;
    private HashMap<ViewHolderType, BindLogic<StudentRoom>> viewHolderTypesLogic;
    private BindLogic.BindFunction<StudentRoom> commonBind;

    private StudentRoomsViewHolderTypes()
    {
        this.user = SharedPreferencesAccessor.getInstance().getUser();
        this.studentRoomController = new StudentRoomController();
        this.likeController = new LikeController();
        this.viewHolderTypesLogic = new HashMap<>();

        commonBind = (studentRoom, viewHolder) -> {
                Context context = viewHolder.itemView.getContext();
                CardView cardView = viewHolder.itemView.findViewById(R.id.cardview_studentRoomItem);
                ImageView backgroundImage = viewHolder.itemView.findViewById(R.id.imageView_photoStudentRoom);
                TextView price = viewHolder.itemView.findViewById(R.id.textView_studentRoomPrice);
                TextView title = viewHolder.itemView.findViewById(R.id.textView_studentRoomTitle);
                TextView city = viewHolder.itemView.findViewById(R.id.textView_studentRoomPlace);
                price.setText(String.format(context.getString(R.string.price_format),
                        String.format(Locale.FRENCH, "%d", studentRoom.getMonthlyPrice())));
                title.setText(studentRoom.getTitle());
                city.setText(studentRoom.getCity().toString());

                if(studentRoom.getPhoto() != null && studentRoom.getPhoto().size() > 0) {
                    Photo firstPhoto = studentRoom.getPhoto().get(0);
                    Glide.with(context).load(PhotosManager.getInstance(context)
                            .getBaseUrl(studentRoom.getId(), firstPhoto))
                            .centerCrop()
                            .into(backgroundImage);
                }

                cardView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, StudentRoomActivity.class);
                    if(studentRoom.getId() != null) {
                        intent.putExtra(context.getString(R.string.STUDENT_ROOM_ID), studentRoom.getId());
                        context.startActivity(intent);
                    }
                });
        };

        this.viewHolderTypesLogic.put(ViewHolderType.STUDENT_ROOM_SELF,
                new BindLogic<>(R.layout.student_room_recycler_view_item, (studentRoom, viewHolder) -> {
                    commonBind.bind(studentRoom, viewHolder);
                    ImageView editButton = viewHolder.itemView.findViewById(R.id.imageView_action);
                    editButton.setImageDrawable(viewHolder.itemView.getContext()
                            .getDrawable(R.drawable.ic_edit_24dp));
                    editButton.setOnClickListener(view -> {
                        Intent intent = new Intent(view.getContext(), EditStudentRoomActivity.class);
                        if(studentRoom.getId() != null) {
                            intent.putExtra(view.getContext().getString(R.string.STUDENT_ROOM_ID),
                                    studentRoom.getId());
                            view.getContext().startActivity(intent);
                        }
                    });
                }));

        this.viewHolderTypesLogic.put(ViewHolderType.STUDENT_ROOM_ELSE,
                new BindLogic<>(R.layout.student_room_recycler_view_item, (studentRoom, viewHolder) -> {
                    commonBind.bind(studentRoom, viewHolder);
                    Context context = viewHolder.itemView.getContext();
                    ImageView likeButton = viewHolder.itemView.findViewById(R.id.imageView_action);
                    Drawable favoriteFilled = context.getDrawable(R.drawable.ic_favorite_filled_24dp);
                    Drawable favoriteEmpty = context.getDrawable(R.drawable.ic_favorite_empty_24dp);
                    boolean isLikedByUser = studentRoom.isLiked();
                    if (isLikedByUser)
                        likeButton.setImageDrawable(favoriteFilled);
                    else
                        likeButton.setImageDrawable(favoriteEmpty);

                    likeButton.setOnClickListener(e ->
                            new Thread(() -> {
                                if (studentRoom.isLiked()) {
                                    Call call = likeController.unlike(user.getId(),
                                            studentRoom.getId());
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onResponse(Call call, Response response) {
                                            if(response.isSuccessful()) {
                                                likeButton.setImageDrawable(favoriteEmpty);
                                                studentRoom.setLiked(false);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {

                                        }
                                    });
                                } else {
                                    Call call = likeController.sendLike(user.getId(), studentRoom.getId());
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onResponse(Call call, Response response) {
                                            if(response.isSuccessful()) {
                                                likeButton.setImageDrawable(favoriteFilled);
                                                studentRoom.setLiked(true);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {

                                        }
                                    });
                                }
                            }).start());
                })
        );
    }

    public HashMap<ViewHolderType, BindLogic<StudentRoom>> getTypes()
    {
        return this.viewHolderTypesLogic;
    }

    public static StudentRoomsViewHolderTypes getInstance()
    {
        if(studentRoomsViewHolderTypes == null)
            studentRoomsViewHolderTypes = new StudentRoomsViewHolderTypes();
        return studentRoomsViewHolderTypes;
    }

    public BindLogic.BindFunction<StudentRoom> getCommonBind() {
        return commonBind;
    }

    public void setCommonBind(BindLogic.BindFunction<StudentRoom> commonBind) {
        this.commonBind = commonBind;
    }
}
