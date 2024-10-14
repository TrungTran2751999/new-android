package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.ThongBaoListItemObj;
import com.huewaco.cskh.webservice.objects.GetThongBaoDeleteResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoReadResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.sql.SQLOutput;


public class FTabThongBaoListDetail extends FParent {
	protected com.huewaco.cskh.interfacex.ITFReadDeleteThongBao iTFReadDeleteThongBao;
	protected ThongBaoListItemObj obj;
	public int POSITION = -1;

	//protected String date;
	private TextView id_tv_content_message,
			id_tv_date,
			id_title_thongbao,
			id_thoi_gian,
			id_ly_do,
			id_ma_kh,
			id_so_tien,
			id_chi_so_moi,
			id_dia_chi,
			id_so_tien_tt,
			id_content_no_format,
			id_footer_thong_bao;
	private LinearLayout
			layout_TNCN,
			layout_SCMN,
			layout_BTN,
			layout_XNTT;
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

		//layout
		layout_TNCN = v.findViewById(R.id.layout_TNCN);
		layout_SCMN = v.findViewById(R.id.layout_TNCN);
		layout_BTN = v.findViewById(R.id.layout_BTN);
		layout_XNTT = v.findViewById(R.id.layout_XNTT);
		//noi dung thong bao
			//TNCN, SCMN
		id_title_thongbao = v.findViewById(R.id.id_title_thong_bao);
		id_thoi_gian = v.findViewById(R.id.id_thoi_gian);
		id_ly_do = v.findViewById(R.id.id_ly_do);
		id_footer_thong_bao = v.findViewById(R.id.id_footer_thong_bao);
			//BTN
		id_ma_kh = v.findViewById(R.id.id_ma_kh);
		id_so_tien = v.findViewById(R.id.id_so_tiens);
		id_chi_so_moi = v.findViewById(R.id.id_chi_so_moi);
		id_dia_chi = v.findViewById(R.id.id_dia_chi);
			//XNTT
		id_so_tien_tt = v.findViewById(R.id.id_so_tien_tt);
			//
		id_content_no_format = v.findViewById(R.id.id_content_no_format);
		//id_tv_content_message.setText(Html.fromHtml(obj.getNoiDung()));
		//Do noi dung lay tu obj.getNoiDung() duoc lay tu database nen qua xu ly bang handleThongBao se loi nen se goi di xong set lai
//		CommonHelper.setTextHtml(id_tv_content_message,obj.getNoiDung());
//		String ct = id_tv_content_message.getText().toString();
		handleThongBao(obj.getNoiDung(), obj.getPosition());
//		CommonHelper.setTextHtml(id_tv_content_message,handleThongBao(ct, obj.getPosition()));
//		id_tv_date.setText(date);
//		id_tv_title.setText(title);
	}
	private String handleThongBao(String tb, int position){
		try{
			if(!tb.contains("<p>")){
				switch (position) {
					case 0:{
						String title = "Công ty HueWaco xin thông báo lịch tạm ngừng cấp nước ảnh hưởng đến quý khách hàng như sau:";
//						String thoiGian = "<p><strong>Thời gian: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[0] + "</em><p><br/>";
//						String lyDo = "<p><strong>Lý do: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[1].split("Rất mong quý khách hàng thông cảm")[0] + "</p></em><br/>";
						String thoiGian = tb.split("Thời gian:")[1].split("Lý do:")[0];
						String timeFrom = thoiGian.split("dự kiến")[0];
						String timeTo = thoiGian.split("dự kiến")[1];

						String hourFrom = timeFrom.split("Từ")[1].split("giờ")[0].trim();
						String minuteFrom = timeFrom.split("giờ")[1].split("phút")[0].trim();
						if(Integer.parseInt(hourFrom)<10){
							hourFrom = "0"+hourFrom;
						}
						if(Integer.parseInt(minuteFrom) < 10){
							minuteFrom = "0"+minuteFrom+":00";
						}else{
							minuteFrom = minuteFrom+":00";
						}
						String dayFrom = timeFrom.split("ngày")[1];

						String hourTo = timeTo.split("đến")[1].split("giờ")[0].trim();
						String minuteTo = timeTo.split("giờ")[1].split("phút")[0].trim();
						if(Integer.parseInt(hourTo)<10){
							hourTo = "0"+hourTo;
						}
						if(Integer.parseInt(minuteTo) < 10){
							minuteTo = "0"+minuteTo+":00";
						}else{
							minuteTo = minuteTo+":00";
						}
						String dayTo = timeTo.split("ngày")[1].replace(".","");

						String timeFromResult = dayFrom + " " +hourFrom+":"+minuteFrom;
						String timeToResult = dayTo+" "+hourTo+":"+minuteTo;

						String time = timeFromResult + " - " + timeToResult;
						String lyDo = tb.split("Thời gian:")[1].split("Lý do:")[1].split("Rất mong quý khách hàng thông cảm")[0];
						String footer = "Rất mong quý khách hàng thông cảm.\nTrung tâm CSKH: 1800 0036";
						//visible
						layout_TNCN.setVisibility(View.VISIBLE);
						//set text
						id_title_thongbao.setText("THÔNG BÁO LỊCH TẠM NGỪNG CẤP NƯỚC");
						id_tv_content_message.setText(title);
						id_thoi_gian.setText(time);
						id_ly_do.setHeight(300);
						id_ly_do.setText(lyDo);
						id_footer_thong_bao.setText(footer);
						break;
					}
					case 1: {
						String title = "Công ty HueWaco xin thông báo sự cố mất nước ảnh hưởng đến quý khách hàng như sau:";
//						String thoiGian = "<p><strong>Thời gian: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[0] + "</em><p><br/>";
//						String lyDo = "<p><strong>Lý do: </strong><em>" + tb.split("Thời gian:")[1].split("Lý do:")[1].split("Rất mong quý khách hàng thông cảm")[0] + "</p></em><br/>";
						String thoiGian = tb.split("Thời gian:")[1].split("Lý do:")[0];
						String timeFrom = thoiGian.split("dự kiến")[0];
						String timeTo = thoiGian.split("dự kiến")[1];

						String hourFrom = timeFrom.split("Từ")[1].split("giờ")[0].trim();
						String minuteFrom = timeFrom.split("giờ")[1].split("phút")[0].trim();
						if(Integer.parseInt(hourFrom)<10){
							hourFrom = "0"+hourFrom;
						}
						if(Integer.parseInt(minuteFrom) < 10){
							minuteFrom = "0"+minuteFrom+":00";
						}else{
							minuteFrom = minuteFrom+":00";
						}
						String dayFrom = timeFrom.split("ngày")[1];

						String hourTo = timeTo.split("đến")[1].split("giờ")[0].trim();
						String minuteTo = timeTo.split("giờ")[1].split("phút")[0].trim();
						if(Integer.parseInt(hourTo)<10){
							hourTo = "0"+hourTo;
						}
						if(Integer.parseInt(minuteTo) < 10){
							minuteTo = "0"+minuteTo+":00";
						}else{
							minuteTo = minuteTo+":00";
						}
						String dayTo = timeTo.split("ngày")[1].replace(".","");

						String timeFromResult = dayFrom + " " +hourFrom+":"+minuteFrom;
						String timeToResult = dayTo+" "+hourTo+":"+minuteTo;

						String time = timeFromResult + " - " + timeToResult;
						String lyDo = tb.split("Thời gian:")[1].split("Lý do:")[1].split("Rất mong quý khách hàng thông cảm")[0];
						String footer = "Rất mong quý khách hàng thông cảm.\nTrung tâm CSKH: 1800 0036";
						//visible
						layout_SCMN.setVisibility(View.VISIBLE);
						//set text
						id_title_thongbao.setText("THÔNG BÁO SỰ CỐ MẤT NƯỚC");
						id_tv_content_message.setText(title);
						id_thoi_gian.setText(time);
						id_ly_do.setText(lyDo);
						id_footer_thong_bao.setText(footer);
						break;
					}
					case 2:{
//						String title = "<p>" + tb.split("Mã khách hàng:")[0] + "</p><br/>";
//						String maKhachHang = "<p><strong>Mã khách hàng: </strong>" + tb.split("Mã khách hàng:")[1].split("Tên khách hàng:")[0] + "<p>";
//						String tenKhachHang = "<p><strong>Tên khách hàng: </strong>" + tb.split("Tên khách hàng:")[1].split("Đã tiêu thụ:")[0] + "<p>";
//						String daTieuThu = "<p><strong>Đã tiêu thụ: </strong>" + tb.split("Đã tiêu thụ:")[1].split("Số tiền thanh toán:")[0] + "<p>";
//						String soTienThanhToan = "<p><strong>Số tiền thanh toán: </strong>" + tb.split("Số tiền thanh toán:")[1].split("Trân trọng kính báo.")[0] + "<p><br/>";
//						String footer = "<p>Trân trọng kính báo.</p><p>" + tb.split("Trân trọng kính báo.")[1] + "</p>";

						String tenKh = GlobalVariable.KHACH_HANG.getTenKhachHang();
						String maKhachHang = GlobalVariable.KHACH_HANG.getIdKh();
						String tenKhachHang = tb.split("Tên khách hàng:")[1].split("Đã tiêu thụ:")[0];
						String daTieuThu = tb.split("Đã tiêu thụ:")[1].split("Số tiền thanh toán:")[0];
						String soTienThanhToan = tb.split("Số tiền thanh toán:")[1].split("Trân trọng kính báo.")[0];
						String kyHanThanhtoan = tb.split("khách hàng kỳ")[1].split("như sau: ")[0];
						//lay dia chi khach hang
						String soNha = GlobalVariable.KHACH_HANG.getSoNha();
						String phuongXa = GlobalVariable.KHACH_HANG.getPhuongXa();
						String quanHuyen = GlobalVariable.KHACH_HANG.getQuanHuyen();
						String diaChi = soNha+" "+phuongXa+", "+quanHuyen;
						String title = "Công ty HueWACO trân trọng thông báo đến khách hàng "+tenKh+" thông tin tiền nước kỳ"+kyHanThanhtoan+"như sau:";
						String footer = "Quý khách vui lòng bỏ qua thông báo này nếu đã thanh toán.\n Trung tâm DVKH: 1800 0036";

						//visible
						layout_BTN.setVisibility(View.VISIBLE);
						//set text
						id_title_thongbao.setText("THÔNG BÁO THANH TOÁN TIỀN NƯỚC");
						id_tv_content_message.setText(title);
						id_ma_kh.setText(maKhachHang);
						id_so_tien.setText(soTienThanhToan);
						id_chi_so_moi.setText(daTieuThu);
						id_dia_chi.setText(diaChi);
						id_footer_thong_bao.setText(footer);
						break;
					}
					case 3:{
//						String title = "<p>" + tb.split("số tiền:")[0] + "</p><br/>";
//						String soTien = "<p><strong>Số tiền: </strong>" + tb.split("số tiền:")[1].split("Trân trọng cảm ơn.")[0] + "<p><br/>";
//						String footer = "<p>Trân trọng cảm ơn.</p><p>" + tb.split("Trân trọng cảm ơn.")[1] + "</p>";
						String kyHan = tb.split("kỳ hóa đơn tháng")[1].split("số tiền")[0].replace(",","");
						String title = "Công ty HueWACO xác nhận quý khách hàng đã thanh toán tiền nước của kỳ hóa đơn tháng"+kyHan+":";
						//visble
						layout_XNTT.setVisibility(View.VISIBLE);
						//set text
						id_title_thongbao.setText("THÔNG BÁO XÁC NHẬN THANH TOÁN");
						id_tv_content_message.setText(title);
						id_so_tien_tt.setText(tb.split("số tiền:")[1].split("Trân trọng cảm ơn.")[0]);
						id_footer_thong_bao.setText("Trân trọng cảm ơn quý khách đã thanh toán kỳ hạn.\nTrung tâm DVKH: 1800 0036");
						break;
					}
					case 4:{
						System.out.println(tb);
//						String title = "<p>" + tb.split("số tiền")[0] + "</p><br/>";
//						String soTien = "<p><strong>Số tiền: </strong>" + tb.split("số tiền")[1].split("Kính mong ")[0] + "<p><br/>";
//						String kinhMong = "<p>"+"Kính mong "+tb.split("Trân trọng cảm ơn.")[0].split("Kính mong")[1]+"</p>";
//						String footer = "<p>Trân trọng cảm ơn.</p><p>" + tb.split("Trân trọng cảm ơn.")[1] + "</p>";
//						tb = title + soTien + kinhMong + footer;

						String kyHan = tb.split("kỳ hóa đơn")[1].split("số tiền")[0].replace(",","");
						String title = "Công ty HueWACO xác nhận quý khách hàng chưa thanh toán tiền nước của kỳ hóa đơn tháng"+kyHan+":";
						String footer = tb.split("VNĐ.")[1].split(".Trân trọng cảm ơn")[0];
						//visble
						layout_XNTT.setVisibility(View.VISIBLE);
						//set text
						id_title_thongbao.setText("THÔNG BÁO NỢ KỲ HẠN THANH TOÁN");
						id_tv_content_message.setText(title);
						id_so_tien_tt.setText(tb.split("số tiền")[1].split("VNĐ.")[0]);
						id_footer_thong_bao.setText(footer + "\n" + " Trân trọng cảm ơn.\nTrung tâm DVKH: 1800 0036");
						break;
					}
					case 5:{
						System.out.println(tb);
//						String title = "<p>" + tb.split(":")[0] + ": "+ "</p><br/>";
//						String time = "<strong>" + tb.split("tháng")[1].split("đã bắt đầu")[0] + "</strong>";
//
//						String contentNoNode = tb.split(":")[1].split("tháng")[0].trim();
//						String contentNoNodeHandled = Character.toUpperCase(contentNoNode.charAt(0)) + contentNoNode.substring(1);
//						String content = "<p>"+ contentNoNodeHandled + " tháng " + time + " đã bắt đầu ";
//
//						String footerNonode = tb.split("đã bắt đầu")[1].replace(",","").trim();
//						String footerNonodeHandle = Character.toUpperCase(footerNonode.charAt(0)) + footerNonode.substring(1);
//						String footer = "</p>"+"<p><br/>"+footerNonodeHandle+"</p>";
//
//						tb = title + content + footer;
						String content = tb.split("Công ty Cổ phần cấp nước T.T.Huế")[1];
						content = "Công ty HueWACO"+content;
						id_title_thongbao.setText("THÔNG BÁO GHI CHỈ SỐ");
						id_content_no_format.setVisibility(View.VISIBLE);
						id_content_no_format.setText(content);
						break;
					}
					case 6:{
						System.out.println(tb);
//						String title = "<p>" + tb.split(":")[0] + ": "+ "</p><br/>";
//						String time = "<strong>" + tb.split("tháng")[1].split("đã bắt đầu")[0] + "</strong>";
//
//						String contentNoNode = tb.split(":")[1].split("tháng")[0].trim();
//						String contentNoNodeHandled = Character.toUpperCase(contentNoNode.charAt(0)) + contentNoNode.substring(1);
//						String content = "<p>"+ contentNoNodeHandled + " tháng " + time + " đã bắt đầu ";
//
//						String footerNonode = tb.split("đã bắt đầu")[1].replace(",","").trim();
//						String footerNonodeHandle = Character.toUpperCase(footerNonode.charAt(0)) + footerNonode.substring(1);
//						String footer = "</p>"+"<p><br/>"+footerNonodeHandle+"</p>";
//
//						tb = title + content + footer;
						String content = tb.split("Công ty Cổ phần cấp nước T.T.Huế")[1];
						content = "Công ty HueWACO"+content;
						id_title_thongbao.setText("THÔNG BÁO KHÁC");
						id_content_no_format.setVisibility(View.VISIBLE);
						id_content_no_format.setText(content);
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
