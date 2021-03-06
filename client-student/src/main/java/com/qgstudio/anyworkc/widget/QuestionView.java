package com.qgstudio.anyworkc.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.data.model.Question;
import com.qgstudio.anyworkc.data.model.StudentAnswerAnalysis;
import com.qgstudio.anyworkc.exam.adapters.AskingAdapter;
import com.qgstudio.anyworkc.exam.adapters.ChoiceAdapter;
import com.qgstudio.anyworkc.exam.adapters.FillingAdapter;
import com.qgstudio.anyworkc.exam.adapters.OptionAdapter;
import com.qgstudio.anyworkc.utils.LogUtil;
import com.qgstudio.anyworkc.utils.ToastUtil;

import java.util.StringTokenizer;

public class QuestionView extends FrameLayout {
    Question mQuestion;
    StudentAnswerAnalysis mAnalysis;
    TextView tvQuestionInfo;

    TextView tvQuestionContent;
    ImageView btnCollect;

    RecyclerView recyclerViewQuestionSelections;

    //EditText editTextQuestionFillBlank;

    TextView btnAnswerControl;

    TextView tvAnswer;
    TextView tvAnswerInvisible;
    Drawable answerShowIcon;
    Drawable answerHideIcon;
    private boolean isAnswerBottomShowing;
    ValueAnimator showAnimator;
    OptionAdapter optionAdapter;
    private boolean isTestMode = true;
    private boolean isCollected;
    private OnAnswerStateChangedListener onAnswerStateChangedListener;

    public QuestionView(@NonNull Context context) {
        super(context);
        init();
    }

    public QuestionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuestionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_workout, this, false);
        addView(view);
        //find view ??????????????????@BindView??????????????????
        tvQuestionInfo = view.findViewById(R.id.tv_question_info);
        tvQuestionContent = view.findViewById(R.id.tv_question_content);
        recyclerViewQuestionSelections = view.findViewById(R.id.recycler_view_question_selections);
        //editTextQuestionFillBlank = view.findViewById(R.id.editTv_question_fill_blank);
        tvAnswer = view.findViewById(R.id.tv_answer);
        btnAnswerControl = view.findViewById(R.id.btn_answer_control);
        tvAnswerInvisible = view.findViewById(R.id.tv_answer_invisible);
        btnCollect = view.findViewById(R.id.btn_collect);
        //??????
        answerShowIcon = getContext().getResources().getDrawable(R.drawable.icon_on);
        answerHideIcon = getContext().getResources().getDrawable(R.drawable.icon_off);
        //????????????icon
        setDrawableBounds(40);
        setBtnAnswerControlIcon(answerHideIcon);
        //??????????????????
        tvAnswer.setVisibility(View.GONE);
        //??????answerControl??????
        btnAnswerControl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleShowAnswerBottom();
            }
        });


        if (isTestMode) {
            tvAnswer.setVisibility(View.GONE);
            btnAnswerControl.setVisibility(GONE);
        } else {
            btnAnswerControl.setVisibility(VISIBLE);
        }

    }


    public void showAnswerBottom() {
        if (!isAnswerBottomShowing) {
            toggleShowAnswerBottom();
        }
    }

    public void hideAnswerBottom() {
        if (isAnswerBottomShowing) {
            toggleShowAnswerBottom();
        }
    }

    /**
     * ????????????
     *
     * @param question
     */
    public void setQuestion(Question question, int pos, int sum) {
        mQuestion = question;
        if (question.getEnumType() == Question.Type.UNKNOWN) {
            ToastUtil.showToast(Question.Type.UNKNOWN.string);
            return;
        }

        setAnswerBottom();

        setQuestionContent(mQuestion.getContent());//??????????????????
        setPosition(pos, sum);

        //?????????????????????????????????
        if (!isTestMode) {
            pos = -1;
        }

        switch (mQuestion.getEnumType()) {
            case TRUE_OR_FALSE:
            case SELECT:

                optionAdapter = new ChoiceAdapter(getContext(), mQuestion, pos, mAnalysis == null ? null : mAnalysis.getStudentAnswer());
                recyclerViewQuestionSelections.setAdapter(optionAdapter);
                recyclerViewQuestionSelections.setLayoutManager(new LinearLayoutManager(getContext()));

                break;
            case FILL_BLANK:
                optionAdapter = new FillingAdapter(getContext(), mQuestion, pos, mAnalysis == null ? null : mAnalysis.getStudentAnswer());
                recyclerViewQuestionSelections.setAdapter(optionAdapter);
                recyclerViewQuestionSelections.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case SHORT_ANSWER:

                optionAdapter = new AskingAdapter(getContext(), mQuestion, pos, mAnalysis == null ? null : mAnalysis.getStudentAnswer());
                recyclerViewQuestionSelections.setAdapter(optionAdapter);
                recyclerViewQuestionSelections.setLayoutManager(new LinearLayoutManager(getContext()));


                break;
            case UNKNOWN:

                ToastUtil.showToast(Question.Type.UNKNOWN.string);

                break;
        }

    }

    /**
     * ?????????????????????
     *
     * @param analysis
     */
    public void setAnalysis(StudentAnswerAnalysis analysis, boolean isOpenBottomAnswer) {
        if (!isTestMode) {
            if (isOpenBottomAnswer) {
                isAnswerBottomShowing = true;
                tvAnswer.setVisibility(VISIBLE);
                btnAnswerControl.setText("????????????");
                setBtnAnswerControlIcon(answerShowIcon);
            }

            mAnalysis = analysis;
            setQuestion(mAnalysis.getQuestion(), 0, 1);
        }
    }

    public void setAnalysis(StudentAnswerAnalysis analysis, boolean isOpenBottomAnswer, int pos, int sum) {
        if (!isTestMode) {
            mAnalysis = analysis;
            setQuestion(mAnalysis.getQuestion(), pos, sum);
            if (isOpenBottomAnswer) {
                isAnswerBottomShowing = true;
                tvAnswer.setVisibility(VISIBLE);
                btnAnswerControl.setText("????????????");
                setBtnAnswerControlIcon(answerShowIcon);
                ViewGroup.LayoutParams layoutParams = tvAnswer.getLayoutParams();
                layoutParams.height = tvAnswerInvisible.getHeight();
                tvAnswer.setLayoutParams(layoutParams);
            } else {
                isAnswerBottomShowing = false;
                tvAnswer.setVisibility(GONE);
                btnAnswerControl.setText("????????????");
                setBtnAnswerControlIcon(answerHideIcon);
            }

        }
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param pos ????????????
     * @param sum ????????????
     */
    public void setPosition(int pos, int sum) {
        Question.Type type = null;
        if (mQuestion != null) {
            type = mQuestion.getEnumType();
        }
        setQuestionInfo(pos + 1 + "/" + sum + "  (" + (type != null ? type.string : "") + ")");
    }

    public void setQuestionInfo(String info) {
        tvQuestionInfo.setText(info);
    }

    public void setQuestionContent(String content) {
        tvQuestionContent.setText(content);
    }

    public void setBtnCollectListener(OnClickListener listener) {
        btnCollect.setOnClickListener(listener);
    }

    private void toggleShowAnswerBottom() {
        //??????
        isAnswerBottomShowing = !isAnswerBottomShowing;
        if (onAnswerStateChangedListener != null) {
            onAnswerStateChangedListener.onAnswerStateChanged(isAnswerBottomShowing);
        }
        if (showAnimator == null) {
            showAnimator = ValueAnimator.ofInt(0, tvAnswerInvisible.getHeight());
            showAnimator.setDuration(200);
            showAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewGroup.LayoutParams layoutParams = tvAnswer.getLayoutParams();
                    layoutParams.height = (int) animation.getAnimatedValue();
                    tvAnswer.setLayoutParams(layoutParams);
                }
            });
            showAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    //show?????????????????????????????????
                    if (isAnswerBottomShowing) {
                        tvAnswer.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //hide????????????????????????gone
                    if (!isAnswerBottomShowing) {
                        tvAnswer.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        if (isAnswerBottomShowing) {
            showAnimator.start();
            btnAnswerControl.setText("????????????");
            setBtnAnswerControlIcon(answerShowIcon);
        } else {
            btnAnswerControl.setText("????????????");
            setBtnAnswerControlIcon(answerHideIcon);
            showAnimator.reverse();
        }
    }

    private void setAnswerBottom() {
        String key = mQuestion.getKey();
        if (key != null && mQuestion.getEnumType() == Question.Type.FILL_BLANK) {
            //?????????????????????
            StringTokenizer tokenizer = new StringTokenizer(key, "???");
            String answer = "";
            for (int i = 1; tokenizer.hasMoreTokens(); i++) {
                answer = answer + i + "." + tokenizer.nextToken() + "\n";
            }
            key = answer;
        } else if (key != null && mQuestion.getEnumType() == Question.Type.TRUE_OR_FALSE) {
            if (key.equals("1")) {
                key = "??????\n";
            } else {
                key = "??????\n";
            }
        } else {
            key = key + "\n";
        }
        LogUtil.d("???????????????????????????", key);
//        String s = "<font color='#F13E58'>???????????????"
//                + key
//                + "</font><br><br>"
//                + (mQuestion.getAnalysis() == null ? "????????????" : mQuestion.getAnalysis());
        String s = "????????????:\n"
                + key
                + "\n?????????\n"
                + (mQuestion.getAnalysis() == null ? "????????????" : mQuestion.getAnalysis());
        //tvAnswer.setText(Html.fromHtml(s));
        //tvAnswerInvisible.setText(Html.fromHtml(s));
        SpannableString string = new SpannableString(s);
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#3f51b5")), 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#009688")), "????????????:\n".length() + key.length() + "\n".length() - 1, "????????????:\n".length() + key.length() + "\n".length() - 1 + 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvAnswer.setText(string);
        tvAnswerInvisible.setText(string);
    }

    private void setDrawableBounds(int param) {
        answerShowIcon.setBounds(0, 0, param, param);
        answerHideIcon.setBounds(0, 0, param, param);
    }

    private void setBtnAnswerControlIcon(Drawable drawable) {
        Drawable[] drawables = btnAnswerControl.getCompoundDrawables();
        btnAnswerControl.setCompoundDrawables(drawables[0], drawables[1], drawable, drawables[3]);
    }

    public void setTestMode(boolean enable) {
        isTestMode = enable;
        if (isTestMode) {
            tvAnswer.setVisibility(View.GONE);
            btnAnswerControl.setVisibility(GONE);
        } else {
            tvAnswer.setVisibility(View.GONE);
            btnAnswerControl.setVisibility(VISIBLE);
            btnAnswerControl.setText("????????????");
            setBtnAnswerControlIcon(answerHideIcon);
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean is) {
        isCollected = is;
        if (isCollected) {
            btnCollect.setImageResource(R.drawable.icon_collect);
        } else {
            btnCollect.setImageResource(R.drawable.icon_uncollect);
        }
    }

    public void setCollectionEnable(boolean enable) {
        btnCollect.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setOnAnswerStateChangedListener(OnAnswerStateChangedListener listener) {
        onAnswerStateChangedListener = listener;
    }

    public interface OnAnswerStateChangedListener {
        void onAnswerStateChanged(boolean isShowing);
    }
}
