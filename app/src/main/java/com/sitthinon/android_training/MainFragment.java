package com.sitthinon.android_training;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register
        registerController();

//        Login Controller
        loginController();

    }//Main Mathod

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        final EditText userEditText = getView().findViewById(R.id.edtUser);
        final EditText passwordEditText = getView().findViewById(R.id.edtPassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());

                if (user.isEmpty() || password.isEmpty()) {
                    myAlert.normalDialog("Have Space", "Please Fill All Blank");
                } else {

                    try {

                        GetUserWhereUserThread getUserWhereUserThread = new GetUserWhereUserThread(getActivity());
                        getUserWhereUserThread.execute(user);
                        String json = getUserWhereUserThread.get();
                        Log.d("24FebV1", "json ==>" + json);

                        if (json.equals("null")) {
                            myAlert.normalDialog("User False","No User In Database");
                        } else {
                            JSONArray jsonArray = new JSONArray(json);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            if (password.equals(jsonObject.getString("Password"))) {
                                Toast.makeText(getActivity(), "Welcome" + jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();
                            } else {
                                myAlert.normalDialog("Password","Password False");
                            }

                        } //if

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Replace Fragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


}//Main Class
