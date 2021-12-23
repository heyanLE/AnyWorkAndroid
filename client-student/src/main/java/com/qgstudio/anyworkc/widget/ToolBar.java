package com.qgstudio.anyworkc.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgstudio.anyworkc.R;

public class ToolBar extends FrameLayout {

    private ImageView backIv;

    private TextView titleTv;

    private OnBackListener listener;

    public ToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = inflate(context, R.layout.widget_toolbar, this);

        backIv = v.findViewById(R.id.iv_back);
        titleTv = v.findViewById(R.id.tv_toolbar_title);

        backIv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBack();
                }
            }
        });

        titleTv.setText("做题记录");

    }

    public OnBackListener getListener() {
        return listener;
    }

    public void setListener(OnBackListener listener) {
        this.listener = listener;
    }

    public interface OnBackListener {
        void onBack();
    }
}
