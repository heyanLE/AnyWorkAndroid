package com.qgstudio.anyworkc.enter.login;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.dialog.LoadingDialog;
import com.qgstudio.anyworkc.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgetPasswordFragment extends DialogFragment {

    /**
     * 邮箱
     */
    private static final String EMAIL_REX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

    private ForgetApi forgetApi;

    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_forget_password, container, false);

        final EditText email = view.findViewById(R.id.email_input);
        Button confirm = view.findViewById(R.id.confirm);
        Button cancle = view.findViewById(R.id.cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("")) {
                    //email.setError("邮箱输入不能为空");
                    Toast.makeText(getActivity(),"邮箱输入不能为空",Toast.LENGTH_LONG).show();
                    email.requestFocus();
                }else if (!Pattern.matches(EMAIL_REX, email.getText().toString())) {
                    Toast.makeText(getActivity(), "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                } else {
                    if (forgetApi == null) {
                        forgetApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(ForgetApi.class);
                    }
                    showLoading();
                    Map<String, String> info = new HashMap<>();
                    info.put("email", email.getText().toString());
                    //防止连续点击
                    confirm.setEnabled(false);
                    forgetApi.forget(info)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseResult>() {
                                @Override
                                public void onCompleted() {
                                    //防止连续点击
                                    confirm.setEnabled(true);
                                    hideLoad();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast(e.getMessage() == null ? "发生未知错误" : e.getMessage());

                                }

                                @Override
                                public void onNext(ResponseResult responseResult) {
                                    ToastUtil.showToast(responseResult.getStateInfo());
                                    dismiss();
                                }
                            });
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    void hideLoad() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
