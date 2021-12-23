package com.qgstudio.anyworkc.records;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.records.bean.RecordsData;

import java.util.List;

/**
 * 做题记录
 * 传入的三个list不允许为null
 */
public class RecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecordsAdapter";

    private static final int TODAY = 0;

    private static final int YESTERDAY = 1;

    private static final int EARLIER = 2;

    private static final int OTHER = 3;

    private static final int FINISH = 1;

    private static final int UNFINISH = 2;

    private List<RecordsData.DataBean> today;

    private List<RecordsData.DataBean> yesterday;

    private List<RecordsData.DataBean> earlier;

    private onItemClickListener l;

    public RecordsAdapter(List<RecordsData.DataBean> today, List<RecordsData.DataBean> yesterday, List<RecordsData.DataBean> earlier) {
        this.today = today;
        this.yesterday = yesterday;
        this.earlier = earlier;
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {

        View v;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
        }

        void setVisible(int visible) {
            if (visible == View.GONE) {
                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)v.getLayoutParams();
                param.width = 0;
                param.height = 0;
                v.setLayoutParams(param);
            }else if (visible == View.VISIBLE) {
                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)v.getLayoutParams();
                param.width = RecyclerView.LayoutParams.WRAP_CONTENT;
                param.height = RecyclerView.LayoutParams.WRAP_CONTENT;
                v.setLayoutParams(param);
            }else {

            }
        }
    }


    static class RecordsViewHolder extends RecyclerView.ViewHolder {

        TextView recordsTitle;

        TextView recordsTime;

        TextView recordsStatus;

        public RecordsViewHolder(@NonNull View itemView) {
            super(itemView);

            recordsTitle = itemView.findViewById(R.id.records_title_tv);
            recordsTime = itemView.findViewById(R.id.records_time_tv);
            recordsStatus = itemView.findViewById(R.id.records_status_tv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder vh = null;

        View v = null;

        if (i == TODAY) {
            vh = createTvViewHolder(viewGroup, "今天");
        }else if (i == YESTERDAY) {
            vh = createTvViewHolder(viewGroup, "咋天");
        }else if (i == EARLIER) {
            vh = createTvViewHolder(viewGroup, "更早");
        }else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_records, viewGroup, false);
            vh = new RecordsViewHolder(v);
        }

        return vh;
    }

    private RecyclerView.ViewHolder createTvViewHolder(ViewGroup viewGroup, String text) {
        View v = null;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_records_title, viewGroup, false);
        if (v instanceof  TextView) {
            TextView tv = (TextView)v;
            tv.setText(text);
        }
        return new TitleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        TitleViewHolder v = null;
        switch (type) {
            case TODAY:
                v = (TitleViewHolder)viewHolder;
                if (today.size() == 0) {
                    v.setVisible(View.GONE);
                }else {
                    v.setVisible(View.VISIBLE);
                }
                break;
            case YESTERDAY:
                v = (TitleViewHolder)viewHolder;
                if (yesterday.size() == 0) {
                    v.setVisible(View.GONE);
                }else {
                    v.setVisible(View.VISIBLE);
                }
                break;
            case EARLIER:
                v = (TitleViewHolder)viewHolder;
                if (earlier.size() == 0 || (today.size() == 0 && yesterday.size() == 0)) {
                    v.setVisible(View.GONE);
                }else {
                    v.setVisible(View.VISIBLE);
                }
                break;
            case OTHER:
                bindItem(viewHolder, i);
                break;
        }
    }

    private void bindItem(RecyclerView.ViewHolder vh, int position) {
        RecordsData.DataBean dataBean = null;
        if (position <= today.size()) {
            // 今天
            dataBean = today.get(position - 1);
        }else if (position <= today.size() + yesterday.size() + 1) {
            // 咋天
            dataBean = yesterday.get(position - today.size() - 2);
        }else {
            // 更早
            dataBean = earlier.get(position - today.size() - yesterday.size() - 3);
        }

        if (vh instanceof RecordsViewHolder) {
            RecordsViewHolder recordsViewHolder = (RecordsViewHolder)vh;
            recordsViewHolder.recordsTitle.setText(dataBean.getTestpaperTitle());
            // TODO 进行时间转换
            recordsViewHolder.recordsTime.setText("2020.08.30 15:30");

            if (dataBean.getStatus() == FINISH) {
                recordsViewHolder.recordsStatus.setText("已完成");
                recordsViewHolder.recordsStatus.setTextColor(Color.parseColor("#28BB8E"));
            }else {
                recordsViewHolder.recordsStatus.setText("未完成");
                recordsViewHolder.recordsStatus.setTextColor(Color.parseColor("#EC4241"));
            }
            bindClick(dataBean,recordsViewHolder);
        }
    }

    public void bindClick(final RecordsData.DataBean dataBean, RecordsViewHolder vh) {
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.onClick(dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "" + today.size() + yesterday.size() + earlier.size() + 3);
        return today.size() + yesterday.size() + earlier.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TODAY;
        }

        if (position == today.size() + 1) {
            return YESTERDAY;
        }

        if (position == yesterday.size() + today.size() + 2) {
            return EARLIER;
        }

        return OTHER;
    }

    public interface onItemClickListener{
        void onClick(RecordsData.DataBean dataBean);
    }

    public onItemClickListener getL() {
        return l;
    }

    public void setL(onItemClickListener l) {
        this.l = l;
    }
}
