package pivx.org.pivxwallet.wallofcoins.selling_wizard.phone_list;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseActivity;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.adapters.PhoneListAdapter;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.contact_details.ContactDetailsFragment;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.contact_details.SellingBaseFragment;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.models.PhoneListVO;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.utils.BuyDashPhoneListPref;

/**
 * Created by  on 19-Mar-18.
 */

public class PhoneListFragment extends SellingBaseFragment implements View.OnClickListener {

    private final String TAG = "OrderHistoryFragment";
    private View rootView;
    private RecyclerView recyclerViewPhoneList;
    private Button btnSignUp, btnExistingSignIn;
    private PhoneListFragment fragment;
    private TextView txtViewNoData;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_selling_phone_list, container, false);
        init();
        setListeners();
        setPhoneList();
        return rootView;
    }

    private void init() {
        fragment = this;
        recyclerViewPhoneList = (RecyclerView) rootView.findViewById(R.id.recyclerViewPhoneList);
        btnSignUp = (Button) rootView.findViewById(R.id.btnSignUp);
        btnExistingSignIn = (Button) rootView.findViewById(R.id.btnExistingSignIn);
        txtViewNoData = (TextView) rootView.findViewById(R.id.txtViewNoData);
        recyclerViewPhoneList.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void setListeners() {
        btnExistingSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private void setPhoneList() {
        BuyDashPhoneListPref credentialsPref =
                new BuyDashPhoneListPref(PreferenceManager.getDefaultSharedPreferences(mContext));

        ArrayList<PhoneListVO> phoneListVOS = credentialsPref.getStoredPhoneList();

        HashSet<PhoneListVO> hashSet = new HashSet<>();
        hashSet.addAll(phoneListVOS);
        phoneListVOS.clear();
        phoneListVOS.addAll(hashSet);

        if (phoneListVOS != null & phoneListVOS.size() > 0) {
            recyclerViewPhoneList.setAdapter(new PhoneListAdapter(mContext, credentialsPref.getStoredPhoneList(), fragment));
            txtViewNoData.setVisibility(View.GONE);
        } else
            txtViewNoData.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExistingSignIn:
            /*    Bundle bundle = new Bundle();
                bundle.putString(WOCConstants.SCREEN_TYPE, "PhoneListFragment");
                EmailAndPhoneFragment phoneFragment = new EmailAndPhoneFragment();
                phoneFragment.setArguments(bundle);

                ((BuyDashBaseActivity) mContext).replaceFragment(phoneFragment, true, true);*/
                break;
            case R.id.btnSignUp:
                ((SellingBaseActivity) mContext).replaceFragment(new ContactDetailsFragment(),
                        true, true);
                break;
        }
    }


}
