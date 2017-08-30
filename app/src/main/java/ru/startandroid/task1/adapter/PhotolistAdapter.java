package ru.startandroid.task1.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.startandroid.task1.R;
import ru.startandroid.task1.model.Photo;


public class PhotolistAdapter extends RecyclerView.Adapter<PhotolistAdapter.MyViewHolder> {

    private List<Photo> photoList;
    private Context context;
    private int dp;
    public PhotolistAdapter(Context context, List<Photo> list, int dp) {
        this.context = context;
        this.photoList = list;
        this.dp = dp;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photo photoListItem = photoList.get(position);
        Log.d("adap", "" + photoListItem.getUrl());
        Glide.with(context).load(photoListItem.getUrl()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo;
        public MyViewHolder(View view) {
            super(view);
            photo =  view.findViewById(R.id.imageView);
            float density = context.getResources().getDisplayMetrics().density;
            int pixels = Math.round((float) dp * density);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(pixels, pixels);
            photo.setLayoutParams(params);
        }
    }
}
