package com.example.finduser.data;

public class Contact {
    private final String mDisplayName;
    private final String mPhoneNumber;
    private final String mEmailAddress;

    public Contact(String displayName, String phoneNumber, String emailAddress) {
        mDisplayName = displayName;
        mPhoneNumber = phoneNumber;
        mEmailAddress = emailAddress;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }
}
