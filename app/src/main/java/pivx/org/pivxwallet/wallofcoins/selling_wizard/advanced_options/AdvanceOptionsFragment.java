package pivx.org.pivxwallet.wallofcoins.selling_wizard.advanced_options;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseActivity;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseFragment;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.models.AddressVo;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.utils.SellingConstants;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.verify_details.VerifySellingDetailsFragment;

/**
 * Created by  on 20-Apr-18.
 */

public class AdvanceOptionsFragment extends SellingBaseFragment implements View.OnClickListener {

    private View rootView;
    private CheckBox chkBox;
    private Button btnCancle, btnSave;
    EditText edtViewMinPayment, edtViewMaxPayment;
    private AddressVo addressVo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_selling_options_dialog, container, false);
            init();
            setListeners();
            setTopbar();
            handleArgs();
            return rootView;
        } else
            return rootView;
    }

    private void init() {

        edtViewMinPayment = (EditText) rootView.findViewById(R.id.edtViewMinPayment);
        edtViewMaxPayment = (EditText) rootView.findViewById(R.id.edtViewMaxPayment);
        btnCancle = (Button) rootView.findViewById(R.id.btnCancle);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        chkBox = (CheckBox) rootView.findViewById(R.id.chkBox);

        btnSave.setText(getString(R.string.action_continue));
    }

    private void setListeners() {
        btnCancle.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void setTopbar() {
        ((SellingBaseActivity) mContext).setTopbarTitle(
                mContext.getString(R.string.title_advanced_options));
    }

    private void handleArgs() {

        if (getArguments() != null) {
            addressVo = (AddressVo)
                    getArguments().getSerializable(SellingConstants.ADDRESS_DETAILS_VO);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSave:
                if (isValidDetails()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SellingConstants.ADDRESS_DETAILS_VO, getSellingDetails());
                    VerifySellingDetailsFragment fragment = new VerifySellingDetailsFragment();
                    fragment.setArguments(bundle);

                    ((SellingBaseActivity) mContext).replaceFragment(fragment, true, true);
                }
                break;
            case R.id.btnCancle:
                showToast("Under Implementation");
                break;


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopbar();
    }

    private boolean isValidDetails() {

        if (edtViewMinPayment.getText().toString().isEmpty()) {
            showToast(getString(R.string.enter_min_payment));
            edtViewMinPayment.requestFocus();
            return false;
        } else if (edtViewMaxPayment.getText().toString().isEmpty()) {
            showToast(getString(R.string.enter_max_payment));
            edtViewMinPayment.requestFocus();
            return false;
        } else if (!chkBox.isChecked()) {
            return false;
        }
        return true;
    }

    private AddressVo getSellingDetails() {

        return addressVo;
    }
}