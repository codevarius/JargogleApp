package com.kshv.example.jargogle_app.ui.main;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.kshv.example.jargogle_app.R;
import com.kshv.example.jargogle_app.model.JargogleDataProvider;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private static LoginFragment mFragment;
    public static String currentUsr;
    private String userToVerify,passwdToVerify,userToCreate,passwdToAssign;

    public static LoginFragment newInstance() {
        return mFragment == null ? new LoginFragment() : mFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mFragment = this;

        view.findViewById(R.id.login_button).setOnClickListener(v -> {
            userToVerify = ((EditText) view.findViewById(R.id.login_text)).getText().toString();
            passwdToVerify = ((EditText) view.findViewById(R.id.passwd_text)).getText().toString();

            if (JargogleDataProvider.getInstance(getContext()).verifyUser(userToVerify, passwdToVerify)) {
                LoginFragment.currentUsr = userToVerify;
                Objects.requireNonNull(getFragmentManager()).beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow();
            } else {
                Toast toast = Toast.makeText(getContext(),
                        R.string.wrong_passwd_or_user, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });

        view.findViewById(R.id.new_usr_button).setOnClickListener(v -> {
            userToCreate = ((EditText) view.findViewById(R.id.login_text)).getText().toString();
            passwdToAssign = ((EditText) view.findViewById(R.id.passwd_text)).getText().toString();

            if (JargogleDataProvider.getInstance(getContext()).checkUserExists(userToCreate)) {
                Toast toast = Toast.makeText(getContext(),
                        R.string.user_exists, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            } else {
                LoginFragment.currentUsr = userToCreate;
                JargogleDataProvider.getInstance(getContext()).createNewUsr(userToCreate, passwdToAssign);
                JargogleDataProvider.getInstance(getContext()).insertNewGradient(new String[]{"#000000", "#000000"});
                Objects.requireNonNull(getFragmentManager()).beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow();
            }
        });

        return view;
    }

}
