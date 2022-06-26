package com.example.finduser.presenter;

import android.text.Editable;
import com.example.finduser.R;
import com.example.finduser.contract.Contract;
import com.example.finduser.data.Contact;
import com.example.finduser.data.model.ContactSearchModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FindUserPresenter implements Contract.Presenter {
    private final Contract.View mFragment;
    private ContactSearchModel mContactSearchModel;
    private Disposable mDisposable;

    public FindUserPresenter(Contract.View fragment) {
        mFragment = fragment;
    }

    @Override
    public void start() {
        mContactSearchModel =
                new ContactSearchModel(mFragment.getApplicationContext());
    }

    @Override
    public void end() {
        mDisposable.dispose();
        mContactSearchModel.dispose();
    }

    @Override
    public void onButtonClick(Editable text) {
        mFragment.hideSoftSoftInputFromWindow();
        mFragment.showProgressBar();
        searchContact(text);
    }

    private void searchContact(Editable text) {
        if (text == null) {
            mFragment.displayToast(mFragment.getApplicationContext().getString(R.string.contact_not_found));
        } else {
            if (mDisposable != null) {
                mDisposable.dispose();
            }
            Single<Contact> single = mContactSearchModel.getContact(text.toString());
            mDisposable =
                    single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(contact ->
                                    onSuccess(contact), this::onError);
        }
    }

    private void onSuccess(Contact contact) {
        mFragment.hideProgressBar();
        mFragment.showContactDetailInPopup(contact.getDisplayName(), contact.getPhoneNumber(),
                contact.getEmailAddress());
    }

    private void onError(Throwable throwable) {
        mFragment.hideProgressBar();
        mFragment.displayToast(mFragment.getApplicationContext().getString(R.string.contact_not_found));
    }
}
