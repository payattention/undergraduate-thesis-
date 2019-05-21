package com.erkutkaralar.aile_bakicisosyalplatformu;

import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowJobs extends AppCompatActivity {

    ListView listView;
    PostClass2 adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> userNameFromFB;
    ArrayList<String> userSurnameFromFB;
    ArrayList<String> userSchoolFromFB;
    ArrayList<String> userAgeFromFB;
    ArrayList<String> userCityFromFB;
    ArrayList<String> userDescriptionFromFB;
    ArrayList<String> jobStartingDateFB;
    ArrayList<String> jobEndingDateFB;
    ArrayList<String> jobGender;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search2,menu);
        MenuItem item = menu.findItem(R.id.action_search2);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                char ch[] = newText.toCharArray();
                for (int i = 0; i < newText.length(); i++) {

                    if (i == 0 && ch[i] != ' ' ||
                            ch[i] != ' ' && ch[i - 1] == ' ') {

                        if (ch[i] >= 'a' && ch[i] <= 'z') {

                            ch[i] = (char)(ch[i] - 'a' + 'A');
                        }
                        if (ch[i] == 'ç'){
                            ch[i] = (char)(ch[i] - 'ç' + 'Ç');
                        }
                    }

                }
                String NewText = new String(ch);

                adapter.clear();
                userSchoolFromFB.clear();
                userSurnameFromFB.clear();
                userAgeFromFB.clear();
                userCityFromFB.clear();
                userDescriptionFromFB.clear();
                jobStartingDateFB.clear();
                jobEndingDateFB.clear();
                jobGender.clear();
                getSearchedDataFromFirebase(NewText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jobs);

        listView = findViewById(R.id.showSearchedJobListView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        userNameFromFB = new ArrayList<String>();
        userSurnameFromFB = new ArrayList<String>();
        userAgeFromFB = new ArrayList<String>();
        userCityFromFB = new ArrayList<String>();
        userSchoolFromFB = new ArrayList<String>();
        userDescriptionFromFB = new ArrayList<String>();
        jobStartingDateFB = new ArrayList<String>();
        jobEndingDateFB = new ArrayList<String>();
        jobGender = new ArrayList<String>();

        adapter = new PostClass2(userNameFromFB, userSurnameFromFB, userAgeFromFB, userCityFromFB, userSchoolFromFB, userDescriptionFromFB,jobStartingDateFB,jobEndingDateFB,jobGender, this);
        listView.setAdapter(adapter);
    }

    public void getSearchedDataFromFirebase(final String searchedCity) {

        Query query = firebaseDatabase.getInstance().getReference("CreateJob").orderByChild("city").equalTo(searchedCity);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();


                        userNameFromFB.add(hashMap.get("name"));
                        userSurnameFromFB.add(hashMap.get("surname"));
                        userAgeFromFB.add(hashMap.get("age"));
                        userCityFromFB.add(hashMap.get("city"));
                        userSchoolFromFB.add(hashMap.get("school"));
                        userDescriptionFromFB.add(hashMap.get("description"));
                        jobStartingDateFB.add(hashMap.get("startingDate"));
                        jobEndingDateFB.add(hashMap.get("endDate"));
                        jobGender.add(hashMap.get("gender"));
                        adapter.notifyDataSetChanged();

                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShowJobs.this, "Hata", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
