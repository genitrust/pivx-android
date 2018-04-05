package pivx.org.pivxwallet.wallofcoins.selling_wizard.verify_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.HashMap;

import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseActivity;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseFragment;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.api.RetrofitErrorUtil;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.api.SellingAPIClient;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.api.SellingApiConstants;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.models.AddressVo;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.models.SendVerificationRespVo;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.utils.SellingConstants;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.utils.WOCLogUtil;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.verification_otp.VerifycationCodeFragment;
import pivx.org.pivxwallet.wallofcoins.utils.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by  on 05-Apr-18.
 */

public class VerifySellingDetailsFragment extends SellingBaseFragment implements View.OnClickListener {
    private View rootView;
    private Button btnContinue;
    private ProgressBar progressBar;
    private EditText edtViewAcc, edtViewPrice, edtViewEmail, edtViewPhone;
    private AddressVo addressVo;
    private String addressId, phone;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_selling_verify_details, container, false);
            init();
            setListeners();
            setTopbar();
            handleArgs();
            return rootView;
        } else
            return rootView;
    }

    private void init() {
        btnContinue = (Button) rootView.findViewById(R.id.btnContinue);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        edtViewAcc = (EditText) rootView.findViewById(R.id.edtViewAcc);
        edtViewPrice = (EditText) rootView.findViewById(R.id.edtViewPrice);
        edtViewEmail = (EditText) rootView.findViewById(R.id.edtViewEmail);
        edtViewPhone = (EditText) rootView.findViewById(R.id.edtViewPhone);
    }

    private void setListeners() {
        btnContinue.setOnClickListener(this);
    }

    private void setTopbar() {

        ((SellingBaseActivity) mContext).setTopbarTitle(
                mContext.getString(R.string.title_verify_selling_details));
    }

    private void handleArgs() {

        if (getArguments() != null) {
            addressVo = (AddressVo)
                    getArguments().getSerializable(SellingConstants.ADDRESS_DETAILS_VO);
            phone = addressVo.getNumber();
            edtViewAcc.setText(addressVo.getNumber());
            edtViewPrice.setText(addressVo.getCurrentPrice());
            edtViewEmail.setText(addressVo.getEmail());
            edtViewPhone.setText(addressVo.getPhone());
            addressVo.setUserEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopbar();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnContinue:
                createAddress();
                break;
        }
    }

    private void createAddress() {
        if (NetworkUtil.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            SellingAPIClient.createService(interceptor, mContext).createAddress(addressVo).
                    enqueue(new Callback<AddressVo>() {
                        @Override
                        public void onResponse(Call<AddressVo> call, Response<AddressVo> response) {
                            progressBar.setVisibility(View.GONE);

                            if (response.code() == 200) {
                                AddressVo addressVo = response.body();
                                addressId = addressVo.getId();
                                WOCLogUtil.showLogError("Address Id:", addressVo.getId());
                                sendVerificationCode(addressVo.getPhone(), addressVo.getId());
                            } else {
                                String error = RetrofitErrorUtil.parseError(response);
                                if (error != null && !error.isEmpty())
                                    showToast(error);
                            }

                        }

                        @Override
                        public void onFailure(Call<AddressVo> call, Throwable t) {
                            showToast(t.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    });

        } else
            showToast(mContext.getString(R.string.network_not_avaialable));
    }

    private void sendVerificationCode(String phone, String addId) {
        if (NetworkUtil.isOnline(mContext)) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(SellingApiConstants.KEY_PUBLISHER_ID, SellingApiConstants.WALLOFCOINS_PUBLISHER_ID);
            hashMap.put(SellingApiConstants.KEY_PHONE, phone);
            hashMap.put(SellingApiConstants.AD_ID, addId);

            progressBar.setVisibility(View.VISIBLE);
            SellingAPIClient.createService(interceptor, mContext).sendVerificationCode(hashMap).
                    enqueue(new Callback<SendVerificationRespVo>() {
                        @Override
                        public void onResponse(Call<SendVerificationRespVo> call, Response<SendVerificationRespVo> response) {
                            progressBar.setVisibility(View.GONE);

                            if (response.code() == 200) {
                                navigateToCodeScreen(response.body().__CASH_CODE);

                            } else {
                                String error = RetrofitErrorUtil.parseError(response);
                                if (error != null && !error.isEmpty())
                                    showToast(error);
                            }

                        }

                        @Override
                        public void onFailure(Call<SendVerificationRespVo> call, Throwable t) {
                            showToast(t.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    });

        } else
            showToast(mContext.getString(R.string.network_not_avaialable));
    }

    private void navigateToCodeScreen(String code) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SellingConstants.VERIFICATION_CODE, code);
        bundle.putSerializable(SellingConstants.PHONE_NUMBER, phone);
        bundle.putSerializable(SellingConstants.ADDRESS_ID, addressId);
        VerifycationCodeFragment fragment = new VerifycationCodeFragment();
        fragment.setArguments(bundle);

        ((SellingBaseActivity) mContext).replaceFragment(fragment, true, true);
    }
}
