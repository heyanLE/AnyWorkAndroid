package com.qgstudio.anyworkc.paper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qgstudio.anyworkc.App;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.model.StudentAnswerAnalysis;
import com.qgstudio.anyworkc.data.model.StudentAnswerResult;
import com.qgstudio.anyworkc.data.model.StudentTestResult;
import com.qgstudio.anyworkc.workout.data.Testpaper;

import com.qgstudio.anyworkc.dialog.LoadingDialog;
import com.qgstudio.anyworkc.utils.DateUtil;
import com.qgstudio.anyworkc.utils.GsonUtil;
import com.qgstudio.anyworkc.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * @author Yason 2017/7/10.
 */

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.Holder> {

    private Context mContext;
    private List<Testpaper> mPapers;

    public PaperAdapter(Context context, List<Testpaper> papers) {
        mContext = context;
        mPapers = papers;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paper, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final Testpaper paper = mPapers.get(position);
        final int type = paper.getTestpaperType();

        //??????????????????????????????
        holder.tv_title.setText(paper.getTestpaperTitle());

        String chapter = paper.getChapterName();
        holder.tv_chapter.setText(getTextString(chapter));

        holder.tv_type.setText(type == 1 ? "??????" : "??????");

        //????????????
        if (type == 1) {
            String createTime = paper.getCreateTime();
            String endTime = paper.getEndingTime();
            Date createD = getDate(createTime);
            Date endD = getDate(endTime);

            String pattern = "yyyy/MM/dd";
            String create = DateUtil.longToString(createD.getTime(), pattern);
            String ending = DateUtil.longToString(endD.getTime(), pattern);
            holder.tv_date.setText(create + " ~ " + ending);
        } else {
            holder.tv_date.setText("??????");
        }

//        //????????????
//        String url = API_DEFAULT_URL + "picture/organization/" + paper.getOrganizationId() + ".jpg";
//        Glide.with(mContext).load(url).into(holder.civ);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    String createTime = paper.getCreateTime();
                    String endTime = paper.getEndingTime();
                    Date createD = getDate(createTime);
                    Date endD = getDate(endTime);

                    long now = System.currentTimeMillis();
                    if (now < createD.getTime()) {
                        ToastUtil.showToast("?????????????????????");
                        return;
                    }
                    if (now > endD.getTime()) {
                        ToastUtil.showToast("?????????????????????");
                        return;
                    }
                    LoadingDialog dialog = new LoadingDialog(mContext);
                    dialog.show();

                    intoTestActivity(v.getContext(), mPapers.get(position).getTestpaperId(), type, dialog);
                } else {
                    //ExamActivity.start(v.getContext(), mPapers.get(position).getTestpaperId(), type);
                }
            }
        });

    }


    /**
     * ??????????????????????????????????????????Date
     * @param str ?????????????????????????????????
     * @return Date
     */
    private Date getDate(String str) {
        str = str.substring(0, 23) + "Z";
        str = str.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return date;
    }

    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    public void addAll(List<Testpaper> papers) {
        if (mPapers.size() == 0) {
            mPapers.addAll(papers);
            notifyDataSetChanged();
            return;
        }
        int start = mPapers.size() + 1;
        int count = papers.size();
        mPapers.addAll(papers);
        notifyItemRangeInserted(start, count);
    }

    private String getTextString(String text) {
        if (text == null || text.equals("")) {
            return "??????";
        }
        return text;
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_paper) CircleImageView civ;
        @BindView(R.id.textView5) TextView tv_title;
        @BindView(R.id.textView6) TextView tv_chapter;
        @BindView(R.id.textView7) TextView tv_type;
        @BindView(R.id.textView8) TextView tv_date;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void intoTestActivity(final Context context, final int testpaperId, final int type, final LoadingDialog dialog) {


        Map<String, Integer> info = new HashMap<>();
        info.put("testpaperId", testpaperId);
        info.put("userId", App.getInstance().getUser().getUserId());

        Log.e(TAG, "checkTheTestFinish: "+ GsonUtil.GsonString(info) );
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
                        ToastUtil.showToast("??????????????????");
                    }

                    @Override
                    public void onNext(ResponseResult<StudentTestResult> result) {
                        dialog.dismiss();
                        if (result.getState() == 1) {
                            double socre = result.getData().getSocre();
                            List<StudentAnswerResult> results = new ArrayList<>();

                            List<StudentAnswerAnalysis> analysis = result.getData().getStudentAnswerAnalysis();
                            for (StudentAnswerAnalysis analysi : analysis) {
                                results.add(new StudentAnswerResult(analysi));
                            }
                            //GradeActivity.start(context, socre, GsonUtil.GsonString(results));

                        } else {
                            //ExamActivity.start(context, testpaperId, type);
                        }
                    }
                });
    }

    public interface CheckApi {

        @POST("organization/studentTestDetail")
        @Headers("Content-Type:application/json")
        Observable<ResponseResult<StudentTestResult>> checkTheTestFinish(@Body Map<String, Integer> testpaperId);
    }

}
