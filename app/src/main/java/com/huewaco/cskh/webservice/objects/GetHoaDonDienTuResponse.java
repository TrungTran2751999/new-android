package com.huewaco.cskh.webservice.objects;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;

import org.json.JSONObject;

import java.util.Objects;

public class GetHoaDonDienTuResponse extends CommonResponse{
    public int status;
    public String base64Pdf;
    public GetHoaDonDienTuResponse(Context context, String str, AParent currentActivity) {
        super(context, str, currentActivity);
        parseStringToJson(str);
    }
    private void parseStringToJson(String str) {
        if(!Objects.equals(str, "null")) {
            JSONObject obj = null;
            try {

                obj = new JSONObject(str);
            } catch (Exception e1) {
                System.out.print(e1.getMessage());
            }
            if (obj != null) {
                try {
                    base64Pdf = obj.getString("base64Pdf");
                    status = obj.getInt("status");
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }else{
            System.out.println("dsdsdsds");
        }
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBase64Pdf() {
        return base64Pdf;
    }

    public void setBase64Pdf(String base64Pdf) {
        this.base64Pdf = base64Pdf;
    }

}
