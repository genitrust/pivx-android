package pivx.org.pivxwallet.wallofcoins.selling_wizard.instruction;

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
import pivx.org.pivxwallet.wallofcoins.selling_wizard.SellingBaseFragment;

/**
 * Created by  on 11-Apr-18.
 */

public class InstructionFragment extends SellingBaseFragment implements View.OnClickListener {

    private View rootView;
    private EditText edtViewBankName, edtViewNewPass, edtViewAccNum, edtViewPivAvail, edtViewCurrRate;
    private Button btnEditRate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_selling_instruction, container, false);
            init();
            setListeners();
            setTopbar();
            return rootView;
        } else
            return rootView;
    }

    private void init() {

        edtViewBankName = (EditText) rootView.findViewById(R.id.edtViewBankName);
        edtViewNewPass = (EditText) rootView.findViewById(R.id.edtViewNewPass);
        edtViewAccNum = (EditText) rootView.findViewById(R.id.edtViewAccNum);
        edtViewPivAvail = (EditText) rootView.findViewById(R.id.edtViewPivAvail);
        edtViewCurrRate = (EditText) rootView.findViewById(R.id.edtViewCurrRate);


        btnEditRate = (Button) rootView.findViewById(R.id.btnEditRate);


    }

    private void setListeners() {
        btnEditRate.setOnClickListener(this);

    }

    private void setTopbar() {
        ((SellingBaseActivity) mContext).setTopbarTitle(
                mContext.getString(R.string.title_selling));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnContinue:

                break;
        }
    }
}
