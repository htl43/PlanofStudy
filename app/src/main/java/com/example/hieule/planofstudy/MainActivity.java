package com.example.hieule.planofstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ET_Name, ET_Pass;
    private Button login, register;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ET_Name = (EditText)findViewById(R.id.UserNameEditText);
        ET_Pass = (EditText)findViewById(R.id.PasswordEditText);
        login = (Button)findViewById(R.id.LoginButton);
        register = (Button)findViewById(R.id.RegisterButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    private void userLogin(){

        final String username = ET_Name.getText().toString().trim();
        final String password = ET_Pass.getText().toString().trim();

        progressDialog.show();


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharePreManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getString("student_id"),
                                                obj.getString("username"),
                                                obj.getString("name")
                                        );
                                Toast.makeText(getApplicationContext(),
                                        "User Login Successful",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onClick(View v) {
        if(v == login){
            userLogin();

        }
        if(v == register){
            Intent intent =  new Intent(MainActivity.this, RegisterActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
