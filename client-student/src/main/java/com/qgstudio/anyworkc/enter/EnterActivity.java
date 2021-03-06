package com.qgstudio.anyworkc.enter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.common.DialogManagerActivity;
import com.qgstudio.anyworkc.enter.login.LoginFragment;
import com.qgstudio.anyworkc.enter.register.RegisterFragment;
import com.qgstudio.anyworkc.utils.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录注册的 Activity ，切换登录注册的 Fragment ，并为其配置需要的 Presenter
 * Created by chenyi on 2017/3/28.
 */

public class EnterActivity extends DialogManagerActivity {

    @BindView(R.id.container)
    FrameLayout container;


    @OnClick(R.id.register)
    public void intoRegister() {
        // 根据 tag 从 FragmentManager 中将 fragment 取出
        RegisterFragment registerFragment =
                (RegisterFragment) getSupportFragmentManager().findFragmentByTag(RegisterFragment.ARGUMENT_REGISTER_ID);
        if (registerFragment == null) {
            // 如果当前没有该 fragment ，创建该 fragment 并通过 FragmentManager 进行管理
            registerFragment = RegisterFragment.newInstance();

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), registerFragment,
                    R.id.container, RegisterFragment.ARGUMENT_REGISTER_ID);
        }
    }

    @OnClick(R.id.login)
    public void intoLogin() {
        // 跳转到登录界面
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.ARGUMENT_LOGIN_ID);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, loginFragment, LoginFragment.ARGUMENT_LOGIN_ID)
                    .commit();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_enter);
        ButterKnife.bind(this);
        intoLogin();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void start(Context context, int flag) {
        Intent intent = new Intent(context, EnterActivity.class);
        intent.putExtra("FLAG", flag);
        context.startActivity(intent);
    }

}
