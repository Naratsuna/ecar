package com.example.CustomWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.ecar.R;
import androidx.annotation.Nullable;

public class TakePhotoWidget extends LinearLayout implements View.OnClickListener{

    private static final String TAG="TakePhotoWidget";

    public TakePhotoWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.fragment_takephoto,this);
    }

    @Override
    public void onClick(View v) {

    }
}
