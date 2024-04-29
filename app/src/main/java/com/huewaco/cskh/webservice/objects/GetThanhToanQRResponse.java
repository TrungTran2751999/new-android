package com.huewaco.cskh.webservice.objects;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class GetThanhToanQRResponse extends CommonResponse{
    public String QrContent;
    public ArrayList<Bill> Bills = new ArrayList<>();
    public Infos Infos = new Infos();
    public GetThanhToanQRResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        parseStringToJson((result));
    }

    public ArrayList<Bill> getBills() {
        return Bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        Bills = bills;
    }

    public Infos getInfos() {
        return Infos;
    }

    public void setInfos(Infos Infos) {
        this.Infos = Infos;
    }

    public String getQrContent() {
        return QrContent;
    }

    public void setQrContent(String qrContent) {
        QrContent = qrContent;
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
                    if (obj != null) {
                        QrContent = obj.getString("QRContent");
                        //Bills
                        JSONArray billJsonArr = obj.getJSONArray("Bills");
                        for (int i = 0; i < billJsonArr.length(); i++) {
                            JSONObject billObj = billJsonArr.getJSONObject(i);
                            Bill bill = new Bill();
                            bill.setBillId(billObj.getString("BillId"));
                            bill.setPeriod(billObj.getString("Period"));
                            bill.setMoney(billObj.getInt("Money"));
                            Bills.add(bill);
                        }
                        //Infos
                        JSONObject infoObj = obj.getJSONObject("Infos");
                        Infos.setName(infoObj.getString("name"));
                    }
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }else{
            System.out.println("dsdsdsds");
        }
    }
    public class Bill{
        public String BillId;
        public String Period;
        public int Money;

        public String getBillId() {
            return BillId;
        }

        public void setBillId(String billId) {
            BillId = billId;
        }

        public String getPeriod() {
            return Period;
        }

        public void setPeriod(String period) {
            Period = period;
        }

        public int getMoney() {
            return Money;
        }

        public void setMoney(int money) {
            Money = money;
        }

        public String getNote() {
            return Note;
        }

        public void setNote(String note) {
            Note = note;
        }

        public String getInfoEx() {
            return InfoEx;
        }

        public void setInfoEx(String infoEx) {
            InfoEx = infoEx;
        }

        public String Note;
        public String InfoEx;
    }
    public class Infos{
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
