package com.huewaco.cskh.webservice.objects;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetPasswordResponse extends CommonResponse {


    String idkh = "";
    String password = "";



    public GetPasswordResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private void parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        try {
			/*jsonArr = new JSONArray(str);
			if (jsonArr != null && jsonArr.length() > 0) {
				obj = jsonArr.getJSONObject(0);
			}*/
            obj = new JSONObject(str);
        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (obj != null) {
            try {
                idkh = obj.getString("Idkh");
                password = obj.getString("Password");

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
    public String getIdkh() {
        return idkh;
    }

    public String getPassword() {
        return password;
    }
}
