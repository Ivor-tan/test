package com.example.myTest.Utils.View;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.test.my.R;


public class MyDialog extends AlertDialog implements View.OnClickListener {
    //在构造方法里提前加载了样式
    private Context context;//上下文
    private int layoutResID;//布局文件id
    private int[] listenedItem;//监听的控件id
    private View view;
    private RelativeLayout relativeLayout;

    public MyDialog(Context context, int layoutResID, int[] listenedItem) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItem = listenedItem;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提前设置Dialog的一些样式
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//设置dialog显示居中
        //dialogWindow.setWindowAnimations();设置动画效果
        setContentView(layoutResID);

        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);//去掉系统黑框

        setCanceledOnTouchOutside(false);//点击外部Dialog消失
//        遍历控件id添加点击注册
        for (int id : listenedItem) {
            findViewById(id).setOnClickListener(this);
            view = findViewById(id);
        }
    }

    private OnCenterItemClickListener listener;

    //很明显我们要在这里面写个接口，然后添加一个方法
    public interface OnCenterItemClickListener {
        void OnCenterItemClick(MyDialog dialog, View view);
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.OnCenterItemClick(this, v);
    }

    public int[] getListenedItem() {
        return listenedItem;
    }

    public View getView() {
        return view;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }
}
