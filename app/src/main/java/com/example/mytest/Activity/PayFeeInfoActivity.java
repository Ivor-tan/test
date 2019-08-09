package com.example.mytest.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mytest.bean.PayPropertyFeeInfoBean;
import com.test.my.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class PayFeeInfoActivity extends AppCompatActivity {
    private RadioButton currently_charge, historical_arrears;
    private RecyclerView pay_property_list;
    private TextView pay_property_sum, gopay;
    private int sum;

    private CommonAdapter<PayPropertyFeeInfoBean> mAdapter;

    private List<PayPropertyFeeInfoBean> currently_charge_list = new ArrayList<>();
    private List<PayPropertyFeeInfoBean> historical_arrears_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee_info);
        initData();
        initView();


    }

    private void initData() {


        for (int i = 0; i < 5; i++) {
            PayPropertyFeeInfoBean bean = new PayPropertyFeeInfoBean();
            bean.setFee_year("2019");
            bean.setFee_month("4");
            bean.setMoneys(100);
            historical_arrears_list.add(bean);
            currently_charge_list.add(bean);
        }
        for (int i = 0; i < 5; i++) {
            PayPropertyFeeInfoBean bean = new PayPropertyFeeInfoBean();
            bean.setFee_year("2018");
            bean.setFee_month("4");
            bean.setMoneys(100);
            currently_charge_list.add(bean);
        }

    }

    private void initView() {
        pay_property_list = findViewById(R.id.pay_property);
        setAdapter(currently_charge_list);
        pay_property_sum = findViewById(R.id.pay_property_sum);
        currently_charge = findViewById(R.id.currently_charge);
        historical_arrears = findViewById(R.id.historical_arrears);
        gopay = findViewById(R.id.gopay);
        gopay.setOnClickListener(new myOnClickListener());
    }

    private void setAdapter(List<PayPropertyFeeInfoBean> datelist) {
        mAdapter = new CommonAdapter<PayPropertyFeeInfoBean>(PayFeeInfoActivity.this, R.layout.activity_pay_fee_info_item, datelist) {
            @Override
            protected void convert(final ViewHolder holder, PayPropertyFeeInfoBean payPropertyFeeInfoBean, int position) {

                holder.setText(R.id.fee_year_month, payPropertyFeeInfoBean.getFee_year() + "年" + payPropertyFeeInfoBean.getFee_month() + "月账单")
                        .setText(R.id.moneys, payPropertyFeeInfoBean.getMoneys() + "元")
                        .setTag(R.id.pay_property_fee_CheckBox, new Integer(position))
                        .setOnClickListener(R.id.pay_property_fee_CheckBox, new myOnClickListener(holder, payPropertyFeeInfoBean, position));

                ((CheckBox) holder.getView(R.id.pay_property_fee_CheckBox)).setChecked(payPropertyFeeInfoBean.getCheckBox_state());
            }
        };
        pay_property_list.setAdapter(mAdapter);
        pay_property_list.setLayoutManager(new LinearLayoutManager(this));
    }

    private class myOnClickListener implements View.OnClickListener {
        PayPropertyFeeInfoBean payPropertyFeeInfoBean;
        ViewHolder holder;
        int pos;

        public myOnClickListener() {
        }

        public myOnClickListener(ViewHolder holder, PayPropertyFeeInfoBean payPropertyFeeInfoBean, int pos) {
            this.payPropertyFeeInfoBean = payPropertyFeeInfoBean;
            this.holder = holder;
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pay_property_fee_CheckBox:
                    payPropertyFeeInfoBean.setCheckBox_state(((CheckBox) holder.getView(R.id.pay_property_fee_CheckBox)).isChecked());
                    if (payPropertyFeeInfoBean.getCheckBox_state()) {
                        sum = sum + currently_charge_list.get(pos).getMoneys();
                        pay_property_sum.setText(sum + "元");
                    } else {
                        sum = sum - currently_charge_list.get(pos).getMoneys();
                        pay_property_sum.setText(sum + "元");
                    }
                    break;
                case R.id.currently_charge:
                    Log.d("main_test", "onClick: currently_charge");
                    currently_charge.setTextSize(18);
                    historical_arrears.setTextSize(16);
                    break;

                case R.id.historical_arrears:
                    Log.d("main_test", "onClick: historical_arrears");
//                    setAdapter(historical_arrears_list);
//                    pay_property_list.notifyAll();
                    currently_charge.setTextSize(16);
                    historical_arrears.setTextSize(18);
                    break;
                case R.id.gopay:
                    Log.d("main_test", "onClick: historical_arrears");
                    currently_charge_list.clear();
                    mAdapter.notifyDataSetChanged();
                    break;


            }


        }
    }
}