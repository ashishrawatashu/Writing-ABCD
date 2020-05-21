package com.example.abcd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button write_ABC_BT, write_abc_BT, write_123_BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
    }

    private void init() {
        write_123_BT = findViewById(R.id.write_123_BT);
        write_abc_BT = findViewById(R.id.write_abc_BT);
        write_ABC_BT = findViewById(R.id.write_ABC_BT);

        write_abc_BT.setOnClickListener(this);
        write_ABC_BT.setOnClickListener(this);
        write_123_BT.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.write_ABC_BT:
                Constants.Type = "ABC";
                Intent ABC_intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(ABC_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;

            case R.id.write_abc_BT:
                Constants.Type = "abc";
                Intent abc_intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(abc_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;

            case R.id.write_123_BT:
                Constants.Type = "123";
                Intent num_intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(num_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;

        }

    }
}
