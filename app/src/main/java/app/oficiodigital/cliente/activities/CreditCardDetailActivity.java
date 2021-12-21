package app.oficiodigital.cliente.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.contracts.DeleteCreditCardContract;
import app.oficiodigital.cliente.models.CreditCard;
import app.oficiodigital.cliente.notifications.Alert;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import app.oficiodigital.cliente.notifications.Snack;
import app.oficiodigital.cliente.presenters.DeleteCreditCardPresenter;
import app.oficiodigital.cliente.storage.daohandlers.CreditCardDao;
import app.oficiodigital.cliente.utils.NetworkState;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class CreditCardDetailActivity extends BaseActivity implements DeleteCreditCardContract.View{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.titular_name) TextView mTitularName;
    @BindView(R.id.credit_card_number) TextView mCCNumber;
    @BindView(R.id.expire_date) TextView mExpirationDate;
    @BindView(R.id.main_credit_card) CheckBox mMainPayment;

    public static final String CARD_ID = "credit_card_id";

    private CreditCardDao mCreditCardDao;
    private DeleteCreditCardPresenter mDeleteCreditCardPresenter;
    private String mCreditCardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credi_card_detail);
        ButterKnife.bind(this);
        mCreditCardDao = new CreditCardDao(this);

        mDeleteCreditCardPresenter = new DeleteCreditCardPresenter(this, this);
        mCreditCardId = getIntent().getStringExtra(CARD_ID);

        //setupToolbar();
        //setupUI();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        CreditCard card = (CreditCard) mCreditCardDao.getItemByID(mCreditCardId);

        if (card != null){
            mTitularName.setText(card.getName());
            mCCNumber.setText("**** **** **** " + card.getNumber());
            mExpirationDate.setText(card.getExpirationMonth() + " / " + card.getExpirationYear());

            if (card.getMain() != 1) {
                mMainPayment.setChecked(false);
                mMainPayment.setEnabled(true);
            }
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.chevron_left);
        actionBar.setTitle(R.string.credit_card);
    }

    @OnCheckedChanged(R.id.main_credit_card)
    void mainCard(boolean change) {
        if (change) {
            if (NetworkState.isConnectionAvailable(this)) {
                Alert alert = new Alert(this);
                alert.setPositiveListener(getString(android.R.string.ok), (dialogInterface, i) -> {
                    showLoadingMsg(true);
                    mDeleteCreditCardPresenter.doMainCard(mCreditCardId);
                });
                alert.setNegativeListener(getString(android.R.string.cancel), (dialogInterface, i) -> {
                    alert.close();
                    mMainPayment.setChecked(false);
                });
                alert.fire(getString(R.string.do_main_card));
            } else
                Snack.show(findViewById(android.R.id.content), getString(R.string.check_internet_connection));
        }
    }

    @OnClick(R.id.delete_credit_card)
    void deleteCrediCard() {
        if (NetworkState.isConnectionAvailable(this)){
            LoadingDialog.show(this, getString(R.string.removing_credit_card));
            mDeleteCreditCardPresenter.deleteCreditCard(mCreditCardId);
        } else
            Snack.show(findViewById(android.R.id.content), getString(R.string.check_internet_connection));

    }

    @Override
    public void onCardMadeMain() {
        showLoadingMsg(false);
        Snack.show(findViewById(android.R.id.content), getString(R.string.main_credit_card));
    }

    @Override
    public void onMainCardError(String error) {
        showLoadingMsg(false);
        mMainPayment.setEnabled(false);
        Snack.show(findViewById(android.R.id.content), error);
    }

    @Override
    public void onCardDeleted() {
        LoadingDialog.dismiss();
        Toast.makeText(this, R.string.credit_card_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDeleteCardError(String error) {
        LoadingDialog.dismiss();
        Snack.show(findViewById(android.R.id.content), error);
    }

}
