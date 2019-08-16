package com.plass.computer.pplas_project.Manager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.plass.computer.pplas_project.R;

import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ems);
        setTitle("환자 리스트");
        context = this;

    }

    class PatientAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> list;
        public PatientAdapter(Context context, ArrayList<String> list ) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
