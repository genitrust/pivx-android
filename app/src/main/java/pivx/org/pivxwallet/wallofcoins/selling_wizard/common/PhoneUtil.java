package pivx.org.pivxwallet.wallofcoins.selling_wizard.common;

import android.util.Log;

import java.util.ArrayList;

import pivx.org.pivxwallet.wallofcoins.selling_wizard.models.PhoneListVO;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.storage.SharedPreferenceUtil;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.utils.ObjectSerializer;

/**
 * Created by  on 04-Apr-18.
 */

public class PhoneUtil {

    private static final String CREDENTIALS_LIST = "credentials_list";

    public static void addPhone(String phone, String deviceId) {
        ArrayList<PhoneListVO> voArrayList;

        try {
            voArrayList = (ArrayList<PhoneListVO>) ObjectSerializer.deserialize(
                    SharedPreferenceUtil.getString(CREDENTIALS_LIST,
                    ObjectSerializer.serialize(new ArrayList())));
            PhoneListVO createHoldResp = new PhoneListVO();
            createHoldResp.setDeviceId(deviceId);
            createHoldResp.setPhoneNumber(phone);

            voArrayList.add(createHoldResp);


            SharedPreferenceUtil.putValue(CREDENTIALS_LIST,
                    ObjectSerializer.serialize(voArrayList));


            for (PhoneListVO vo : voArrayList) {
                Log.e("Auth id list", vo.getDeviceId());
                Log.e("phone no list", vo.getPhoneNumber());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<PhoneListVO> getStoredPhoneList() {
        ArrayList<PhoneListVO> voArrayList = new ArrayList<>();

        try {
            voArrayList = (ArrayList) ObjectSerializer.deserialize(
                    SharedPreferenceUtil.getString(CREDENTIALS_LIST,
                    ObjectSerializer.serialize(new ArrayList())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voArrayList;
    }

    public String getDeviceIdFromPhone(String phone) {
        String deviceId = "";
        ArrayList<PhoneListVO> voArrayList;

        try {
            voArrayList = (ArrayList<PhoneListVO>) ObjectSerializer.
                    deserialize(SharedPreferenceUtil.getString(CREDENTIALS_LIST,
                    ObjectSerializer.serialize(new ArrayList())));

            for (PhoneListVO vo : voArrayList) {
                Log.e("Stored phone",vo.getPhoneNumber()+"---"+"Stored deviceId"+vo.getDeviceId());
                if (vo.getPhoneNumber().equalsIgnoreCase(phone)) {
                    deviceId = vo.getDeviceId();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }
}