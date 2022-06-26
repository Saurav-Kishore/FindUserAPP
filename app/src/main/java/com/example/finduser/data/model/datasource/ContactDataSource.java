package com.example.finduser.data.model.datasource;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.example.finduser.data.Contact;

import java.util.concurrent.TimeUnit;

public class ContactDataSource {

    private final ContentResolver mContentResolver;

    public ContactDataSource(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public Contact getContact(String displayName) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            // do nothing
        }
        String name = null, phone = null, email = null;
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        };
        String selection = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";
        String[] selectionArgs = {displayName};
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " COLLATE LOCALIZED ASC";
        Cursor cursor1 = mContentResolver.query(contactsUri, projection, selection, selectionArgs, sortOrder);
        if (cursor1 != null && cursor1.moveToFirst()) {
            int indexId = cursor1.getColumnIndex(ContactsContract.Contacts._ID);
            int indexName = cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
            name = cursor1.getString(indexName);
            String contactId = cursor1.getString(indexId);
            Uri dataUri = ContactsContract.Data.CONTENT_URI;
            String[] dataProjection = new String[]{
                    ContactsContract.Data.CONTACT_ID,
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.Data.DATA1
            };
            String dataSelection = ContactsContract.Data.CONTACT_ID + " = ?";
            String[] dataSelectionArgs = {contactId};
            Cursor cursor2 = mContentResolver.query(dataUri, dataProjection, dataSelection, dataSelectionArgs, null);
            if (cursor2 != null && cursor2.moveToFirst()) {
                do {
                    int indexMimeType = cursor2.getColumnIndex(ContactsContract.Data.MIMETYPE);
                    int indexData1 = cursor2.getColumnIndex(ContactsContract.Data.DATA1);
                    String mimeType = cursor2.getString(indexMimeType);
                    switch (mimeType) {
                        case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                            phone = cursor2.getString(indexData1);
                            break;
                        case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                            email = cursor2.getString(indexData1);
                            break;
                    }
                } while (cursor2.moveToNext());
            }
            return new Contact(name, phone, email);
        }

        return null;
    }
}
