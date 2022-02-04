package com.example.exam2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ImagePreview extends AppCompatActivity {
    ImageView iv_imagepreview;
    String str_author, str_downloadurl, str_width, str_height;
    int int_width, int_height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_preview);

        iv_imagepreview = findViewById(R.id.iv_imagepreview);

        Intent i = getIntent();
        str_author = getIntent().getExtras().getString("author");
        str_downloadurl = getIntent().getExtras().getString("download_url");
        str_width = getIntent().getExtras().getString("width");
        str_height = getIntent().getExtras().getString("height");

        int_width = Integer.parseInt(str_width);
        int_height = Integer.parseInt(str_height);

        this.setTitle(str_author);

        Picasso.get()
                .load(str_downloadurl)
                //.load(R.drawable.ic_broken_image_gray_24dp)
                .placeholder(R.drawable.ic_broken_image_gray_24dp)
                .error(R.drawable.ic_broken_image_gray_24dp)
                .resize(int_width, int_height)
                .centerCrop()
                .into(iv_imagepreview);
    }
}
