package com.qgstudio.anyworkc.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.common.DialogManagerActivity;
import com.qgstudio.anyworkc.core.Apis;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.RetrofitSubscriber;
import com.qgstudio.anyworkc.data.model.Organization;
import com.qgstudio.anyworkc.dialog.NewBaseDialog;
import com.qgstudio.anyworkc.main.data.OrganizationApi;
import com.qgstudio.anyworkc.main.data.OrganizationRepository;
import com.qgstudio.anyworkc.utils.LogUtil;
import com.qgstudio.anyworkc.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewOrganizationActivity extends DialogManagerActivity {

    private static final String TAG = "NewOrganizationActivity";
    NewBaseDialog mBaseDialog;
    public static Organization myOrganization;
    NewOrganizationFragment fragment;

    @BindView(R.id.edi_searching)
    EditText searching;
    @BindView(R.id.my_organization)
    TextView tvMyOrganization;

    OrganizationApi mOrganizationApi;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_organization);
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        mOrganizationApi = retrofit.create(OrganizationApi.class);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }
        mOrganizationApi.getJoinOrganization(Apis.getJoinOrganizationApi())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Organization>>() {
                    @Override
                    protected void onSuccess(List<Organization> data) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onSuccess -> " + data);
                        String s = "???????????????";
                        if (data.isEmpty()){
                            s = s + "???";
                        }else {
                            myOrganization = data.get(0);
                            s = s + data.get(0).getOrganizationName();
                            tvMyOrganization.setClickable(true);
                        }
                        tvMyOrganization.setText(s);
                    }

                    @Override
                    protected void onFailure(String info) {
                        tvMyOrganization.setText("???????????????");
                        ToastUtil.showToast("??????????????????");
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        tvMyOrganization.setText("???????????????");
                        ToastUtil.showToast("??????????????????");
                    }
                });

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.frame, NewOrganizationFragment.newInstance())
                .commit();
        fragment = (NewOrganizationFragment) mFragmentManager.findFragmentById(R.id.frame);

        searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                NewOrganizationFragment ofm = (NewOrganizationFragment) mFragmentManager.getFragments().get(0);
                ofm.filter(s.toString());
            }
        });

        tvMyOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogCreateHelper helper = new DialogCreateHelper(myOrganization);
                mBaseDialog = helper.create(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (mJoinOrganizationListener != null) {
//                                int id = organization.getOrganizationId();
//                                String password = helper.edi.getText().toString();
//                                mJoinOrganizationListener.join(id, password, position);
//                        }
//                        if (mLeaveOrganizationListener != null) {
//                            int id = organization.getOrganizationId();
//                            mLeaveOrganizationListener.leave(id, position);
//                        }
                        OrganizationRepository repository = new OrganizationRepository();
                        repository.leaveOrganization(myOrganization.getOrganizationId(), NewOrganizationActivity.this);
                    }
                });
                if (!mBaseDialog.isShowing()) {
                    mBaseDialog.show();
                }
            }
        });
    }

    @OnClick(R.id.img_back)
    public void back() {
        this.finish();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchingActivity.class);
        context.startActivity(intent);
    }

    class DialogCreateHelper {
        View root;
        //        CircleImageView civ;
        TextView tvOgName;
        //        EditText edi;
        TextView tvTeacher;
        ImageView imgCancel;
        Button btLeave;

        private Organization organization;
//        private String type;
//
//        public static final String TYPE_JOIN = "??????";
//        public static final String TYPE_LEAVE = "??????";

        public DialogCreateHelper(Organization o) {
            organization = o;
//            type = t;

            //view?????????
            root = LayoutInflater.from(NewOrganizationActivity.this).inflate(R.layout.new_dialog_organization, null);
//            civ = (CircleImageView) root.findViewById(R.id.cimg);
//            tv = (TextView) root.findViewById(R.id.text);
//            edi = (EditText) root.findViewById(R.id.edi);
//            imgCancel = (ImageView) root.findViewById(R.id.image_cancel);
//            btLeave = (Button) root.findViewById(R.id.button);

//            //????????????
//            String url = API_DEFAULT_URL + "picture/organization/" + organization.getOrganizationId() + ".jpg";
//            Glide.with(mContext).load(url).into(civ);

            //????????????
            String organizationName = organization.getOrganizationName();
            String teacher = "???????????????" + organization.getTeacherName();


//            String description = "?????????" + organization.getDescription();
//            tv.setText(teacher + "\n" + description);
//
//            //??????????????????????????????
//            int visibility = t.equals(TYPE_JOIN) ? View.VISIBLE : View.GONE;
//            edi.setVisibility(visibility);

        }

        public NewBaseDialog create(View.OnClickListener listener) {
            return new NewBaseDialog.Builder(NewOrganizationActivity.this, -2)
                    .view(root)
                    .setPositiveListener("????????????", listener)
                    .cancelTouchout(true)
                    .build();
        }
    }

    public void leaveSucceed(){
        tvMyOrganization.setText("??????????????????");
        myOrganization = null;
        tvMyOrganization.setClickable(false);
        mBaseDialog.cancel();
        fragment.mPresenter.getAllOrganization();
    }
}
