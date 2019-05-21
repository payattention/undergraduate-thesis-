package com.erkutkaralar.aile_bakicisosyalplatformu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class anaSayfa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_ana_sayfa);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }

    public void profilIntent (View view){

        Intent intent = new Intent(getApplicationContext(),Profil.class);
        startActivity(intent);

    }

    public void showProfile (View view){

        Intent intent = new Intent(getApplicationContext(),ShowProfile.class);
        startActivity(intent);

    }

    public void showCreateIsIlani (View view){

        Intent intent = new Intent(getApplicationContext(),isIlani.class);
        startActivity(intent);

    }

    public void showJob (View view){

        Intent intent = new Intent(getApplicationContext(),ShowJobs.class);
        startActivity(intent);

    }

}
