package pivx.org.pivxwallet.wallofcoins.selling_wizard.price;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingWizardBaseActivity;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingWizardBaseFragment;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.advanced_options.SellingWizardAdvanceOptionsFragment;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.api.RetrofitErrorUtil;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.api.SellingAPIClient;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.models.SellingWizardAddressVo;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.models.SellingWizardMarketsVo;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.utils.SellingConstants;
import pivx.org.pivxwallet.wallofcoins.utils.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 04-Apr-18.
 */

public class SellingWizardPriceFragment extends SellingWizardBaseFragment implements View.OnClickListener {


    private View rootView;
    private Button button_continue;
    private EditText edit_static_price, edit_min_payment, edit_max_payment, edit_seller_price;
    private final String TAG = "PriceFragment";
    private ProgressBar progressBar;
    private SellingWizardAddressVo sellingWizardAddressVo;
    private AppCompatSpinner spinner_primary_market, spinner_secondary_market;
    private AppCompatCheckBox chekbox_dynamic_pricing;
    private LinearLayout layout_static_price;
    private RelativeLayout layout_dynamic_price;
    private ArrayList<SellingWizardMarketsVo> sellingWizardMarketsVoArrayList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_selling_price, container, false);
            init();
            setListeners();
            setTopbar();
            handleArgs();
            return rootView;
        } else
            return rootView;
    }

    private void init() {

        edit_static_price = (EditText) rootView.findViewById(R.id.edit_static_price);
        edit_min_payment = (EditText) rootView.findViewById(R.id.edit_min_payment);
        edit_max_payment = (EditText) rootView.findViewById(R.id.edit_max_payment);
        edit_seller_price = (EditText) rootView.findViewById(R.id.edit_seller_price);
        chekbox_dynamic_pricing = (AppCompatCheckBox) rootView.findViewById(R.id.chekbox_dynamic_pricing);

        spinner_primary_market = (AppCompatSpinner) rootView.findViewById(R.id.spinner_primary_market);
        spinner_secondary_market = (AppCompatSpinner) rootView.findViewById(R.id.spinner_secondary_market);

        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        layout_static_price = (LinearLayout) rootView.findViewById(R.id.layout_static_price);
        layout_dynamic_price = (RelativeLayout) rootView.findViewById(R.id.layout_dynamic_price);

        sellingWizardMarketsVoArrayList = new ArrayList<>();

    }

    private void setListeners() {
        button_continue.setOnClickListener(this);
        chekbox_dynamic_pricing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (sellingWizardMarketsVoArrayList.size() == 0)
                        getMarkes();
                    layout_static_price.setVisibility(View.GONE);
                    layout_dynamic_price.setVisibility(View.VISIBLE);
                } else {
                    layout_dynamic_price.setVisibility(View.GONE);
                    layout_static_price.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setTopbar() {

        ((SellingWizardBaseActivity) mContext).setTopbarTitle(
                mContext.getString(R.string.title_price));
    }

    private void handleArgs() {

        if (getArguments() != null) {
            sellingWizardAddressVo = (SellingWizardAddressVo)
                    getArguments().getSerializable(SellingConstants.ARGUMENT_ADDRESS_DETAILS_VO);
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
            case R.id.button_continue:
                //if (chkBoxdynamicPricing.isChecked()) {
                    /*if (isValidDetails()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SellingConstants.ADDRESS_DETAILS_VO, getSellingDetails());
                        VerifySellingDetailsFragment fragment = new VerifySellingDetailsFragment();
                        fragment.setArguments(bundle);

                        ((SellingBaseActivity) mContext).replaceFragment(fragment, true, true);
                    }*/
                // if (isValidDetails()) {

                if (isValid()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SellingConstants.ARGUMENT_ADDRESS_DETAILS_VO, getSellingDetails());
                    SellingWizardAdvanceOptionsFragment fragment = new SellingWizardAdvanceOptionsFragment();
                    fragment.setArguments(bundle);

                    ((SellingWizardBaseActivity) mContext).replaceFragment(fragment, true, true);
                }

                //}

                // }

                break;
        }
    }

    private boolean isValid() {
        if (edit_static_price.getText().toString().trim().isEmpty()) {
            showToast(getString(R.string.enter_price));
            edit_static_price.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isValidDetails() {

        if (chekbox_dynamic_pricing.isChecked()) {
            if (spinner_primary_market.getSelectedItemPosition() == 0) {
                showToast(getString(R.string.primary_empty));
                return false;
            } else if (spinner_secondary_market.getSelectedItemPosition() == 0) {
                showToast(getString(R.string.secondary_empty));
                return false;
            } else if (spinner_primary_market.getSelectedItemPosition() == spinner_secondary_market.getSelectedItemPosition()) {
                showToast(getString(R.string.error_primary_secondry_same));
                return false;
            }
        } else {
            if (edit_static_price.getText().toString().trim().isEmpty()) {
                showToast(getString(R.string.all_field_required));
                edit_static_price.requestFocus();
                return false;
            }
        }

        return true;
    }

    private SellingWizardAddressVo getSellingDetails() {

        sellingWizardAddressVo.setDynamicPrice(true);

        if (chekbox_dynamic_pricing.isChecked()) {
            int priMarketPos, secMarketPos;
            priMarketPos = spinner_primary_market.getSelectedItemPosition();
            secMarketPos = spinner_secondary_market.getSelectedItemPosition();
            sellingWizardAddressVo.setPrimaryMarket(sellingWizardMarketsVoArrayList.get(priMarketPos - 1).getId());
            sellingWizardAddressVo.setSecondaryMarket(sellingWizardMarketsVoArrayList.get(secMarketPos - 1).getId());

            sellingWizardAddressVo.setSellerFee(edit_seller_price.getText().toString().trim());
            sellingWizardAddressVo.setMinPayment(edit_min_payment.getText().toString().trim());
            sellingWizardAddressVo.setMaxPayment(edit_max_payment.getText().toString().trim());

        } else {

            //null other fields
            sellingWizardAddressVo.setPrimaryMarket(null);
            sellingWizardAddressVo.setSecondaryMarket(null);
            sellingWizardAddressVo.setSellerFee(null);
            sellingWizardAddressVo.setMinPayment(null);
            sellingWizardAddressVo.setMaxPayment(null);
        }
        sellingWizardAddressVo.setCurrentPrice(edit_static_price.getText().toString().trim());
        return sellingWizardAddressVo;
    }

    private void getMarkes() {
        if (NetworkUtil.isOnline(mContext)) {
            SellingAPIClient.createService(mContext).getMarkets("PIV", "USD").enqueue(new Callback<ArrayList<SellingWizardMarketsVo>>() {
                @Override
                public void onResponse(Call<ArrayList<SellingWizardMarketsVo>> call, Response<ArrayList<SellingWizardMarketsVo>> response) {

                    if (response.code() == 200) {
                        sellingWizardMarketsVoArrayList = response.body();
                        handleMarkesReponse(sellingWizardMarketsVoArrayList);
                    } else {
                        String error = RetrofitErrorUtil.parseError(response);
                        if (error != null && !error.isEmpty())
                            showToast(error);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<SellingWizardMarketsVo>> call, Throwable t) {
                    Toast.makeText(getContext(), R.string.try_again, Toast.LENGTH_LONG).show();
                }
            });
        } else
            showToast(mContext.getString(R.string.network_not_avaialable));

    }

    private void handleMarkesReponse(ArrayList<SellingWizardMarketsVo> arrayList) {
        ArrayList<String> primaryList = new ArrayList<String>();
        ArrayList<String> secondaryList = new ArrayList<String>();
        primaryList.add(0, "Select Primary market");
        secondaryList.add(0, "Select Secondary market");
        for (SellingWizardMarketsVo sellingWizardMarketsVo : arrayList) {
            primaryList.add(sellingWizardMarketsVo.getLabel());
            secondaryList.add(sellingWizardMarketsVo.getLabel());
        }

        ArrayAdapter<String> primaryAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, primaryList);
        primaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_primary_market.setAdapter(primaryAdapter);

        ArrayAdapter<String> secondaryAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, secondaryList);
        secondaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_secondary_market.setAdapter(secondaryAdapter);
    }
}
