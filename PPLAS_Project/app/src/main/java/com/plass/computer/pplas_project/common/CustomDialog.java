package com.plass.computer.pplas_project.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.plass.computer.pplas_project.R;

/**
 * Created by alsrh on 2019-09-22.
 */

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context,String title, String message) {

        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog);
    }
}
