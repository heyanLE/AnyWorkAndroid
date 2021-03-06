package com.qgstudio.anyworkc.workout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.model.StudentAnswerAnalysis;
import com.qgstudio.anyworkc.data.model.StudentAnswerResult;
import com.qgstudio.anyworkc.data.model.StudentTestResult;
import com.qgstudio.anyworkc.dialog.LoadingDialog;
import com.qgstudio.anyworkc.exam.ExamActivity;
import com.qgstudio.anyworkc.grade.GradeActivity;
import com.qgstudio.anyworkc.ranking.RankingFragment;
import com.qgstudio.anyworkc.utils.GsonUtil;
import com.qgstudio.anyworkc.utils.ToastUtil;
import com.qgstudio.anyworkc.workout.data.Chapter;
import com.qgstudio.anyworkc.workout.data.Testpaper;
import com.qgstudio.anyworkc.workout.data.WorkoutInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<WorkoutInfo> datas = new ArrayList<>();
    public Context context;
    private OnChapterClickListener listener;
    private boolean isTest;

    public ChapterAdapter(List<? extends WorkoutInfo> datas, Context context) {
        this.datas.clear();
        this.datas.addAll(datas);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == WorkoutInfo.TYPE_CHAPTER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_workout_chapter, parent, false);
            viewHolder = new ChapterViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_workout_info, parent, false);
            viewHolder = new CatalogViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == WorkoutInfo.TYPE_CHAPTER) {
            //??????
            ChapterViewHolder chapterViewHolder = (ChapterViewHolder) holder;
            final Chapter chapter = (Chapter) datas.get(position);
            chapterViewHolder.tvChapterTittle.setText(chapter.getChapterName());
            chapterViewHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onChapterClick(chapter.getChapterId());
                    }
                }
            });
        } else {
            //??????
            CatalogViewHolder catalogViewHolder = (CatalogViewHolder) holder;
            final Testpaper testpaper = (Testpaper) datas.get(position);
            catalogViewHolder.tvCatalogTittle.setText(testpaper.getTestpaperTitle());
            catalogViewHolder.tvCatalogTime.setText(testpaper.getCreateTime() + "-" + testpaper.getEndingTime());

            switch (testpaper.getStatus()) {
                case Testpaper.STATUS_UNDO:
                    catalogViewHolder.stateTab.setText("?????????");
                    catalogViewHolder.stateTab.setBackgroundColor(Color.parseColor("#F54864"));
                    break;
                case Testpaper.STATUS_UNFINISHED:
                    catalogViewHolder.stateTab.setText("?????????");
                    catalogViewHolder.stateTab.setBackgroundColor(Color.parseColor("#4898F5"));
                    break;
                case Testpaper.STATUS_DONE:
                    catalogViewHolder.stateTab.setText("?????????");
                    catalogViewHolder.stateTab.setBackgroundColor(Color.parseColor("#33C571"));
                    break;
            }
            ((CatalogViewHolder) holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String createTime = testpaper.getCreateTime();
                    String endTime = testpaper.getEndingTime();
                    Date createD = getDate(createTime);
                    Date endD = getDate(endTime);

                    long now = System.currentTimeMillis();

                    //??????????????????????????????????????????
                    /*if (testpaper.getStatus() != Testpaper.STATUS_DONE) {
                        if (now < createD.getTime()) {
                            ToastUtil.showToast("?????????????????????");
                            return;
                        }
                        if (now > endD.getTime()) {
                            ToastUtil.showToast("?????????????????????");
                            return;
                        }
                    }*/

                    LoadingDialog dialog = new LoadingDialog(context);
                    dialog.show();
                    intoTestActivity(v.getContext(), testpaper.getTestpaperId(), testpaper.getTestpaperTitle(), 1, dialog, testpaper.getStatus());
                }
            });
            ((CatalogViewHolder) holder).btnRank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RankingFragment fragment = RankingFragment.newInstance(testpaper.getTestpaperId());
                    final AppCompatActivity activity = (AppCompatActivity) context;
                    FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.workout_activity_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    fragment.setOnBackListener(new RankingFragment.OnBackListener() {
                        @Override
                        public void onClick() {
                            activity.getSupportFragmentManager().popBackStack();
                        }
                    });

                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getClass() == Chapter.class ? WorkoutInfo.TYPE_CHAPTER : WorkoutInfo.TYPE_CATALOG;
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chapter_tittle)
        TextView tvChapterTittle;
        @BindView(R.id.card_view)
        View card;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CatalogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.catalog_tittle)
        TextView tvCatalogTittle;
        @BindView(R.id.catalog_time)
        TextView tvCatalogTime;
        @BindView(R.id.tv_workout_state_tab)
        TextView stateTab;
        @BindView(R.id.card)
        View card;
        @BindView(R.id.btn_rank)
        View btnRank;

        public CatalogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnChapterClickListener(OnChapterClickListener listener) {
        this.listener = listener;
    }

    interface OnChapterClickListener {
        void onChapterClick(int chapterID);
    }

    private void intoTestActivity(final Context context, final int testpaperId, final String paperTittle, final int type, final LoadingDialog dialog, final int state) {


        Map<String, Integer> info = new HashMap<>();
        info.put("testpaperId", testpaperId);
        Log.e(TAG, "checkTheTestFinish: " + GsonUtil.GsonString(info));
        //??????????????????????????????????????????????????????
        if (state != Testpaper.STATUS_DONE) {
            dialog.dismiss();
            ExamActivity.start(context, testpaperId, type, paperTittle, state);
        } else {
            RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(CheckApi.class)
                    .checkTheTestFinish(info)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseResult<StudentTestResult>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            e.printStackTrace();
                            Log.d("ChapterAdapter", e.getMessage());
                            ToastUtil.showToast("??????????????????");
                        }

                        @Override
                        public void onNext(ResponseResult<StudentTestResult> result) {
                            dialog.dismiss();
                            double socre = result.getData().getSocre();
                            List<StudentAnswerResult> results = new ArrayList<>();

                            List<StudentAnswerAnalysis> analysis = result.getData().getStudentAnswerAnalysis();
                            for (StudentAnswerAnalysis analysi : analysis) {
                                results.add(new StudentAnswerResult(analysi));
                            }
                            GradeActivity.start(context, socre, GsonUtil.GsonString(results), paperTittle);

                        }
                    });
        }

    }

    /**
     * ??????????????????????????????????????????Date
     *
     * @param str ?????????????????????????????????
     * @return Date
     */
    private Date getDate(String str) {
        System.out.println("=============" + str);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return date;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public interface CheckApi {

        @POST("test/done/detail")
        @Headers("Content-Type:application/json")
        Observable<ResponseResult<StudentTestResult>> checkTheTestFinish(@Body Map<String, Integer> testpaperId);
    }
}
