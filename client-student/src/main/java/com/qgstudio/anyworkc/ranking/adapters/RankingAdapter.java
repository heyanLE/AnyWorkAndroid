package com.qgstudio.anyworkc.ranking.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qgstudio.anyworkc.App;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.data.model.RankingMessage;
import com.qgstudio.anyworkc.main.HomeActivity;
import com.qgstudio.anyworkc.utils.GlideUtil;
import com.qgstudio.anyworkc.widget.RankRecyclerView;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RankingMessage> rankingMessages;
    private Typeface ttf;
    private RankRecyclerView rankRecyclerView;

    public RankingAdapter(Context context, ArrayList<RankingMessage> rankingMessages, RankRecyclerView recyclerView) {
        this.context = context;
        this.rankingMessages = rankingMessages;
        rankRecyclerView = recyclerView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView numberImage;
        TextView numberText;
        ImageView headPic;
        TextView name;
        TextView studentId;
        TextView score;

        public ViewHolder(View itemView) {
            super(itemView);

            numberImage = itemView.findViewById(R.id.number_image);
            numberText = itemView.findViewById(R.id.number_text);
            headPic = itemView.findViewById(R.id.head_pic);
            name = itemView.findViewById(R.id.name);
            studentId = itemView.findViewById(R.id.student_id);
            score = itemView.findViewById(R.id.score);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        RankingMessage rankingMessage = rankingMessages.get(position);
        //??????????????????
//        if (App.getInstance().getUser().getStudentId().equals(rankingMessage.getStudentId())) {
//            holder.itemView.setBackgroundColor(Color.parseColor("#441690ed"));
//        } else {
//            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//        }
        if (position == 0) {
            holder.numberText.setVisibility(View.GONE);
            holder.numberImage.setVisibility(View.VISIBLE);
            holder.numberImage.setImageResource(R.drawable.icon_ranking_first);
        } else if (position == 1) {
            holder.numberText.setVisibility(View.GONE);
            holder.numberImage.setVisibility(View.VISIBLE);
            holder.numberImage.setImageResource(R.drawable.icon_ranking_second);
        } else if (position == 2) {
            holder.numberText.setVisibility(View.GONE);
            holder.numberImage.setVisibility(View.VISIBLE);
            holder.numberImage.setImageResource(R.drawable.icon_ranking_third);
        } else {
            //????????????
            holder.numberText.setTypeface(ttf == null ? Typeface.createFromAsset(context.getAssets(), "zhankukuaileti.ttf") : ttf);

            holder.numberImage.setVisibility(View.GONE);
            holder.numberText.setVisibility(View.VISIBLE);
            holder.numberText.setText("NO." + (position + 1));
        }
        GlideUtil.setPictureWithOutCache(holder.headPic, rankingMessage.getImagePath(), R.drawable.icon_head);
        if (rankingMessage.getStudentId().endsWith(App.getInstance().getUser().getStudentId())) {
            holder.name.setTextColor(Color.parseColor("#99427cfd"));
            holder.studentId.setTextColor(Color.parseColor("#99427cfd"));
            holder.numberText.setTextColor(Color.parseColor("#99427cfd"));
        } else {
            holder.name.setTextColor(Color.parseColor("#616161"));
            holder.studentId.setTextColor(Color.parseColor("#616161"));
            holder.numberText.setTextColor(Color.parseColor("#616161"));
        }
        holder.name.setText(rankingMessage.getUsername());
        holder.studentId.setText(rankingMessage.getStudentId());
        holder.score.setTypeface(ttf == null ? Typeface.createFromAsset(context.getAssets(), "zhankukuaileti.ttf") : ttf);
        //??????????????????????????????
        int score = (int) rankingMessage.getScore();
        if (score < 60) {
            //????????????????????????
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.score.setBackground(context.getDrawable(R.drawable.background_score_red));
            }
        } else if (score < 80) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.score.setBackground(context.getDrawable(R.drawable.background_score_yellow));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.score.setBackground(context.getDrawable(R.drawable.background_score_green));
            }
        }
        holder.score.setText(score + "");
        holder.headPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rankRecyclerView.isNeedScroll = false;
                        showPopup(holder.headPic, event);
                        break;
                    case MotionEvent.ACTION_UP:
                        rankRecyclerView.isNeedScroll = true;
                        hidePopup();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        rankRecyclerView.isNeedScroll = true;
                        hidePopup();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move(holder.headPic, event);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankingMessages.size();
    }

    PopupWindow popupWindow;

    private void showPopup(final ImageView view, final MotionEvent event) {
        FrameLayout frameLayout = new FrameLayout(context);
        final ImageView view1 = new ImageView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200, 200);

        view1.setLayoutParams(layoutParams);
        frameLayout.addView(view1);
        view1.setImageDrawable(view.getDrawable());
        view1.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Point C = calculateXY(view, event);
                view1.layout(C.x, C.y, C.x + view1.getWidth(), C.y + view1.getHeight());
            }
        });
        hidePopup();
        popupWindow = new PopupWindow(frameLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAsDropDown(view, 0, 0);

    }

    private void move(View view, MotionEvent event) {
        if (popupWindow == null) {
            return;
        }
        Point C = calculateXY(view, event);
        View view1 = ((FrameLayout) popupWindow.getContentView()).getChildAt(0);
        view1.layout(C.x, C.y, C.x + view1.getWidth(), C.y + view1.getHeight());

    }

    private Point calculateXY(View view, MotionEvent event) {
        View view1 = popupWindow.getContentView();
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        //??????????????????
        Point A = new Point(viewLocation[0] + view.getWidth() / 2, viewLocation[1] + view.getHeight() / 2);
        //???????????????
        Point B = new Point((int) event.getRawX(), (int) event.getRawY());
        //???View????????????
        int centerDistance = 200;
        //????????????
        int distanceAB = (int) Math.pow(Math.pow((A.x - B.x), 2) + Math.pow((A.y - B.y), 2), 0.5);
        //????????????
        float scale = (float) centerDistance / distanceAB;
        //C?????????A????????????
        int dx = (int) ((A.x - B.x) * scale);
        int dy = (int) ((A.y - B.y) * scale);
        //??????C????????????
        Point C = new Point(A.x + dx, A.y + dy);
        //??????c???????????????
        C.offset(-100, -100 - HomeActivity.statusBarHeight);
        return C;
    }

    private void hidePopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        popupWindow = null;
    }
}
