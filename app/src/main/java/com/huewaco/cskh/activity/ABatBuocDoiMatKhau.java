package com.huewaco.cskh.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

public class ABatBuocDoiMatKhau extends AParent {
    private Button id_btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_buoc_doi_mat_khau);
        initComponent();
        addListener();
    }

    @Override
    protected void initComponent() {
        //id_btn_continue = (Button) findViewById(R.id.id_btn_continue);
    }

    @Override
    protected void addListener() {
        id_btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_continue:
                startActivity(new Intent(getApplicationContext(),ALogin3.class));
                if(GlobalVariable.IS_ANIMATION_ACTIVITY){
                    overridePendingTransition(R.anim.next_1, R.anim.next_2);
                }
                break;
            default:
                break;
        }
    }

}
