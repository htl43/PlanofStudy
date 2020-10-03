package com.example.hieule.planofstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewUsername, textViewOwnerName, textViewStudentID;
    private Button countinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharePreManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }

        textViewUsername = (TextView)findViewById(R.id.UserNameTextDisplay);
        textViewOwnerName = (TextView)findViewById(R.id.NameTextDisplay);
        textViewStudentID = (TextView)findViewById(R.id.StudentIDTextDisplay);

        countinue = (Button)findViewById(R.id.countinueButton);
        countinue.setOnClickListener(this);

        textViewUsername.setText(SharePreManager.getInstance(this).getUserName());
        textViewOwnerName.setText(SharePreManager.getInstance(this).getOwnerName());
        textViewStudentID.setText(SharePreManager.getInstance(this).getStudentID());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                SharePreManager.getInstance(this).logOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menuSetting:
                Toast.makeText(this, "You click setting", Toast.LENGTH_LONG).show();
                break;
        }
        return  true;
    }

    @Override
    public void onClick(View v) {
        if(v == countinue){
            startActivity(new Intent(this, WebViewActivity.class));
        }
    }
}
