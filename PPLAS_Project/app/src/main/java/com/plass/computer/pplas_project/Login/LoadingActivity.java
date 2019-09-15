package com.plass.computer.pplas_project.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by alsrh on 2019-09-14.
 */

public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
