package com.example.mytest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mytest.bean.PayPropertyFeeInfoBean;
import com.test.my.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组 的 Adapter
 * <p>
 * Created by Tnno Wu on 2018/03/05.
 */

public class PayPropertyFeeAdapter extends RecyclerView.Adapter<PayPropertyFeeAdapter.ViewHolder> {

    private static final String TAG = PayPropertyFeeAdapter.class.getSimpleName();

    private Context mContext;

    private List<PayPropertyFeeInfoBean> mList = new ArrayList<>();

    public PayPropertyFeeAdapter(Context context) {
        mContext = context;
    }

    public void setGroupDataList(List<PayPropertyFeeInfoBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_pay_fee_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.fee_year.setText(mList.get(position).getFee_year());
        holder.moneys.setText(mList.get(position).getMoneys() + "");
        holder.fee_year_month.setText(mList.get(position).getFee_year() + mList.get(position).getFee_month());
//          分组
//        if (position == 0) {
//            holder.fee_year.setVisibility(View.VISIBLE);
//        } else {
//            if (mList.get(position).getFee_year().equals(mList.get(position - 1).getFee_year())) {
//                holder.fee_year.setVisibility(View.GONE);
//            } else {
//                holder.fee_year.setVisibility(View.VISIBLE);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView moneys, fee_year, fee_year_month;
        CheckBox pay_property_fee_radiobutton;

        public ViewHolder(View itemView) {
            super(itemView);
            pay_property_fee_radiobutton = itemView.findViewById(R.id.pay_property_fee_CheckBox);
            moneys = itemView.findViewById(R.id.moneys);
            fee_year_month = itemView.findViewById(R.id.fee_year_month);
        }
    }
}
