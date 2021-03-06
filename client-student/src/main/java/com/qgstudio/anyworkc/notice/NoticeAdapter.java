package com.qgstudio.anyworkc.notice;

//import android.annotation.NonNull;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.common.DialogManagerActivity;
import com.qgstudio.anyworkc.core.Apis;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.dialog.NewBaseDialog;
import com.qgstudio.anyworkc.notice.data.Notice;
import com.qgstudio.anyworkc.utils.LogUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    public List<Notice> list;
    Context context;
    NewBaseDialog mBaseDialog;


    public NoticeAdapter(List<Notice> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.clClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("notice", "onclick");
                final int position = holder.getAdapterPosition();
                final Notice notice = list.get(position);
                Log.d("notice", notice.messageId + "");

//                View view = LayoutInflater.from(context).inflate(R.layout.dialog_notice, null);
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setView(view);
//                Dialog dialog = builder.create();
//                dialog.show();

                final NoticeAdapter.DialogCreateHelper helper = new NoticeAdapter.DialogCreateHelper(notice);
                helper.tvTime.setText(notice.createTime);
                helper.tvContent.setText(notice.content);
                helper.tvName.setText(notice.publisher);

                mBaseDialog = helper.create(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mBaseDialog.cancel();
//                        if (mJoinOrganizationListener != null) {
//                                int id = organization.getOrganizationId();
//                                String password = helper.edi.getText().toString();
//                                mJoinOrganizationListener.join(id, password, position);
//                        }
//                        if (mLeaveOrganizationListener != null) {
//                            int id = organization.getOrganizationId();
//                            mLeaveOrganizationListener.leave(id, position);
//                        }
//                        OrganizationRepository repository = new OrganizationRepository();
//                        repository.leaveOrganization(myOrganization.getOrganizationId(), NewOrganizationActivity.this);
                    }
                });
                if (!mBaseDialog.isShowing()) {
                    mBaseDialog.show();
                }

                NoticeApi noticeApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(NoticeApi.class);
                noticeApi.markWatched(Apis.markWatchedApi(),buildReadRequestParam(notice.messageId))
                        .subscribeOn(Schedulers.io())
                        .observeOn((AndroidSchedulers.mainThread()))
                        .subscribe(new Observer<ResponseResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.d("notice", "onError");
                            }

                            @Override
                            public void onNext(ResponseResult result) {
                                LogUtil.d("notice", "onnext");
                                if (result.getState() == 1) {
//                                        holder.mgNtRead.setImageResource(R.drawable.icon_menu_read);
                                    list.get(position).status = 1;
                                    NoticeAdapter.this.notifyDataSetChanged();
                                }
                            }
                        });

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
        // holder.textView.setText(list.get(position).toString());
        Notice notice = list.get(position);
        holder.tvNtContent.setText(notice.title);
        holder.tvNtName.setText(notice.publisher);
        holder.tvNtTime.setText(notice.createTime);
        if (notice.status == Notice.STATUS_READED) {
            holder.mgNtRead.setImageResource(R.drawable.icon_menu_read);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.textview_notice)
//        TextView textView;
        @BindView(R.id.tv_content_item_notice)
        TextView tvNtContent;
        @BindView(R.id.tv_name_item_notice)
        TextView tvNtName;
        @BindView(R.id.tv_time_item_notice)
        TextView tvNtTime;
        @BindView(R.id.image_view_item_notice)
        ImageView mgNtRead;
        @BindView(R.id.item_click)
        ConstraintLayout clClickItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    private Object buildReadRequestParam(int id) {
        HashMap info = new HashMap();
        info.put("messageId", id + "");
        return info;
    }

    class DialogCreateHelper {
        View root;
        //        CircleImageView civ;
//        TextView tvOgName;
        //        EditText edi;
//        TextView tvTeacher;
//        ImageView imgCancel;
//        Button btLeave;
        TextView tvContent;
        TextView tvName;
        TextView tvTime;

        private Notice notice;
//        private String type;
//
//        public static final String TYPE_JOIN = "??????";
//        public static final String TYPE_LEAVE = "??????";

        public DialogCreateHelper(Notice n) {
            notice = n;
//            type = t;

            //view?????????
            root = LayoutInflater.from(context).inflate(R.layout.dialog_notice, null);
            tvContent = root.findViewById(R.id.tv_content_item_notice);
            tvName = root.findViewById(R.id.tv_name_item_notice);
            tvTime = root.findViewById(R.id.tv_time_item_notice);
//            civ = (CircleImageView) root.findViewById(R.id.cimg);
//            tv = (TextView) root.findViewById(R.id.text);
//            edi = (EditText) root.findViewById(R.id.edi);
//            imgCancel = (ImageView) root.findViewById(R.id.image_cancel);
//            btLeave = (Button) root.findViewById(R.id.button);

//            //????????????
//            String url = API_DEFAULT_URL + "picture/organization/" + organization.getOrganizationId() + ".jpg";
//            Glide.with(mContext).load(url).into(civ);

            //????????????
//            String organizationName = organization.getOrganizationName();
//            String teacher = "???????????????" + organization.getTeacherName();


//            String description = "?????????" + organization.getDescription();
//            tv.setText(teacher + "\n" + description);
//
//            //??????????????????????????????
//            int visibility = t.equals(TYPE_JOIN) ? View.VISIBLE : View.GONE;
//            edi.setVisibility(visibility);

        }

        public NewBaseDialog create(View.OnClickListener listener) {
            return new NewBaseDialog.Builder((DialogManagerActivity) context, -2)
                    .view(root)
                    .setPositiveListener("??????", listener)
                    .cancelTouchout(true)
                    .build();
        }
    }
}
