package com.example.finduser.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.finduser.R;
import com.example.finduser.contract.Contract;

public class FindUserFragment extends Fragment implements Contract.View {

    private Contract.Presenter mPresenter;
    private View mView;
    private ProgressBar mProgressBar;
    private Button mButton;
    private EditText mEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.find_user_fragment, container, false);
        mView = view;
        mProgressBar = view.findViewById(R.id.progress_bar);
        mButton = view.findViewById(R.id.button);
        mEditText = view.findViewById(R.id.contactName);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
        mButton.setOnClickListener(v -> mPresenter.onButtonClick(mEditText.getText()));
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void hideSoftSoftInputFromWindow() {
        View focusedView = getActivity().getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            focusedView.clearFocus();
        }
    }

    @Override
    public void displayToast(String toastText) {
        Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void showContactDetailInPopup(String name, String phone, String email) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.contact_detail_popup, null, false);
        ((TextView) popupView.findViewById(R.id.name)).setText(name);
        ((TextView) popupView.findViewById(R.id.phone)).setText(phone);
        ((TextView) popupView.findViewById(R.id.email)).setText(email);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.end();
    }
}
