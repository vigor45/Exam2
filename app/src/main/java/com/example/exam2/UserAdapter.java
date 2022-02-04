package com.example.exam2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends ArrayAdapter<User>{
    private static final String TAG = "UserAdapter";
    private Context mContext;
    private int mResource;
    public ArrayList<User> languageModelList;
    int int_width, int_height;

    static class ViewHolder {
        TextView holdid;
        TextView holdauthor;
        TextView holdwidth;
        TextView holdheight;
        ImageView holdurl;
        TextView holddownloadurl;
        Button holdview;
    }

    public UserAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
        this.languageModelList = objects;
    }

    @Override
    public int getCount() {
        return languageModelList.size();
    }

    @Override
    public User getItem(int position) {
        return languageModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //setupImageLoader();

        String str_id = getItem(position).getId();
        String str_author = getItem(position).getAuthor();
        String str_width = getItem(position).getWidth();
        String str_height = getItem(position).getHeight();
        String str_url = getItem(position).getUrl();
        String str_download_url = getItem(position).getDownload_url();

        ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, null, false);

        holder = new ViewHolder();
        holder.holdauthor =  convertView.findViewById(R.id.tv_author);
        holder.holdurl = convertView.findViewById(R.id.iv_image);
        holder.holdview = convertView.findViewById(R.id.btn_preview);

        convertView.setTag(holder);

        holder = new ViewHolder();
        holder.holdauthor =  convertView.findViewById(R.id.tv_author);
        holder.holdurl = convertView.findViewById(R.id.iv_image);
        holder.holdview = convertView.findViewById(R.id.btn_preview);

        holder.holdauthor.setText(str_author);
        holder.holdview.setTag(str_author + "|" + str_download_url + "|" + str_width + "|" + str_height);

        Picasso.get()
                .load(str_download_url)
                .placeholder(R.drawable.ic_broken_image_gray_24dp)
                .error(R.drawable.ic_broken_image_gray_24dp)
                .resize(100, 100)
                .centerCrop()
                .into(holder.holdurl);

        return convertView;
    }


    public void setData(ArrayList<User> modelList) {
        this.languageModelList = modelList;
        notifyDataSetChanged();
    }

}
