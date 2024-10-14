package com.huewaco.cskh.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentTransaction;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.activity.AIntro2;
import com.huewaco.cskh.activity.ALogin3;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.GridTaskAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.helper.ShareReferenceConfig;
import com.huewaco.cskh.interfacex.ITFRefreshThongBaoDaDoc;
import com.huewaco.cskh.objects.TaskObj;
import com.huewaco.cskh.objects.ThongBaoListItemObj;
import com.huewaco.cskh.webservice.objects.GetPasswordResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoSoTinChuaDocResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class FTabThongBao extends FParent implements AdapterView.OnItemClickListener, ITFRefreshThongBaoDaDoc{
    private GridView id_grid_task;
    private ArrayList<TaskObj> mArrTasks = new ArrayList<TaskObj>();
    private GridTaskAdapter adapter;
    private Button id_button_to_test;
    private GetPasswordResponse getPasswordResponse;
    //
    HashMap<String, ArrayList<ThongBaoListItemObj>> hashMap = new HashMap<String, ArrayList<ThongBaoListItemObj>>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_tab_thongbao, container, false);
        fpActivity.itfRefreshThongBaoDaDoc = this;
        initCommonView(v, this);
        initComponent(v);
        addListener();
        setText();
        if (CommonHelper.isNetworkAvailable(fpActivity)) {
            new GetSoLuongThongBaoMoiTask().execute();
        } else {
            CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
        }

        if(ShareReferenceConfig.instance(fpActivity).getPassWord().isEmpty()){
            new getPassword().execute();
        }
        if(ShareReferenceConfig.instance(fpActivity).getPassWord().equals("000000")){
            showDialogKhuyenCaoDoiMatKhau(fpActivity, getString(R.string.yeu_cau_doi_mat_khau));
        }
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void initComponent(View v) {
        id_button_to_test = v.findViewById(R.id.id_button_to_test);
        id_grid_task = (GridView) v.findViewById(R.id.id_grid_task);
        //---
        TaskObj lichcatnuoc = new TaskObj();
        lichcatnuoc.setNameTask(getString(R.string.tab_thongbao_tb_lichcatnuoc));
        lichcatnuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbao_lichcatnuoc));
        //lichcatnuoc.setNumberNotification(3);
        mArrTasks.add(lichcatnuoc);
        //---
        TaskObj matnuoc = new TaskObj();
        matnuoc.setNameTask(getString(R.string.tab_thongbao_tb_ttmatnuoc));
        matnuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongtinsucomatnuoc));
        mArrTasks.add(matnuoc);

        //---
        TaskObj tiennuoc = new TaskObj();
        tiennuoc.setNameTask(getString(R.string.tab_thongbao_tb_tttiennuoc));
        tiennuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongtintiennuoc));
        mArrTasks.add(tiennuoc);
        //---
        TaskObj thanhtoan = new TaskObj();
        thanhtoan.setNameTask(getString(R.string.tab_thongbao_tb_thanhtoan));
        thanhtoan.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbao_thanhtoan));
        mArrTasks.add(thanhtoan);

        //---
        TaskObj notiennuoc = new TaskObj();
        notiennuoc.setNameTask(getString(R.string.tab_thongbao_tb_notiennuoc));
        notiennuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbaonotiennuoc));
        mArrTasks.add(notiennuoc);
        //---
        TaskObj thongbaoghichiso = new TaskObj();
        thongbaoghichiso.setNameTask(getString(R.string.tab_thongbao_tb_ghichiso));
        thongbaoghichiso.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ghichiso1));
        mArrTasks.add(thongbaoghichiso);

        //---
        TaskObj khac = new TaskObj();
        khac.setNameTask(getString(R.string.tab_thongbao_tb_khac));
        khac.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbaokhac));
        mArrTasks.add(khac);

        //---

        adapter = new GridTaskAdapter(fpActivity, mArrTasks);
        id_grid_task.setAdapter(adapter);
        id_button_to_test.setAlpha(0);
        CommonHelper.isPushNotification(fpActivity);
    }

    @Override
    protected void addListener() {
        id_grid_task.setOnItemClickListener(this);
        id_button_to_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_button_to_test:

                break;
            default:
                break;
        }

    }

    public void setText() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        startNewFrag(null, position);
    }

    public void startNewFrag(ArrayList<ThongBaoListItemObj> mArr, int position) {
        FTabThongBaoList frg2 = new FTabThongBaoList();
        frg2.position = position;
        //frg2.mArrThongBaoList = mArr;
        frg2.title = mArrTasks.get(position).getNameTask();
        FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
        if (GlobalVariable.IS_ANIMATION) {
            transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        transaction2.addToBackStack(null);
        transaction2.replace(R.id.id_fr_tab_thongbao, frg2).commit();
    }

    public class GetSoLuongThongBaoMoiTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            //
            GetThongBaoSoTinChuaDocResponse getThongBaoSoTinChuaDocResponse = rs.getThongBaoSoTinChuaDocResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            if (getThongBaoSoTinChuaDocResponse != null) {
                for (int i = 0; i < mArrTasks.size(); i++) {
                    TaskObj t = mArrTasks.get(i);
                    if (i == 0) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoLichCatNuoc());
                    } else if (i == 1) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongTinSuCoMatNuoc());
                    } else if (i == 2) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongTinTienNuoc());
                    } else if (i == 3) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoThanhToan());
                    } else if (i == 4) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoNoTienNuoc());
                    } else if (i == 5) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoGhiChiSo());
                    } else if (i == 6) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoKhac());
                    }
                }
            }
            GlobalVariable.TONG_TIN_CHUA_DOC = getThongBaoSoTinChuaDocResponse.getTongSoTinChuaDoc();
            //Log.d("abc", "" + getThongBaoResponse.getIdkh());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            //disMissLoading();
            adapter.refresh(mArrTasks);
            fpActivity.showBadgeNumber(GlobalVariable.TONG_TIN_CHUA_DOC);
        }
    }
    public class getPassword extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }
        @Override
        protected Void doInBackground(String... strings) {
            ResultFromWebservice rs = new ResultFromWebservice();
            getPasswordResponse = rs.getPassword(fpActivity, GlobalVariable.KHACH_HANG.getIdKh(), GlobalVariable.LOGIN_TOKEN_TYPE+" "+GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            return null;
        }
        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(Objects.equals(getPasswordResponse.getPassword(), "GQeL0KjePknA4sWmJ4FqFg==")){
                showDialogKhuyenCaoDoiMatKhau(fpActivity, getString(R.string.yeu_cau_doi_mat_khau));
            }
        }
    }
    public void showDialogKhuyenCaoDoiMatKhau(Activity context, String content){
        Dialog dialog = new Dialog(
                context,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        LayoutInflater inf = LayoutInflater.from(context);
        View layout = inf.inflate(R.layout.dialog_baocaosuco_ngoaigio,
                null);
        layout.setFocusableInTouchMode(true);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        final Button id_btn_call = (Button) layout.findViewById(R.id.id_btn_call_bcsc_ng);
        id_btn_call.setText("Đổi mật khẩu");
        final WebView id_content = layout.findViewById(R.id.id_content_dialog_bcsc_ng);
        final Button id_close = layout.findViewById(R.id.id_btn_close_bcsc_ng);
        String str = CommonHelper.convertSignHTML(content);
        id_content.loadData(str,"text/html", "UTF-8");
        id_btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FTabCDDoiMatKhau cdDoiMatKhau = new FTabCDDoiMatKhau();
                FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
                if (GlobalVariable.IS_ANIMATION) {
                    transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                transaction2.addToBackStack(null);
                dialog.dismiss();
                transaction2.replace(R.id.id_fr_tab_thongbao, cdDoiMatKhau).commit();
            }
        });
        id_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public void reload(int position){
        TaskObj task = mArrTasks.get(position);
        int count = task.getNumberNotification();
        if(count > 0){
            count--;
            task.setNumberNotification(count);
            adapter.refresh(mArrTasks);
        }
    }
}
