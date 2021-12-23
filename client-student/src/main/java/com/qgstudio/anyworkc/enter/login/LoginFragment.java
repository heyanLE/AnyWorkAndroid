package com.qgstudio.anyworkc.enter.login;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.common.UserInfo;
import com.qgstudio.anyworkc.data.model.User;
import com.qgstudio.anyworkc.dialog.LoadingDialog;
import com.qgstudio.anyworkc.enter.EnterActivity;
import com.qgstudio.anyworkc.enter.MeedQuestionFragment;
import com.qgstudio.anyworkc.main.HomeActivity;
import com.qgstudio.anyworkc.mvp.MVPBaseFragment;
import com.qgstudio.anyworkc.utils.TextWatcherV2;
import com.qgstudio.anyworkc.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面的 fragment
 * Created by chenyi on 2017/3/28.
 */

public class LoginFragment extends MVPBaseFragment<LoginContract.View, LoginPresenter> implements LoginContract.View {

    public static final String ARGUMENT_LOGIN_ID = "LOGIN_ID";

    @BindView(R.id.account)
    EditText account;

    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_in)
    Button btnLogin;

    LoadingDialog loadingDialog;

    @OnClick(R.id.sign_in)
    public void login() {
        String acc = account.getText().toString();
        String pass = password.getText().toString();
        if (acc.equals("") || pass.equals("")) {
            ToastUtil.showToast("请输入帐号和密码");
        } else {
            // 通过网络进行登录
            mPresenter.login(acc, pass);
        }
    }

    @OnClick(R.id.btn_to_register)
    public void toRegister() {
        ((EnterActivity) getActivity()).intoRegister();
    }

    public static LoginFragment newInstance() {
        //可通过 newInstance 为 Fragment 添加参数，保证 Fragment 重建时参数字段不被销毁
        return new LoginFragment();
    }

    @OnClick(R.id.meed_question)
    public void clickMeedQuestion() {
        new MeedQuestionFragment().show(getActivity().getFragmentManager(), "");
    }
    @OnClick(R.id.forget_password)
    public void clickForgetPassword(){
        new ForgetPasswordFragment().show(getActivity().getFragmentManager(), "");
    }

    @Override
    public int getRootId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        User user = UserInfo.getUser();
        if (user != null) {
            account.setText(user.getStudentId());
            password.setText(user.getPassword());
        }

        // 布局下移
        if (view.getTag() == null) {
            setDetails(view);
            view.setTag(new Object());
        }

        //设置按键的点击状态
        setBtnLoginState();
        //设置editText监听
        account.addTextChangedListener(new TextWatcherV2() {
            @Override
            public void onTextChanged(CharSequence s) {
                setBtnLoginState();
            }
        });
        password.addTextChangedListener(new TextWatcherV2() {
            @Override
            public void onTextChanged(CharSequence s) {
                setBtnLoginState();
            }
        });
    }

    @Override
    public void loadData(View view) {

    }

    @Override
    public void showError(String errorInfo) {
        ToastUtil.showToast(errorInfo);
    }

    @Override
    public void showSuccess() {
        ToastUtil.showToast("登录成功");
        Intent intent = new Intent(mActivity, HomeActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    @Override
    public void showLoad() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void stopLoad() {
        loadingDialog.dismiss();
    }

    /**
     * @return 判断登陆按键是否可以点击
     */
    private boolean isNeedLoginBtnEnable() {
        String acc = account.getText().toString();
        String pass = password.getText().toString();
        return !acc.isEmpty() && !pass.isEmpty();
    }

    private void setBtnLoginState() {
        if (isNeedLoginBtnEnable()) {
            btnLogin.setEnabled(true);
            btnLogin.setTextColor(Color.WHITE);
        } else {
            btnLogin.setEnabled(false);
            btnLogin.setTextColor(getResources().getColor(R.color.text_hint));
        }
    }

    /**
     * 细节优化
     *
     * @param viewGroup
     */
    private void setDetails(View viewGroup) {
        //获得系统状态栏高度
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelOffset(resourceId);
        }
        View v = viewGroup.findViewById(R.id.cl_login);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        params.topMargin += result;
        v.setLayoutParams(params);
    }
}
