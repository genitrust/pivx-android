package pivx.org.pivxwallet.wallofcoins.selling_wizard.utils;

import android.content.SharedPreferences;

/**
 * Created on 13-Mar-18.
 */

public class SellingWizardAddressPref {
    private final SharedPreferences prefs;
    private static final String SELL_PIV_ADDRESS = "addres";

    public SellingWizardAddressPref(final SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public void setSellPivAddress(String address) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SELL_PIV_ADDRESS, address);
        editor.commit();
    }

    public String getSellPivAddress() {
        return prefs.getString(SELL_PIV_ADDRESS, "");
    }

    public void clearSellPivAddress() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
