package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.ThongBaoListItemObj;
import com.huewaco.cskh.webservice.objects.GetThongBaoDeleteResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoReadResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;


public class FTabThongBaoListDetail extends FParent {
	protected com.huewaco.cskh.interfacex.ITFReadDeleteThongBao iTFReadDeleteThongBao;
	protected ThongBaoListItemObj obj;
	public int POSITION = -1;

	//protected String date;
	private TextView id_tv_content_message,id_tv_date;
	private Button id_btn_camera,id_btn_send;
	private EditText id_edt_content;
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
		View v = inflater.inflate(R.layout.frag_tab_thongbao_list_detail, container, false);
		initCommonView(v, this);
		initComponent(v);
		addListener();
		setText();
		if(CommonHelper.isNetworkAvailable(fpActivity)){
			if(!obj.isDaDoc()){
				new GetReadThongBaoMoiTask().execute();
			}

		}else{
			CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
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
		id_tv_content_message = (TextView)v.findViewById(R.id.id_tv_content_message);
		id_tv_date = (TextView)v.findViewById(R.id.id_tv_date);

		id_btn_camera = (Button)v.findViewById(R.id.id_btn_camera);
		id_btn_send = (Button)v.findViewById(R.id.id_btn_send);
		id_edt_content=(EditText)v.findViewById(R.id.id_edt_content);

		//id_tv_content_message.setText(Html.fromHtml(obj.getNoiDung()));
		//Do noi dung lay tu obj.getNoiDung() duoc lay tu database nen qua xu ly bang handleThongBao se loi nen se goi di xong set lai
		CommonHelper.setTextHtml(id_tv_content_message,obj.getNoiDung());
		String ct = id_tv_content_message.getText().toString();
		CommonHelper.setTextHtml(id_tv_content_message,handleThongBao(ct, obj.getPosition()));
//		id_tv_date.setText(date);
//		id_tv_title.setText(title);
	}
	private String handleThongBao(String tb, int position){
		try{
			if(!tb.contains("<p>")){
				switch (position) {
					case 0:{
						String title = "<p>" + tb.split("Thời gian:")[0] + "</p><br/>";
						String thoiGian = "<p><strong>Thời gian: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[0] + "</em><p><br/>";
						String lyDo = "<p><strong>Lý do: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[1].split("Rất mong quý khách hàng thông cảm")[0] + "</p></em><br/>";
						String footer = "<p>Rất mong quý khách hàng thông cảm.</p><p>" + tb.split("Rất mong quý khách hàng thông cảm")[1] + "</p>";
						tb = title + thoiGian + lyDo + footer;
						break;
					}
					case 1: {
						String title = "<p>" + tb.split("Thời gian:")[0] + "</p><br/>";
						String thoiGian = "<p><strong>Thời gian: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[0] + "</em><p><br/>";
						String lyDo = "<p><strong>Lý do: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[1].split("Rất mong quý khách hàng thông cảm")[0] + "</p></em><br/>";
						String footer = "<p>Rất mong quý khách hàng thông cảm.</p><p>" + tb.split("Rất mong quý khách hàng thông cảm")[1] + "</p>";
						tb = title + thoiGian + lyDo + footer;
						break;
					}
					case 2:{
						String title = "<p>" + tb.split("Mã khách hàng:")[0] + "</p><br/>";
						String maKhachHang = "<p><strong>Mã khách hàng: </strong>" + tb.split("Mã khách hàng:")[1].split("Tên khách hàng:")[0] + "<p>";
						String tenKhachHang = "<p><strong>Tên khách hàng: </strong>" + tb.split("Tên khách hàng:")[1].split("Đã tiêu thụ:")[0] + "<p>";
						String daTieuThu = "<p><strong>Đã tiêu thụ: </strong>" + tb.split("Đã tiêu thụ:")[1].split("Số tiền thanh toán:")[0] + "<p>";
						String soTienThanhToan = "<p><strong>Số tiền thanh toán: </strong>" + tb.split("Số tiền thanh toán:")[1].split("Trân trọng kính báo.")[0] + "<p><br/>";
						String footer = "<p>Trân trọng kính báo.</p><p>" + tb.split("Trân trọng kính báo.")[1] + "</p>";
						tb = title + maKhachHang + tenKhachHang + daTieuThu + soTienThanhToan + footer;
						break;
					}
					case 3:{
						String title = "<p>" + tb.split("số tiền:")[0] + "</p><br/>";
						String soTien = "<p><strong>Số tiền: </strong>" + tb.split("số tiền:")[1].split("Trân trọng cảm ơn.")[0] + "<p><br/>";
						String footer = "<p>Trân trọng cảm ơn.</p><p>" + tb.split("Trân trọng cảm ơn.")[1] + "</p>";
						tb = title + soTien + footer;
						break;
					}
					case 4:{
						String title = "<p>" + tb.split("số tiền")[0] + "</p><br/>";
						String soTien = "<p><strong>Số tiền: </strong>" + tb.split("số tiền")[1].split("Kính mong ")[0] + "<p><br/>";
						String kinhMong = "<p>"+"Kính mong "+tb.split("Trân trọng cảm ơn.")[0].split("Kính mong")[1]+"</p>";
						String footer = "<p>Trân trọng cảm ơn.</p><p>" + tb.split("Trân trọng cảm ơn.")[1] + "</p>";
						tb = title + soTien + kinhMong + footer;
						break;
					}
					case 5:{
						String title = "<p>" + tb.split(":")[0] + ": "+ "</p><br/>";
						String time = "<strong>" + tb.split("tháng")[1].split("đã bắt đầu")[0] + "</strong>";

						String contentNoNode = tb.split(":")[1].split("tháng")[0].trim();
						String contentNoNodeHandled = Character.toUpperCase(contentNoNode.charAt(0)) + contentNoNode.substring(1);
						String content = "<p>"+ contentNoNodeHandled + " tháng " + time + " đã bắt đầu ";

						String footerNonode = tb.split("đã bắt đầu")[1].replace(",","").trim();
						String footerNonodeHandle = Character.toUpperCase(footerNonode.charAt(0)) + footerNonode.substring(1);
						String footer = "</p>"+"<p><br/>"+footerNonodeHandle+"</p>";

						tb = title + content + footer;
						break;
					}
				}
			}
			return tb;
		}catch (Exception e){
			System.out.println(e);
			return tb;
		}
	}

	@Override
	protected void addListener() {
		id_btn_camera.setOnClickListener(this);
		id_btn_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.id_btn_camera:
				break;
			case R.id.id_btn_send:
				break;
			case R.id.id_btn_right:
				if(CommonHelper.isNetworkAvailable(fpActivity)){
					new GetDeleteThongBaoMoiTask().execute();
				}else{
					CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
				}
				break;
		default:
			break;
		}

	}

	public void setText() {

	}
	public class GetReadThongBaoMoiTask extends AsyncTask<String, Void, Void> {
		boolean isSuccess = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		public Void doInBackground(String... params) {

			ResultFromWebservice rs = new ResultFromWebservice();
			//
			GetThongBaoReadResponse getThongBaoReadResponse = rs.getThongBaoReadResponse(fpActivity,obj.getId(), GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
			if(getThongBaoReadResponse.getReturnString().equalsIgnoreCase("true")){
				System.out.println(getThongBaoReadResponse);
				isSuccess = true;
			}else{
				isSuccess = false;
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			if(isSuccess){
				iTFReadDeleteThongBao.refreshRead(obj);
				if(fpActivity.itfRefreshThongBaoDaDoc != null){
					if(POSITION != -1){

						fpActivity.itfRefreshThongBaoDaDoc.reload(POSITION);
					}
				}
				if(POSITION != -1) {
					if (GlobalVariable.TONG_TIN_CHUA_DOC > 0) {
						GlobalVariable.TONG_TIN_CHUA_DOC--;
						fpActivity.showBadgeNumber(GlobalVariable.TONG_TIN_CHUA_DOC);
					}
				}
			}else{
				CommonHelper.showWarning(fpActivity,getString(R.string.loi_xay_ra_thu_luc_khac));
			}

		}
	}
	public class GetDeleteThongBaoMoiTask extends AsyncTask<String, Void, Void> {
		boolean isSuccess = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		public Void doInBackground(String... params) {

			ResultFromWebservice rs = new ResultFromWebservice();
			//
			GetThongBaoDeleteResponse getThongBaoDeleteResponse = rs.getThongBaoDeleteResponse(fpActivity,obj.getId(), GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
			if(getThongBaoDeleteResponse.getReturnString().equalsIgnoreCase("true")){
				isSuccess = true;
			}else{
				isSuccess = false;
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			if(isSuccess){
				iTFReadDeleteThongBao.refreshDelete(obj);
				fpActivity.onBackPressed();
				if(GlobalVariable.TONG_TIN_CHUA_DOC > 0){
					GlobalVariable.TONG_TIN_CHUA_DOC--;
					if(GlobalVariable.TONG_TIN_CHUA_DOC>0){
						fpActivity.showBadgeNumber(GlobalVariable.TONG_TIN_CHUA_DOC);
					}else{
						fpActivity.showBadgeNumber(0);
					}
				}
			}else{
				CommonHelper.showWarning(fpActivity,getString(R.string.loi_xay_ra_thu_luc_khac));
			}
		}
	}
}
