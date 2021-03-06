package com.qgstudio.anyworkc.feedback;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.dialog.LoadingDialog;
import com.qgstudio.anyworkc.feedback.data.FeedBack;
import com.qgstudio.anyworkc.feedback.utils.ShapeView;
import com.qgstudio.anyworkc.mvp.MVPBaseActivity;
import com.qgstudio.anyworkc.utils.GetPath;
import com.qgstudio.anyworkc.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;

import rx.Subscription;

public class FeedbackActivity extends MVPBaseActivity<FeedbackContract.View, FeedbackPresenter> implements FeedbackContract.View {

    private int GetPicture = 1;

    private ImageView noHint;
    private ImageView question;
    private ImageView suggestion;
    private View layoutQuestion;
    private View layoutSuggestion;
    private EditText questionDetail;
    private ImageView addPicture;
    private Spinner module;
    private EditText contact;
    private View commit;
    private View back;
    private TextView questionModule;
    private ShapeView shapeView;

    private ArrayList<String> modules = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private String questionORsuggestion = "???";
    private String moduleDetail = "???";

    private String imagePath;

    private Subscription subscription;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }

        initView();
        setData();
        setOnClickListener();
    }

    private void initView() {
        noHint = (ImageView) findViewById(R.id.feedback_no_hint);
        question = (ImageView) findViewById(R.id.feedback_question);
        suggestion = (ImageView) findViewById(R.id.feedback_suggestion);
        questionDetail = (EditText) findViewById(R.id.feedback_question_detail);
        addPicture = (ImageView) findViewById(R.id.feedback_picture);
        module = (Spinner) findViewById(R.id.feedback_module);
        contact = (EditText) findViewById(R.id.feedback_contact);
        commit = findViewById(R.id.feedback_commit);
        back = findViewById(R.id.feedback_back);
        shapeView = findViewById(R.id.feedback_shape_view);

        layoutQuestion = findViewById(R.id.linear_layout_question);
        layoutSuggestion = findViewById(R.id.linear_layout_suggestion);

        questionModule = (TextView) findViewById(R.id.module_question);
        String s = "????????????(<font color='#3C7EFF'>??????</font>)";
        questionModule.setText(Html.fromHtml(s));
    }

    private void setData() {
        modules.add("??????");
        modules.add("??????");
        modules.add("??????");
        modules.add("??????");
        modules.add("??????");
        modules.add("??????");

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.layout_spinner, modules);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        module.setAdapter(arrayAdapter);
    }

    private void setOnClickListener() {
        noHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                findViewById(R.id.feedback_hint).setVisibility(View.GONE);
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.feedback_hint);
                final int height = linearLayout.getHeight();

                final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
                final int topMargin = layoutParams.topMargin;

                ValueAnimator valueAnimator = ValueAnimator.ofInt(height, 0);
                valueAnimator.setDuration(200);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        layoutParams.height = value;
                        layoutParams.topMargin = topMargin * value / height;
                        linearLayout.setLayoutParams(layoutParams);

                    }
                });
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        linearLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.start();
            }
        });
        layoutQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setImageResource(R.drawable.background_feedback_select);
                suggestion.setImageResource(R.drawable.background_feedback_noselect);
                questionORsuggestion = "???????????????";
            }
        });
        layoutSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestion.setImageResource(R.drawable.background_feedback_select);
                question.setImageResource(R.drawable.background_feedback_noselect);
                questionORsuggestion = "???????????????";
            }
        });
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(FeedbackActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FeedbackActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, GetPicture);
                }
            }
        });
        module.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moduleDetail = modules.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                moduleDetail = "???";
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????????????????
                if (imagePath != null || !questionDetail.getText().toString().isEmpty()) {
                    commit.setEnabled(false);
                    shapeView.setClickable(false);
                    //????????????????????????????????????
                    if (imagePath != null) {
                        shapeView.setVisibility(View.VISIBLE);
                    }
                    FeedBack feedBack = new FeedBack();
                    feedBack.setType(questionORsuggestion);
                    feedBack.setContent(questionDetail.getText().toString());
                    feedBack.setModule(moduleDetail);
                    feedBack.setContantWay(contact.getText().toString());
                    subscription = mPresenter.uploadFeedback(feedBack, imagePath);
                } else {
                    ToastUtil.showToast("???????????????????????????");
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showError(String errorInfo) {
        commit.setEnabled(true);
        addPicture.setClickable(true);
        ToastUtil.showToast(errorInfo);
    }

    @Override
    public void showSuccess() {
        ToastUtil.showToast("??????????????????");
        finish();
    }

    @Override
    public void showLoad() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.show();
    }

    @Override
    public void stopLoad() {
        loadingDialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, GetPicture);
                } else {
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GetPicture && resultCode == RESULT_OK) {
            imagePath = new GetPath().getPath(FeedbackActivity.this, data.getData());
            if (imagePath != null) {
                Log.d("linzongzhan", imagePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                if (bitmap == null) {
                    Log.d("linzongzhan", "????????????");
                }
                Glide.with(FeedbackActivity.this).load(new File(imagePath)).into(addPicture);
                shapeView.setVoid();
//                addPicture.setImageBitmap(bitmap);
            }
        }
    }

    //??????????????????
    @Override
    public void updateUploadProgress(long length, long hasWrited) {
        shapeView.setShapeView((int) length, (int) hasWrited);
    }

    @Override
    public void onBackPressed() {
        if (subscription == null) {
            super.onBackPressed();
        } else {
            if (subscription.isUnsubscribed()) {
                super.onBackPressed();
            } else {
                ToastUtil.showToast("???????????????...");
            }
        }
    }
}
