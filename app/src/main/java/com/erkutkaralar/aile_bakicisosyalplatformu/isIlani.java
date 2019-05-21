package com.erkutkaralar.aile_bakicisosyalplatformu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class isIlani extends AppCompatActivity {

    EditText userNameText;
    EditText userSurnameText;
    EditText cityText;
    EditText ageText;
    EditText schoolText;
    EditText descriptionText;
    EditText TextGender;
    EditText TextStartingD;
    EditText TextEndD;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_ilani);

        userNameText = findViewById(R.id.textName);
        userSurnameText = findViewById(R.id.textSurname);
        cityText = findViewById(R.id.TextCity);
        ageText = findViewById(R.id.TextAgeRange);
        schoolText = findViewById(R.id.TextSchoolStatus);
        descriptionText = findViewById(R.id.TextDescription);
        TextGender = findViewById(R.id.TextGender);
        TextStartingD = findViewById(R.id.TextStartingD);
        TextEndD = findViewById(R.id.TextEndD);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        getDataFromFirebase();
    }

    public void Create(View view){

        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();

        final String userName = userNameText.getText().toString();
        final String userSurname = userSurnameText.getText().toString();
        final String city = cityText.getText().toString();
        final String age = ageText.getText().toString();
        final String school = schoolText.getText().toString();
        final String description = descriptionText.getText().toString();
        final String gender = TextGender.getText().toString();
        final String startingD = TextStartingD.getText().toString();
        final String endD = TextEndD.getText().toString();

        myRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference userIdRef = myRef.child("CreateJob").child(userId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    try {
                        userIdRef.child("name").setValue(userName);
                        userIdRef.child("surname").setValue(userSurname);
                        userIdRef.child("city").setValue(city);
                        userIdRef.child("age").setValue(age);
                        userIdRef.child("school").setValue(school);
                        userIdRef.child("description").setValue(description);
                        userIdRef.child("gender").setValue(gender);
                        userIdRef.child("startingDate").setValue(startingD);
                        userIdRef.child("endDate").setValue(endD);



                        Toast.makeText(isIlani.this, "Güncellendi", Toast.LENGTH_LONG).show();




                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    myRef.child("CreateJob").child(userId).child("name").setValue(userName);
                    myRef.child("CreateJob").child(userId).child("surname").setValue(userSurname);
                    myRef.child("CreateJob").child(userId).child("city").setValue(city);
                    myRef.child("CreateJob").child(userId).child("age").setValue(age);
                    myRef.child("CreateJob").child(userId).child("school").setValue(school);
                    myRef.child("CreateJob").child(userId).child("description").setValue(description);
                    myRef.child("CreateJob").child(userId).child("gender").setValue(gender);
                    myRef.child("CreateJob").child(userId).child("startingDate").setValue(startingD);
                    myRef.child("CreateJob").child(userId).child("endDate").setValue(endD);

                    Toast.makeText(isIlani.this, "Saved", Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(isIlani.this, "Hata", Toast.LENGTH_SHORT).show();
            }
        };
        userIdRef.addListenerForSingleValueEvent(eventListener);
    }

    public void getDataFromFirebase(){

        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference newReferences = firebaseDatabase.getReference("CreateJob").child(userId);
        newReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    Map<String, String> hashMap = (HashMap<String, String>) dataSnapshot.getValue();

                    String nameUser = hashMap.get( "name" );
                    String surnameUser = hashMap.get( "surname" );
                    String ageUser = hashMap.get( "age" );
                    String cityUser = hashMap.get( "city" );
                    String schoolUser = hashMap.get( "school" );
                    String descriptionUser = hashMap.get( "description" );
                    String gender = hashMap.get( "gender" );
                    String startingD = hashMap.get( "startingDate" );
                    String endD = hashMap.get( "endDate" );

                    try {

                        userNameText.setText(nameUser);
                        userSurnameText.setText(surnameUser);
                        ageText.setText(ageUser);
                        cityText.setText(cityUser);
                        schoolText.setText(schoolUser);
                        descriptionText.setText(descriptionUser);
                        TextGender.setText(gender);
                        TextStartingD.setText(startingD);
                        TextEndD.setText(endD);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(isIlani.this, "Hata", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
