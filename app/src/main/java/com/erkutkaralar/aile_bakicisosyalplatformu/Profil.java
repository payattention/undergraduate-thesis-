package com.erkutkaralar.aile_bakicisosyalplatformu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class Profil extends AppCompatActivity {

    ImageView ProfilePhoto;
    Uri selectedImage;
    EditText userNameText;
    EditText userSurnameText;
    EditText cityText;
    EditText ageText;
    EditText schoolText;
    EditText descriptionText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profil);

        ProfilePhoto = findViewById(R.id.imageViewCustomView);
        userNameText = findViewById(R.id.textName);
        userSurnameText = findViewById(R.id.textSurname);
        cityText = findViewById(R.id.TextCity);
        ageText = findViewById(R.id.TextAgeRange);
        schoolText = findViewById(R.id.TextSchoolStatus);
        descriptionText = findViewById(R.id.TextDescription);
        mAuth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getDataFromFirebase();


    }


    public void selectImage(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);

            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            selectedImage = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ProfilePhoto.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }




    public void Save(View view) {

        FirebaseUser user = mAuth.getCurrentUser();

        final String userId = user.getUid();
        final String userMail = user.getEmail();

        final String imageName = "images/" + userId + ".jpg";
        if (selectedImage != null && !selectedImage.equals(Uri.EMPTY)) {
            final StorageReference storageReference = mStorageRef.child(imageName);
            storageReference.putFile(selectedImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            final String downloadURL = uri.toString();


                            final String userName = userNameText.getText().toString();
                            final String userSurname = userSurnameText.getText().toString();
                            final String city = cityText.getText().toString();
                            final String age = ageText.getText().toString();
                            final String school = schoolText.getText().toString();
                            final String description = descriptionText.getText().toString();


                            myRef = FirebaseDatabase.getInstance().getReference();
                            final DatabaseReference userIdRef = myRef.child("profile").child(userId);
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
                                            userIdRef.child("email").setValue(userMail);
                                            userIdRef.child("downloadurl").setValue(downloadURL);
                                            Toast.makeText(Profil.this, "Güncellendi", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(Intent.ACTION_INSERT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            startActivityForResult(intent, 2);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        myRef.child("profile").child(userId).child("name").setValue(userName);
                                        myRef.child("profile").child(userId).child("surname").setValue(userSurname);
                                        myRef.child("profile").child(userId).child("city").setValue(city);
                                        myRef.child("profile").child(userId).child("age").setValue(age);
                                        myRef.child("profile").child(userId).child("school").setValue(school);
                                        myRef.child("profile").child(userId).child("description").setValue(description);
                                        myRef.child("profile").child(userId).child("email").setValue(userMail);
                                        myRef.child("profile").child(userId).child("downloadurl").setValue(downloadURL);

                                        Toast.makeText(Profil.this, "Saved", Toast.LENGTH_LONG).show();


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(Profil.this, "Hata", Toast.LENGTH_SHORT).show();
                                }
                            };
                            userIdRef.addListenerForSingleValueEvent(eventListener);
                        }


                    });


                }


            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profil.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {


            final String userName = userNameText.getText().toString();
            final String userSurname = userSurnameText.getText().toString();
            final String city = cityText.getText().toString();
            final String age = ageText.getText().toString();
            final String school = schoolText.getText().toString();
            final String description = descriptionText.getText().toString();


            myRef = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference userIdRef = myRef.child("profile").child(userId);
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


                            Toast.makeText(Profil.this, "Güncellendi", Toast.LENGTH_LONG).show();




                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {

                        myRef.child("profile").child(userId).child("name").setValue(userName);
                        myRef.child("profile").child(userId).child("surname").setValue(userSurname);
                        myRef.child("profile").child(userId).child("city").setValue(city);
                        myRef.child("profile").child(userId).child("age").setValue(age);
                        myRef.child("profile").child(userId).child("school").setValue(school);
                        myRef.child("profile").child(userId).child("description").setValue(description);


                        Toast.makeText(Profil.this, "Saved", Toast.LENGTH_LONG).show();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Profil.this, "Hata", Toast.LENGTH_SHORT).show();
                }
            };
            userIdRef.addListenerForSingleValueEvent(eventListener);


        }




    }

    public void getDataFromFirebase(){

        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference newReferences = firebaseDatabase.getReference("profile").child(userId);
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
                     String urlImageUser = hashMap.get("downloadurl");

                     try {
                         URL url = new URL(urlImageUser);
                         Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                         ProfilePhoto.setImageBitmap(bmp);


                         userNameText.setText(nameUser);
                         userSurnameText.setText(surnameUser);
                         ageText.setText(ageUser);
                         cityText.setText(cityUser);
                         schoolText.setText(schoolUser);
                         descriptionText.setText(descriptionUser);

                     }catch(Exception e){
                         e.printStackTrace();
                     }



                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profil.this, "Hata", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
