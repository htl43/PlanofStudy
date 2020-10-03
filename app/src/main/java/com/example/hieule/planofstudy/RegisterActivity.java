package com.example.hieule.planofstudy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ET_Name, ET_Username, ET_password, ET_confirmPass, ET_studentID;
    private Button BT_Register;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        ET_Name = (EditText)findViewById(R.id.NameEditText);
        ET_Username = (EditText)findViewById(R.id.UserNameEditText);
        ET_password = (EditText)findViewById(R.id.PasswordEditText);
        ET_confirmPass = (EditText)findViewById(R.id.ConfirmPasswordeditText);
        ET_studentID = (EditText)findViewById(R.id.Student_idEditText);
        textViewLogin = (TextView)findViewById(R.id.LoginTextView);

        BT_Register = (Button)findViewById(R.id.RegisterButton);
        progressDialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Password does not match")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        BT_Register.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }

    private void registerUser(){
        final String name = ET_Name.getText().toString().trim();
        final String username = ET_Username.getText().toString().trim();
        final String password = ET_password.getText().toString().trim();
        final String confirmPass = ET_confirmPass.getText().toString().trim();
        final String student_id = ET_studentID.getText().toString().trim();

        if(password.equals(confirmPass))
        {
            progressDialog.setMessage("Registering user...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("student_id", student_id);
                    params.put("name", name);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
            startActivity(new Intent(this, MainActivity.class));
        }else{
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Confirm Password");
            alertDialog.show();
        }


    }

    @Override
    public void onClick(View v) {
        if(v == BT_Register)
            registerUser();
        if(v == textViewLogin)
            startActivity(new Intent(this, MainActivity.class));

    }
}
