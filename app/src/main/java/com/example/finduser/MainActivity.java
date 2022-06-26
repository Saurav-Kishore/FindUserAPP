package com.example.finduser;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.finduser.contract.Contract;
import com.example.finduser.presenter.FindUserPresenter;
import com.example.finduser.view.FindUserFragment;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "FindUserFragment";
    private FindUserFragment mFindUserFragment;
    private Contract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFragmentAndPresenter();
    }

    private void createFragmentAndPresenter() {
        mFindUserFragment = (FindUserFragment) getFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mFindUserFragment == null) {
            mFindUserFragment = new FindUserFragment();
            fragmentTransaction.add(R.id.find_user_fragment_container, mFindUserFragment, FRAGMENT_TAG);
        }
        mPresenter = createPresenter(mFindUserFragment);
        mFindUserFragment.setPresenter(mPresenter);
        fragmentTransaction.commit();
    }

    private Contract.Presenter createPresenter(Contract.View fragment) {
        return new FindUserPresenter(fragment);
    }

    private Fragment getFragment() {
        return getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

}