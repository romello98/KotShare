package com.example.kotshare.view.recycler_views;

import android.widget.TextView;

import com.example.kotshare.R;
import com.example.kotshare.model.StudentRoom;

import java.util.HashMap;

public class StudentRoomsViewHolderTypes
{
    private static StudentRoomsViewHolderTypes studentRoomsViewHolderTypes;
    private HashMap<ViewHolderType, BindLogic<StudentRoom>> viewHolderTypesLogic;

    private StudentRoomsViewHolderTypes()
    {
        this.viewHolderTypesLogic = new HashMap<>();
        this.viewHolderTypesLogic.put(ViewHolderType.STUDENT_ROOM_SELF,
                new BindLogic<>(R.layout.student_room_recycler_view_item_self, (item, viewHolder) -> {
                    TextView description = viewHolder.itemView.findViewById(R.id.textView_myStudentRoomDescription);
                    TextView title = viewHolder.itemView.findViewById(R.id.textView_myStudentRoomTitle);
                    description.setText(item.getDescription());
                    title.setText(item.getTitle());
                }));

        this.viewHolderTypesLogic.put(ViewHolderType.STUDENT_ROOM_ELSE,
                new BindLogic<>(R.layout.student_room_recycler_view_item, (item, viewHolder) -> {
                    TextView description = viewHolder.itemView.findViewById(R.id.textView_studentRoomDescription);
                    TextView title = viewHolder.itemView.findViewById(R.id.textView_studentRoomTitle);
                    description.setText(item.getDescription());
                    title.setText(item.getTitle());
                }));
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
}
