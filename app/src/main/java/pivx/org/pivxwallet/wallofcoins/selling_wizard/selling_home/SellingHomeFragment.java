package pivx.org.pivxwallet.wallofcoins.selling_wizard.selling_home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseActivity;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.contact_details.SellingBaseFragment;
import pivx.org.pivxwallet.wallofcoins.selling_wizard.phone_list.PhoneListFragment;

/**
 * Created by  on 03-Apr-18.
 */

public class SellingHomeFragment extends SellingBaseFragment implements View.OnClickListener {


    private View rootView;
    private Button btnSignHere;
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
            rootView = inflater.inflate(R.layout.layout_selling_home, container, false);
            init();
            setListeners();
            setTopbar();
            return rootView;
        } else
            return rootView;
    }

    private void init() {

        btnSignHere = (Button) rootView.findViewById(R.id.btnSignHere);
    }

    private void setListeners() {
        btnSignHere.setOnClickListener(this);
    }

    private void setTopbar() {

        ((SellingBaseActivity) mContext).setTopbarTitle(
                mContext.getString(R.string.title_selling));
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopbar();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSignHere:
                ((SellingBaseActivity) mContext).replaceFragment(new PhoneListFragment(),
                        true, true);
                break;
        }
    }

}