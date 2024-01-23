package com.example.lar14;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class RegisterFragment extends Fragment {

    private EditText emailText, phoneText, birthdayText, passwordText, passwordRepeatText;
    private CheckBox rememberMee;

    public RegisterFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        emailText = view.findViewById(R.id.emailText);
        phoneText = view.findViewById(R.id.phoneText);
        birthdayText = view.findViewById(R.id.birthdayText);
        passwordText = view.findViewById(R.id.passwordText);
        passwordRepeatText = view.findViewById(R.id.passwordRepeatText);
        rememberMee = view.findViewById(R.id.rememberMe);



        Button registerButton = view.findViewById(R.id.registrationBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                {
                    successRegistration();
                }
            }
        });

        phoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ничего не делаем перед изменением текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ничего не делаем при изменении текста
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1 && s.charAt(0) != '+') {
                    // Если введен первый символ и он не '+', добавляем '+'
                    s.insert(0, "+");
                } else if (s.length() > 1 && s.charAt(0) != '+') {
                    // Если пользователь удалил '+', добавляем его обратно
                    s.insert(0, "+");
                }

                if (s.length() >= 2 && s.charAt(1) != '7') {
                    // Если после ввода первого символа следует цифра, добавляем '7'
                    s.insert(1, "7");
                }

                if (s.length() > 2) {

                    if (s.charAt(2) != ' ') {
                        s.insert(2, " ");
                    }
                }

                if (s.length() > 3) {
                    if (s.charAt(3) != '(') {
                        s.insert(3, "(");
                    }
                }

                if (s.length() > 7) {
                    if (s.charAt(7) != ')') {
                        s.insert(7, ")");
                    }
                }

                if (s.length() > 8) {
                    if (s.charAt(8) != ' ') {
                        s.insert(8, " ");
                    }
                }

                if (s.length() > 12 ) {
                    if (s.charAt(12) != '-' ) {
                        s.insert(12, "-");
                    }
                }

                if (s.length() > 15){
                    if (s.charAt(15) != '-'){
                        s.insert(15,"-");
                    }
                }
            }
        });

        birthdayText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ничего не делаем перед изменением текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ничего не делаем при изменении текста
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2 || s.length() == 5) {
                    // Добавляем точку после ввода дня и месяца
                    s.append(".");
                }
            }
        });

        return view;
    }

    private void SaveData(){
        SharedPreferences preferences = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("email", emailText.getText().toString());
        editor.putString("phone", phoneText.getText().toString());
        editor.putString("birthday", birthdayText.getText().toString());
        editor.putString("password", passwordText.getText().toString());
        editor.putBoolean("remember", rememberMee.isChecked());
        editor.apply();
    }

    private boolean isValid(){
        String email = emailText.getText().toString().trim();
        String number = phoneText.getText().toString().trim();
        String dateOfBirth = birthdayText.getText().toString().trim();
        String password = passwordText.getText().toString();
        String repeatPassword = passwordRepeatText.getText().toString();
        boolean rememberMe = rememberMee.isChecked();

        if (!isValidEmail(email)) {
            emailText.setError("Неверный email адрес");
            return false;
        }

        if (!isValidPhoneNumber(number)) {
            phoneText.setError("Неверный номер телефона");
            return false;
        }

        if (!isValidDateOfBirth(dateOfBirth)) {
            birthdayText.setError("Неверная дата рождения");
            return false;
        }

        if (!isValidPassword(password)) {
            passwordText.setError("Неверный пароль. Длина пароля должна быть не менее 8 символов, должна присутстовать хотя бы 1 заглавная буква и 1 цифра");
            return false;
        }

        if (!password.equals(repeatPassword)) {
            passwordRepeatText.setError("Пароли не совпадают");
            return false;
        }

        SaveData();

        return true;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        String domainRegex = "^[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        if (!email.matches(emailRegex)) {
            return false;
        }

        String[] parts = email.split("@");
        if (parts.length == 2 && !parts[1].matches(domainRegex)) {
        }

        return true;
    }

    private boolean isValidPhoneNumber(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }

        String digitsOnly = number.replaceAll("[^0-9]", "");


        if (digitsOnly.length() < 11) {
            return false;
        }

        return true;
    }

    private boolean isValidDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return false;
        }

        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        if (!dateOfBirth.matches(dateRegex)) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try {
            Date dob = sdf.parse(dateOfBirth);
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dob);

            Calendar currentDate = Calendar.getInstance();


            currentDate.add(Calendar.YEAR, -18);

            if (dobCalendar.after(currentDate)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            return false;
        }

        return true;
    }

    private void successRegistration(){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, new LoginFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Toast.makeText(getContext(), "Вы успешно зарегистрировались!", Toast.LENGTH_SHORT).show();
    }

}