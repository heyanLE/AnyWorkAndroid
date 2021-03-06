package com.qgstudio.anyworkc.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.qgstudio.anyworkc.App;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.common.DialogManagerActivity;
import com.qgstudio.anyworkc.data.model.Organization;
import com.qgstudio.anyworkc.data.model.User;
import com.qgstudio.anyworkc.dialog.NewBaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KK on 2018/9/21.
 */

public class NewOrganizationAdapter extends RecyclerView.Adapter<NewOrganizationAdapter.Holder> implements Filterable {

    private User mUser;

    public interface JoinOrganizationListener {
        void join(int organizationId, String password, int position);
    }

    public interface LeaveOrganizationListener {
        void leave(int organizationId, int position);
    }

    private int mType;
    private DialogManagerActivity mContext;

    private List<Organization> mOrganizations;
    private List<Organization> mCopyOfOrganizations;

    private NewBaseDialog mBaseDialog;

    private JoinOrganizationListener mJoinOrganizationListener;
    private LeaveOrganizationListener mLeaveOrganizationListener;

    public void updateItemJoinStatus(int position, boolean isJoin) {
        checkDialog();

        mOrganizations.get(position).setIsJoin(isJoin ? 1 : 0);
        notifyItemChanged(position);
    }

    private void checkDialog() {
        if (mBaseDialog != null && mBaseDialog.isShowing()) {
            mBaseDialog.cancel();
        }
    }

    public void setJoinOrganizationListener(JoinOrganizationListener joinOrganizationListener) {
        mJoinOrganizationListener = joinOrganizationListener;
    }

    public void setLeaveOrganizationListener(LeaveOrganizationListener leaveOrganizationListener) {
        mLeaveOrganizationListener = leaveOrganizationListener;
    }

//    public NewOrganizationAdapter(int type, DialogManagerActivity context, List<Organization> organizations) {
//        mType = type;
//        mContext = context;
//        mOrganizations = organizations;
//        mUser = App.getInstance().getUser();
//    }

    public NewOrganizationAdapter(DialogManagerActivity context, List<Organization> organizations) {
//        mType = type;
        mContext = context;
        mOrganizations = organizations;
        mUser = App.getInstance().getUser();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_new_organization, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final Organization organization = mOrganizations.get(position);
        //??????????????????????????????
        holder.tv_name.setText(organization.getOrganizationName());
//        holder.tv_teacher.setText(organization.getTeacherName());
//        holder.tv_description.setText(organization.getDescription());

//        //????????????
//        int id = organization.getOrganizationId();
//        GlideUtil.setPictureWithOutCache(holder.civ, id, R.drawable.ic_organization_default);

        //???type???all?????????????????????
//        if (mType == TYPE_ALL) {
//            holder.tv_status.setText(organization.getIsJoin() == 1 ? "?????????" : "?????????");
//            holder.tv_status.setTextColor(organization.getIsJoin() == 1 ?
//                    ContextCompat.getColor(mContext,R.color.status_join_true_text) : ContextCompat.getColor(mContext,R.color.status_join_false_text));
//            holder.tv_status.setBackgroundColor(organization.getIsJoin() == 1 ?
//                    ContextCompat.getColor(mContext, R.color.status_join_true_bg) : ContextCompat.getColor(mContext, R.color.status_join_false_bg));
//        }else {
//            holder.tv_status.setVisibility(View.GONE);
//            holder.tv_teacher.setVisibility(View.GONE);
//            holder.tv_description.setVisibility(View.GONE);
//        }
        if(organization.getIsJoin() == 1){
            holder.tv_name.setBackgroundResource(R.drawable.bg_my_organize);
//            holder.tv_name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.showToast("??????????????????");
//                }
//            });
        }else {
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NewOrganizationActivity.myOrganization != null){
                        return;
                    }
                    final DialogCreateHelper helper = new DialogCreateHelper(organization);
                    mBaseDialog = helper.create(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mJoinOrganizationListener != null) {
                                int id = organization.getOrganizationId();
//                                String password = helper.edi.getText().toString();
                                String password = helper.edtPassword.getText().toString();
                                mJoinOrganizationListener.join(id, password, position);
                            }
                        }
                    });
                    if (!mBaseDialog.isShowing()) {
                        mBaseDialog.show();
                    }
                }
            });
        }



        //??????item??????????????????
//        if (mType == TYPE_ALL && organization.getIsJoin() != 1) {//?????????
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final DialogCreateHelper helper = new DialogCreateHelper(organization, DialogCreateHelper.TYPE_JOIN);
//                    mBaseDialog = helper.create(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (mJoinOrganizationListener != null) {
//                                int id = organization.getOrganizationId();
//                                String password = helper.edi.getText().toString();
//                                mJoinOrganizationListener.join(id, password, position);
//                            }
//                        }
//                    });
//                    if (!mBaseDialog.isShowing()) {
//                        mBaseDialog.show();
//                    }
//                }
//            });
//        } else {//?????????
//
//            //????????????????????????
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PaperActivity.start(v.getContext(), mOrganizations.get(position).getOrganizationId());
//                }
//            });
//
//            //????????????????????????
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    DialogCreateHelper helper = new DialogCreateHelper(organization, DialogCreateHelper.TYPE_LEAVE);
//                    mBaseDialog = helper.create(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (mLeaveOrganizationListener != null) {
//                                int id = organization.getOrganizationId();
//                                mLeaveOrganizationListener.leave(id, position);
//                            }
//                        }
//                    });
//                    if (!mBaseDialog.isShowing()) {
//                        mBaseDialog.show();
//                    }
//                    return true;
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return mOrganizations.size();
    }


    public void add(Organization organization) {
        mOrganizations.add(organization);
        notifyItemInserted(mOrganizations.size());
    }

    public void addAll(List<Organization> organizations) {
//        if (organizations.size() == 0) {
//            mOrganizations.addAll(organizations);
//            notifyDataSetChanged();
//            return;
//        }
//        int start = mOrganizations.size() + 1;
//        int count = organizations.size();
//        mOrganizations.addAll(organizations);
//        notifyItemRangeInserted(start, count);

//        Iterator<Organization> iter = organizations.iterator();
//        while (iter.hasNext()){
//            Organization og = iter.next();
//            if(og.getOrganizationName().equals(NewOrganizationActivity.myOrganization.getOrganizationName())){
//                organizations.remove(og);
//            }
//        }
        mOrganizations.clear();
        mOrganizations.addAll(organizations);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mOrganizations.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public Filter getFilter() {
        if (mOrganizations.size() <= 0 && mCopyOfOrganizations == null) {
            return null;
        }

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //??????????????????????????????null
                if (constraint == null || constraint.length() == 0) {
                    return null;
                }

                //??????
                //1.??????????????????
                if (mCopyOfOrganizations != null) {
                    mOrganizations.clear();
                    mOrganizations.addAll(mCopyOfOrganizations);
                }

                //2.??????????????????
                FilterResults results = new FilterResults();
                String prefix = constraint.toString();

                List<Organization> newValues = new ArrayList<>();
                for (Organization organization : mOrganizations) {
                    String name = organization.getOrganizationName() + "";
                    if (name.contains(prefix)) {
                        newValues.add(organization);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //???results???null?????????????????????
                if (results == null) {
                    mOrganizations.clear();
                    mOrganizations.addAll(mCopyOfOrganizations);
//                    mCopyOfOrganizations.clear();
                    notifyDataSetChanged();
                    return;
                }

                //??????
                //1.??????????????????
                if (mCopyOfOrganizations == null || mCopyOfOrganizations.size() == 0) {
                    copyData();
                }

                //2.??????????????????????????????
                mOrganizations.clear();
                mOrganizations.addAll((List<Organization>) results.values);

                notifyDataSetChanged();
            }
        };
    }

    private void copyData() {
        if (mCopyOfOrganizations == null) {
            mCopyOfOrganizations = new ArrayList<>(mOrganizations.size());
        }
        mCopyOfOrganizations.addAll(mOrganizations);
    }

    class Holder extends RecyclerView.ViewHolder {

//        @BindView(R.id.civ_organization)
//        CircleImageView civ;
        @BindView(R.id.tv_name)
        TextView tv_name;
//        @BindView(R.id.tv_teacher) TextView tv_teacher;
//        @BindView(R.id.tv_description) TextView tv_description;
//        @BindView(R.id.tv_status) TextView tv_status;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DialogCreateHelper {
        View root;
//        CircleImageView civ;
        TextView tvOgName;
//        EditText edi;
        TextView tvTeacher;
        EditText edtPassword;

        private Organization organization;
//        private String type;
//
//        public static final String TYPE_JOIN = "??????";
//        public static final String TYPE_LEAVE = "??????";

        public DialogCreateHelper(Organization o) {
            organization = o;
//            type = t;

            //view?????????
            root = LayoutInflater.from(mContext).inflate(R.layout.new_dialog_organization, null);
            tvOgName = root.findViewById(R.id.tv_name);
            tvTeacher = root.findViewById(R.id.tv_teacher);
            edtPassword = root.findViewById(R.id.et_password);
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
            String teacher = "?????????" + organization.getTeacherName();
            tvOgName.setText(organizationName);
            tvTeacher.setText(teacher);

//            String description = "?????????" + organization.getDescription();
//            tv.setText(teacher + "\n" + description);
//
//            //??????????????????????????????
//            int visibility = t.equals(TYPE_JOIN) ? View.VISIBLE : View.GONE;
//            edi.setVisibility(visibility);

        }

        public NewBaseDialog create(View.OnClickListener listener) {
            return new NewBaseDialog.Builder(mContext, -2)
                    .view(root)
                    .setPositiveListener("????????????", listener)
                    .cancelTouchout(true)
                    .build();
        }

    }



}
