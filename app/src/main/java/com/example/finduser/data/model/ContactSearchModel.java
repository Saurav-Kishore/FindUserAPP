package com.example.finduser.data.model;

import android.content.Context;
import com.example.finduser.data.Contact;
import com.example.finduser.data.model.datasource.ContactDataSource;
import io.reactivex.Single;

public class ContactSearchModel {

    private final ContactDataSource mContactDataSource;

    public ContactSearchModel(Context context) {
        mContactDataSource = new ContactDataSource(context.getContentResolver());
    }

    public Single<Contact> getContact(String displayName) {
        return Single.fromCallable(() -> mContactDataSource.getContact(displayName));
    }

    public void dispose() {

    }
}
