package com.qgstudio.anyworkc.main;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.common.DialogManagerActivity;
import com.qgstudio.anyworkc.data.model.Organization;
import com.qgstudio.anyworkc.dialog.BaseDialog;
import com.qgstudio.anyworkc.paper.PaperActivity;
import com.qgstudio.anyworkc.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.qgstudio.anyworkc.data.ApiStores.API_DEFAULT_URL;
import static com.qgstudio.anyworkc.main.OrganizationFragment.TYPE_ALL;


/**
 * @author Yason 2017/7/10.
 */

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.Holder> implements Filterable{

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

    private BaseDialog mBaseDialog;

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

    public OrganizationAdapter(int type, DialogManagerActivity context, List<Organization> organizations) {
        mType = type;
        mContext = context;
        mOrganizations = organizations;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_organization, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final Organization organization = mOrganizations.get(position);

        //??????????????????????????????
        holder.tv_name.setText(organization.getOrganizationName());
        holder.tv_teacher.setText(organization.getTeacherName());
        holder.tv_description.setText(organization.getDescription());

        //????????????
        int id = organization.getOrganizationId();
        GlideUtil.setPictureWithOutCache(holder.civ, id, R.drawable.ic_organization_default);

        //???type???all?????????????????????
        if (mType == TYPE_ALL) {
            holder.tv_status.setText(organization.getIsJoin() == 1 ? "?????????" : "?????????");
            holder.tv_status.setTextColor(organization.getIsJoin() == 1 ?
                    ContextCompat.getColor(mContext,R.color.status_join_true_text) : ContextCompat.getColor(mContext,R.color.status_join_false_text));
            holder.tv_status.setBackgroundColor(organization.getIsJoin() == 1 ?
                    ContextCompat.getColor(mContext, R.color.status_join_true_bg) : ContextCompat.getColor(mContext, R.color.status_join_false_bg));
        }else {
            holder.tv_status.setVisibility(View.GONE);
            holder.tv_teacher.setVisibility(View.GONE);
            holder.tv_description.setVisibility(View.GONE);
        }

        //??????item??????????????????
        if (mType == TYPE_ALL && organization.getIsJoin() != 1) {//?????????
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogCreateHelper helper = new DialogCreateHelper(organization, DialogCreateHelper.TYPE_JOIN);
                    mBaseDialog = helper.create(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mJoinOrganizationListener != null) {
                                int id = organization.getOrganizationId();
                                String password = helper.edi.getText().toString();
                                mJoinOrganizationListener.join(id, password, position);
                            }
                        }
                    });
                    if (!mBaseDialog.isShowing()) {
                        mBaseDialog.show();
                    }
                }
            });
        } else {//?????????

            //????????????????????????
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaperActivity.start(v.getContext(), mOrganizations.get(position).getOrganizationId());
                }
            });

            //????????????????????????
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogCreateHelper helper = new DialogCreateHelper(organization, DialogCreateHelper.TYPE_LEAVE);
                    mBaseDialog = helper.create(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLeaveOrganizationListener != null) {
                                int id = organization.getOrganizationId();
                                mLeaveOrganizationListener.leave(id, position);
                            }
                        }
                    });
                    if (!mBaseDialog.isShowing()) {
                        mBaseDialog.show();
                    }
                    return true;
                }
            });
        }
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

        @BindView(R.id.civ_organization) CircleImageView civ;
        @BindView(R.id.tv_name) TextView tv_name;
        @BindView(R.id.tv_teacher) TextView tv_teacher;
        @BindView(R.id.tv_description) TextView tv_description;
        @BindView(R.id.tv_status) TextView tv_status;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DialogCreateHelper {
        View root;
        CircleImageView civ;
        TextView tv;
        EditText edi;

        private Organization organization;
        private String type;

        public static final String TYPE_JOIN = "??????";
        public static final String TYPE_LEAVE = "??????";

        public DialogCreateHelper(Organization o, String t) {
            organization = o;
            type = t;

            //view?????????
            root = LayoutInflater.from(mContext).inflate(R.layout.dialog_origanization, null);
            civ = (CircleImageView) root.findViewById(R.id.cimg);
            tv = (TextView) root.findViewById(R.id.text);
            edi = (EditText) root.findViewById(R.id.edi);

            //????????????
            String url = API_DEFAULT_URL + "picture/organization/" + organization.getOrganizationId() + ".jpg";
            Glide.with(mContext).load(url).into(civ);

            //????????????
            String teacher = "???????????????" + organization.getTeacherName();
            String description = "?????????" + organization.getDescription();
            tv.setText(teacher + "\n" + description);

            //??????????????????????????????
            int visibility = t.equals(TYPE_JOIN) ? View.VISIBLE : View.GONE;
            edi.setVisibility(visibility);

        }

        public BaseDialog create(View.OnClickListener listener) {
            return new BaseDialog.Builder(mContext, -2)
                    .title(organization.getOrganizationName())
                    .view(root)
                    .setPositiveListener(type, listener)
                    .cancelTouchout(true)
                    .build();
        }

    }

}
