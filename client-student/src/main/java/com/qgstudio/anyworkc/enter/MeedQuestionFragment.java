package com.qgstudio.anyworkc.enter;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.feedback.FeedbackActivity;
import com.qgstudio.anyworkc.utils.DesityUtil;

public class MeedQuestionFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_help, container, false);
        Button button = view.findViewById(R.id.btn_to_feedback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = DesityUtil.dp2px(getActivity(),300);
        params.height = DesityUtil.dp2px(getActivity(),300);
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }
}
