package com.example.finduser.contract;

import android.content.Context;
import android.text.Editable;

public interface Contract {
    interface View {
        void hideProgressBar();

        void showProgressBar();

        void setPresenter(Presenter presenter);

        void hideSoftSoftInputFromWindow();

        void displayToast(String toastText);

        Context getApplicationContext();

        void showContactDetailInPopup(String name, String phone, String email);
    }

    interface Presenter {
        void start();

        void end();

        void onButtonClick(Editable text);
    }
}
