
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetThongBaoReadResponse extends CommonResponse {
	//
	String returnString = "";
	public String getReturnString() {
		return returnString;
	}


	public GetThongBaoReadResponse(Context context, String result, AParent currentActivity) {
		super(context,result,currentActivity);
		if (CommonHelper.checkValidString(result) && (!this.hasError())) {
			if (CommonHelper.checkValidString(result) && (!this.hasError())) {
				if(CommonHelper.checkValidString(result) && ((result.equalsIgnoreCase("true") || (result.equalsIgnoreCase("false"))))){
					returnString = result;
				}else{
					parseStringToJson((result));
				}

			}
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


			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}
	}
}
