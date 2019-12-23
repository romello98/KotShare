package com.example.kotshare.view.recycler_views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.kotshare.R;
import com.example.kotshare.controller.LikeController;
import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;
import com.example.kotshare.view.SharedPreferencesAccessor;
import com.example.kotshare.view.activities.StudentRoomActivity;

import java.util.HashMap;
import java.util.Locale;

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
                TextView price = viewHolder.itemView.findViewById(R.id.textView_studentRoomPrice);
                TextView title = viewHolder.itemView.findViewById(R.id.textView_studentRoomTitle);
                TextView city = viewHolder.itemView.findViewById(R.id.textView_studentRoomPlace);
                price.setText(String.format(context.getString(R.string.price_format),
                        String.format(Locale.FRENCH, "%.2f", studentRoom.getMonthlyPrice())));
                title.setText(studentRoom.getTitle());
                city.setText(studentRoom.getCity().toString());
                cardView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, StudentRoomActivity.class);
                    if(studentRoom.getId() != null)
                        intent.putExtra(context.getString(R.string.STUDENT_ROOM_ID), studentRoom.getId());
                    context.startActivity(intent);
                });
        };

        this.viewHolderTypesLogic.put(ViewHolderType.STUDENT_ROOM_SELF,
                new BindLogic<>(R.layout.student_room_recycler_view_item, (studentRoom, viewHolder) -> {
                    commonBind.bind(studentRoom, viewHolder);
                    ImageView editButton = viewHolder.itemView.findViewById(R.id.imageView_action);
                    editButton.setImageDrawable(viewHolder.itemView.getContext()
                            .getDrawable(R.drawable.ic_edit_24dp));
                }));

        this.viewHolderTypesLogic.put(ViewHolderType.STUDENT_ROOM_ELSE,
                new BindLogic<>(R.layout.student_room_recycler_view_item, (studentRoom, viewHolder) -> {
                    commonBind.bind(studentRoom, viewHolder);
                    Context context = viewHolder.itemView.getContext();
                    ImageView editButton = viewHolder.itemView.findViewById(R.id.imageView_action);
                    Drawable favoriteFilled = context.getDrawable(R.drawable.ic_favorite_filled_24dp);
                    Drawable favoriteEmpty = context.getDrawable(R.drawable.ic_favorite_empty_24dp);
                    boolean isLikedByUser = studentRoom.isLiked();
                    if(isLikedByUser)
                        editButton.setImageDrawable(favoriteFilled);
                    else
                        editButton.setImageDrawable(favoriteEmpty);

                    editButton.setOnClickListener(e ->
                    {
                        if(likeController.isLikedBy(studentRoom.getId(), user.getId()))
                        {
                            boolean hasUnliked = likeController.unlike(user.getId(), studentRoom.getId());
                            if(hasUnliked) editButton.setImageDrawable(favoriteEmpty);
                        }
                        else
                        {
                            Like like = likeController.sendLike(user.getId(), studentRoom.getId());
                            if(like != null) editButton.setImageDrawable(favoriteFilled);
                        }
                    });}));
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
