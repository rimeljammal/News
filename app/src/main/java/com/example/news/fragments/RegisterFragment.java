package com.example.news.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.news.R;
import com.example.news.api.LoginApiManager;
import com.example.news.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private RegisterFragmentListener listener;

    private TextInputLayout nameHolder, emailHolder, passwordHolder;
    private EditText nameText, emailText, passwordText;
    private ProgressBar progressBar;
    private Button registerButton;
    private Spinner favSpinner;

    private ArrayAdapter<CharSequence> types_adapter;


    private LoginApiManager loginApiManager;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginApiManager = LoginApiManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nameHolder = view.findViewById(R.id.name_holder);
        emailHolder = view.findViewById(R.id.email_holder);
        passwordHolder = view.findViewById(R.id.password_holder);

        nameText = view.findViewById(R.id.name);
        emailText = view.findViewById(R.id.email);
        passwordText = view.findViewById(R.id.password);
        favSpinner = view.findViewById(R.id.categorySpinner);

        types_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.news_types, android.R.layout.simple_spinner_item);
        types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favSpinner.setAdapter(types_adapter);

        progressBar = view.findViewById(R.id.progressBar);

        registerButton = view.findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragmentListener) {
            listener = (RegisterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RegisterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void attemptRegister() {
        String name = nameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        ArrayList<String> favorite = new ArrayList<>();

        int type = favSpinner.getSelectedItemPosition();

        if (type == 0)
            favorite.add("entertainment");

        if (type == 1)
            favorite.add("sports");

        if (type == 2)
            favorite.add("general");

        if (type == 3)
            favorite.add("health");

        if (type == 4)
            favorite.add("science");

        if (type == 5)
            favorite.add("technology");

        if (type == 6)
            favorite.add("business");


        boolean flag = true;

        if (TextUtils.isEmpty(name)) {
            nameHolder.setError("name field is required");
            flag = false;
        } else {
            nameHolder.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailHolder.setError("email field is required");
            flag = false;
        } else {
            emailHolder.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordHolder.setError("password field is required");
            flag = false;
        } else {
            passwordHolder.setError(null);
        }

        if (flag) {
            showProgressBar();
            User registerUser = new User(name, email, password, favorite);
            loginApiManager
                    .register(registerUser)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            hideProgressBar();
                            if (response.isSuccessful()) {
                                listener.onRegisterSuccess();
                            } else {
                                listener.onRegisterFailure();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            hideProgressBar();
                            listener.onRegisterFailure();
                        }
                    });
        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public interface RegisterFragmentListener {
        void onRegisterSuccess();

        void onRegisterFailure();
    }

}
