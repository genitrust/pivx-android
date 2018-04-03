package pivx.org.pivxwallet.wallofcoins.selling_wizard.contact_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseActivity;

/**
 * Created by  on 03-Apr-18.
 */

public class ContactDetailsFragment extends SellingBaseFragment implements View.OnClickListener {


    private View rootView;
    private Button btnContinue;
    private EditText edtViewMobile, edtViewEmail, edtViewConfirmEmail, edtViewPass;
    private final String TAG = "ContactDetailsFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_selling_contact_details, container, false);
            init();
            setListeners();
            setTopbar();
            return rootView;
        } else
            return rootView;
    }

    private void init() {

        edtViewMobile = (EditText) rootView.findViewById(R.id.edtViewMobile);
        edtViewEmail = (EditText) rootView.findViewById(R.id.edtViewEmail);
        edtViewConfirmEmail = (EditText) rootView.findViewById(R.id.edtViewConfirmEmail);
        edtViewPass = (EditText) rootView.findViewById(R.id.edtViewPass);

        btnContinue = (Button) rootView.findViewById(R.id.btnContinue);
    }

    private void setListeners() {
        btnContinue.setOnClickListener(this);
    }

    private void setTopbar() {

        ((SellingBaseActivity) mContext).setTopbarTitle(
                mContext.getString(R.string.title_contact_details));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnContinue:
                break;
        }
    }

}