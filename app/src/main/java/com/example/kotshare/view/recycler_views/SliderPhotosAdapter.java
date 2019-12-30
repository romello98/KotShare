package com.example.kotshare.view.recycler_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kotshare.R;
import com.example.kotshare.model.Photo;
import com.example.kotshare.view.PhotosManager;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.HashSet;

public class SliderPhotosAdapter extends SliderViewAdapter<SliderPhotosAdapter.SliderAdapterVH> {

    private Context context;
    private ArrayList<Photo> photos;

    public SliderPhotosAdapter(Context context, ArrayList<Photo> photos) {
        this.context = context;
        if(photos == null || photos.isEmpty())
            photos.add(new Photo(-1, null, null, null, null));
        this.photos = photos;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_slider_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        Photo photo = photos.get(position);

        if(photo.getId() == -1)
            Glide.with(context).load(R.mipmap.student_room_default)
                    .centerCrop().into(viewHolder.imageViewBackground);
        else {
            String url = PhotosManager.getInstance(context).getBaseUrl(photo.getStudentRoomId(), photo);
            Glide.with(viewHolder.imageViewBackground).load(url)
                    .centerCrop().into(viewHolder.imageViewBackground);
        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return photos.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image);
        }
    }
}