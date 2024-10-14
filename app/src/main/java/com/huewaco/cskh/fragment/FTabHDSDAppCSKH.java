package com.huewaco.cskh.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.interfacex.ITFShowlocation;
import com.huewaco.cskh.webservice.objects.GetHoaDonDienTuResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class FTabHDSDAppCSKH extends FParent implements AdapterView.OnItemClickListener, LocationListener, ITFShowlocation {
//    private ListView id_lv_list_diem_thu;
//    private ListViewDiemThuTienAdapter adapter;
//    private ArrayList<DiemThuTienListItemObj> mArrDiemThuTienList = new ArrayList<>();
//    protected String title;
//    Location location = null;
    private  int thisYear;
    private int thisMonth;
    private ArrayList<String> mArrYears = new ArrayList<String>();
    private ArrayList<String> mArrMonth = new ArrayList<String>();
    protected String title;
    private ImageView imageView_hdsd;
    private Button btn_thong_bao;
    private Button btn_tra_cuu;
    private Button btn_dich_vu;
    private Button btn_tien_ich;
    private WebView webViewHDSD;
    private ScaleGestureDetector scaleDetector;
    private float scale = 1f;
    private float prevScale = 1f;
    private Matrix matrix = new Matrix();
    private PointF last = new PointF();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fpActivity.itfShowlocation = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_tab_hdsd_app_cskh, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();


        if (CommonHelper.isNetworkAvailable(fpActivity)) {

//            new GetTCThongTinDiemThuTask(fpActivity).execute();
        } else {
            CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
        }
//        askPermissionsAndShowMyLocation();//ok
        return v;
    }

    @Override
    public void onDetach() {
//        fpActivity.itfShowlocation = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void initComponent(View v) {
        id_tv_title.setText("Hướng dẫn sử dụng");
        btn_thong_bao = v.findViewById(R.id.id_btn_tb);
        btn_tra_cuu = v.findViewById(R.id.id_btn_tra_cuu);
        btn_dich_vu = v.findViewById(R.id.id_btn_dich_vu);
        btn_tien_ich = v.findViewById(R.id.id_btn_tien_ich);
        webViewHDSD = v.findViewById(R.id.web_view_hdsd);

        WebSettings webSettings = webViewHDSD.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true); // Bật điều khiển zoom
        webSettings.setDisplayZoomControls(false); // Ẩn

        String html = "<html><body>" +
                "<img style='margin-bottom:15px' src='"+GlobalVariable.DOMAIN+"Scripts/image/thong_bao_hdsd.png' width='100%' />" +
                "<img style='margin-bottom:15px' src='"+GlobalVariable.DOMAIN+"Scripts/image/tra_cuu_hdsd.png' width='100%' />" +
                "<img style='margin-bottom:15px' src='"+GlobalVariable.DOMAIN+"/Scripts/image/dich_vu_hdsd.png' width='100%' />" +
                "<img style='margin-bottom:15px' src='"+GlobalVariable.DOMAIN+"Scripts/image/tien_ich_hdsd.png' width='100%' />"+
                "</body></html>";

        webViewHDSD.loadData(html, "text/html", "UTF-8");
        btn_thong_bao.setVisibility(View.GONE);
        btn_tra_cuu.setVisibility(View.GONE);
        btn_dich_vu.setVisibility(View.GONE);
        btn_tien_ich.setVisibility(View.GONE);
    }
    private void initData() {

    }

    @Override
    protected void addListener() {
        btn_thong_bao.setOnClickListener(this);
        btn_tra_cuu.setOnClickListener(this);
        btn_dich_vu.setOnClickListener(this);
        btn_tien_ich.setOnClickListener(this);
//        id_lv_list_diem_thu.setOnItemClickListener(this);
//        btn_xem_hddt.setOnClickListener(this);
//        btn_download.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_tb:

                break;
            case R.id.id_btn_tra_cuu:

                break;
            default:
//                break;
        }
    }

    public void setText() {

    }
    private static String layLoaiTapTin(Context context, String tenTapTin) {
        // Sử dụng MediaStore để lấy loại MIME của tệp
        String loaiTapTin = context.getContentResolver().getType(Uri.fromFile(new File(tenTapTin)));

        // Nếu loại MIME là null, hãy cố gắng xác định nó dựa trên phần mở rộng tệp
        if (loaiTapTin == null) {
            String phanMoRong = tenTapTin.substring(tenTapTin.lastIndexOf(".") + 1);
            loaiTapTin = MimeTypeMap.getSingleton().getMimeTypeFromExtension(phanMoRong.toLowerCase());
        }

        return loaiTapTin;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        if (mArrDiemThuTienList != null && mArrDiemThuTienList.size() > 0) {
//            DiemThuTienListItemObj dthu = mArrDiemThuTienList.get(position);
//            if (CommonHelper.checkValidString(dthu.getKinhDo()) && CommonHelper.checkValidString(dthu.getViDo())) {
//                AMap.kinhDo = Double.parseDouble(dthu.getKinhDo());
//                AMap.vido = Double.parseDouble(dthu.getViDo());
//                AMap.title = dthu.getDiaChi();
//                fpActivity.startActivity(new Intent(fpActivity, AMap.class));
//            }
//        }
    }

//    public class GetTCThongTinDiemThuTask extends AsyncTask<String, Void, Void> {
//        boolean isError = false;
//        Context context;
//
//        public GetTCThongTinDiemThuTask(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            showLoading();
//        }
//
//        @Override
//        public Void doInBackground(String... params) {
//
//            ResultFromWebservice rs = new ResultFromWebservice();
//            GetTraCuu1ThongTinDiemThuResponse getTraCuu1ThongTinDiemThuResponse = rs.getTCThongTinDiemThuResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
//            if (getTraCuu1ThongTinDiemThuResponse != null && getTraCuu1ThongTinDiemThuResponse.getmArrItem() != null) {
//
//                mArrDiemThuTienList.clear();
//                mArrDiemThuTienList.addAll(getTraCuu1ThongTinDiemThuResponse.getmArrItem());
//            }
//            isError = getTraCuu1ThongTinDiemThuResponse.hasError();
//            Log.d("abc", "" + getTraCuu1ThongTinDiemThuResponse.getID() + "---------------: " + isError);
//            Log.d("abc", "" + getTraCuu1ThongTinDiemThuResponse.getID());
//            return null;
//        }
//
//        @Override
//        public void onPostExecute(Void result) {
//            disMissLoading();
//            adapter.refresh(mArrDiemThuTienList);
//            if (isError) {
//                Log.d("abc", "" + "---------------: " + isError);
//                fpActivity.startDangNhap4();
//            }else{
//                new SortTask().execute();
//            }
//        }
//    }

//    public class SortTask extends AsyncTask<String, Void, Void> {
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            showLoading();
//        }
//
//        @Override
//        public Void doInBackground(String... params) {
//            sortNearestDistanceShow5(location,mArrDiemThuTienList);
//
//            return null;
//        }
//
//        @Override
//        public void onPostExecute(Void result) {
//            disMissLoading();
//            adapter.refresh(mArrDiemThuTienList);
//            if(location != null) {
//                //CommonHelper.showWarning(fpActivity,"Lat: "+location.getLatitude()+" Long: "+location.getLongitude());
//                Log.d("abc=============", "longlat: " + location + "   long: " + location.getLongitude() + "  lat: " + location.getLatitude());
//            }
//        }
//    }

//    private void sortNearestDistanceShow5(Location location, ArrayList<DiemThuTienListItemObj> mArrDiemThuTien) {
///*        location = new Location("");//provider name is unecessary
//        location.setLatitude(16.4571399);//your coords of course
//        location.setLongitude(107.5745091);*/
//        if (location != null && (mArrDiemThuTien != null && mArrDiemThuTien.size() > 0)) {
//            for (DiemThuTienListItemObj dt : mArrDiemThuTien) {
//                //double kc = CommonHelper.distance2Coordinate(16.4571399, 107.5745091, Double.valueOf(dt.getKinhDo()), Double.valueOf(dt.getViDo()));
//                double kc = CommonHelper.distance2Coordinate(location.getLatitude(), location.getLongitude(), Double.valueOf(dt.getViDo()), Double.valueOf(dt.getKinhDo()));
//                dt.setDistance2CurrentCoordinate(kc);
//                //Location lc = new Location(9.888f,9.999f);
//                Location targetLocation = new Location("");//provider name is unecessary
//                targetLocation.setLatitude(Double.valueOf(dt.getViDo()));//your coords of course
//                targetLocation.setLongitude(Double.valueOf(dt.getKinhDo()));
//                //Location des = new Location(dt.getViDo(),dt.getKinhDo());
//                //Log.d("abc","============================== myLat: "+location.getLatitude()+" myLong: "+location.getLongitude()+" yourLat: "+dt.getViDo()+" yourLong: "+dt.getKinhDo()+" KC: "+kc+" KC2: "+location.distanceTo(targetLocation));
//            }
//           // Log.d("abc","==============TRƯỚC KHI SORT================");
//            for(DiemThuTienListItemObj dt : mArrDiemThuTien){
//                //Log.d("abc","dis: "+dt.getDistance2CurrentCoordinate());
//            }
//            CommonHelper.sortDistanceNearest2CurrentCoordinate(mArrDiemThuTien);
//            Log.d("abc","==============SAU KHI SORT================");
//            for(DiemThuTienListItemObj dt : mArrDiemThuTien){
//               // Log.d("abc","dis: "+dt.getDistance2CurrentCoordinate());
//                Location targetLocation = new Location("");//provider name is unecessary
//                targetLocation.setLatitude(Double.valueOf(dt.getViDo()));//your coords of course
//                targetLocation.setLongitude(Double.valueOf(dt.getKinhDo()));
//                double kc = CommonHelper.distance2Coordinate(location.getLatitude(), location.getLongitude(), Double.valueOf(dt.getViDo()), Double.valueOf(dt.getKinhDo()));
//                Log.d("abc","============================== myLat: "+location.getLatitude()+" myLong: "+location.getLongitude()+" yourLat: "+dt.getViDo()+" yourLong: "+dt.getKinhDo()+" KC: "+kc+" KC2: "+location.distanceTo(targetLocation));
//            }
//            ArrayList<DiemThuTienListItemObj> mArrDiemThuTienNew = new ArrayList<>();
//            for(int i = 0 ; i <10; i++){
//                mArrDiemThuTienNew.add(mArrDiemThuTien.get(i));
//            }
//            mArrDiemThuTienList.clear();
//            mArrDiemThuTienList.addAll(mArrDiemThuTienNew);
//
//        }
//    }

    private void askPermissionsAndShowMyLocation() {
        // Với API >= 23, bạn phải hỏi người dùng cho phép xem vị trí của họ.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(fpActivity, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(fpActivity, Manifest.permission.ACCESS_FINE_LOCATION);


            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                // Các quyền cần người dùng cho phép.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
                ActivityCompat.requestPermissions(fpActivity, permissions,
                        GlobalVariable.REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                return;
            }
        }

        // Hiển thị vị trí hiện thời trên bản đồ.
        //this.showMyLocation();
//        getLocation();
    }

/*    public void turnGPSOn() {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        fpActivity.sendBroadcast(intent);

        String provider = Settings.Secure.getString(fpActivity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            fpActivity.sendBroadcast(poke);
        }
    }*/
    public Location getLocation() {
//        //boolean canGetLocation = false;
//
//        //double latitude, longitude;
//        // Millisecond
//        final long MIN_TIME_BW_UPDATES = 1000;
//        // Met
//        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
//        try {
//            LocationManager locationManager = (LocationManager) fpActivity.getSystemService(LOCATION_SERVICE);
//
//            // getting GPS status
//            boolean isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            // getting network status
//            boolean isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                // no network provider is enabled
//                CommonHelper.showWarning(fpActivity,getString(R.string.not_gps_network));
//            } else {
//                //canGetLocation = true;
//                if (isNetworkEnabled) {
//
//                    if (ActivityCompat.checkSelfPermission(fpActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fpActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                        return null;
//                    }
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                    Log.d("Network", "Network Enabled");
//                    if (locationManager != null) {
///*                        location = new Location(LocationManager.NETWORK_PROVIDER);//provider name is unecessary
//                        location.setLatitude(16.4571399);//your coords of course
//                        location.setLongitude(107.5745091);*/
//                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
///*                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                        }*/
//                    }
//                }
//                // if GPS Enabled get lat/long using GPS Services
//                if (isGPSEnabled) {
//                    if (location == null) {
//                        locationManager.requestLocationUpdates(
//                                LocationManager.GPS_PROVIDER,
//                                MIN_TIME_BW_UPDATES,
//                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                        Log.d("GPS", "GPS Enabled");
//                        if (locationManager != null) {
//                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
///*                            location = new Location(LocationManager.GPS_PROVIDER);//provider name is unecessary
//                            location.setLatitude(16.4571399);//your coords of course
//                            location.setLongitude(107.5745091);*/
///*                            if (location != null) {
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                            }*/
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(location != null) {
//            //CommonHelper.showWarning(fpActivity,"Lat: "+location.getLatitude()+" Long: "+location.getLongitude());
//            Log.d("abc=============", "longlat: " + location + "   long: " + location.getLongitude() + "  lat: " + location.getLatitude());
//        }
//        new SortTask().execute();
//        return location;
        return null;
    }

    private String getEnabledLocationProvider() {
//        LocationManager locationManager = (LocationManager) fpActivity.getSystemService(LOCATION_SERVICE);
//
//
//        // Tiêu chí để tìm một nhà cung cấp vị trí.
//        Criteria criteria = new Criteria();
//
//        // Tìm một nhà cung vị trí hiện thời tốt nhất theo tiêu chí trên.
//        // ==> "gps", "network",...
//        String bestProvider = locationManager.getBestProvider(criteria, true);
//
//        boolean enabled = locationManager.isProviderEnabled(bestProvider);
//
//        if (!enabled) {
//            Toast.makeText(fpActivity, "No location provider enabled!", Toast.LENGTH_LONG).show();
//            // Log.i(MYTAG, "No location provider enabled!");
//            return null;
//        }
//        return bestProvider;
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public void showLocation() {
        if(fpActivity.itfShowlocation != null){
            this.getLocation();//ok
        }

    }
}
