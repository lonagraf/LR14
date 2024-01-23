package com.example.lar14;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private TextView register;
    private EditText email, password;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        register = view.findViewById(R.id.register);
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view_tag, new RegisterFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button loginBtn = view.findViewById(R.id.loginBtn);
        SharedPreferences preferences = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        if (preferences.getBoolean("remember", false)){
            email.setText(preferences.getString("email",""));
            password.setText(preferences.getString("password", ""));
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return view;
    }

    private void login(){
        String loginEmail = email.getText().toString().trim();
        String loginPassword = password.getText().toString();

        SharedPreferences preferences = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        String storedEmail = preferences.getString("email", "");
        String storedPassword = preferences.getString("password", "");
        if (loginEmail.equals(storedEmail) && loginPassword.equals(storedPassword)){
            Toast.makeText(getContext(), "Вы успешно вошли", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Неверная почта или пароль. Повторите попытку", Toast.LENGTH_SHORT).show();
        }
    }
}