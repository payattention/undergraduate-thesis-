package com.erkutkaralar.aile_bakicisosyalplatformu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class PostClass2 extends ArrayAdapter<String> {

    private final ArrayList<String> userName;
    private final ArrayList<String> userSurname;
    private final ArrayList<String> userAge;
    private final ArrayList<String> userCity;
    private final ArrayList<String> userSchool;
    private final ArrayList<String> userDescription;
    private final ArrayList<String> startingDate;
    private final ArrayList<String> endDate;
    private final ArrayList<String> gender;

    private final Activity context;


    public PostClass2(ArrayList<String> userName, ArrayList<String> userSurname,ArrayList<String> userAge,ArrayList<String> userCity,ArrayList<String> userSchool,ArrayList<String> userDescription,ArrayList<String> startingDate,ArrayList<String> endDate,ArrayList<String> gender, Activity context) {
        super(context,R.layout.customview2,userName);
        this.userName = userName;
        this.userSurname = userSurname;
        this.userAge = userAge;
        this.userCity = userCity;
        this.userSchool = userSchool;
        this.userDescription = userDescription;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.gender = gender;
        this.context = context;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.customview2,null,true);

        TextView userNameText = customView.findViewById(R.id.textName);
        TextView userSurnameText = customView.findViewById(R.id.textSurname);
        TextView userAgeText = customView.findViewById(R.id.TextAgeRange);
        TextView userCityText = customView.findViewById(R.id.TextCity);
        TextView userSchoolText = customView.findViewById(R.id.TextSchoolStatus);
        TextView userDescriptionText = customView.findViewById(R.id.TextDescription);
        TextView jobStartingDate = customView.findViewById(R.id.TextStartingDate);
        TextView jobEndDate = customView.findViewById(R.id.TextEndDate);
        TextView jobGender = customView.findViewById(R.id.textGender);

        userNameText.setText(userName.get(position));
        userSurnameText.setText(userSurname.get(position));
        userAgeText.setText(userAge.get(position));
        userCityText.setText(userCity.get(position));
        userSchoolText.setText(userSchool.get(position));
        userDescriptionText.setText(userDescription.get(position));
        jobStartingDate.setText(startingDate.get(position));
        jobEndDate.setText(endDate.get(position));
        jobGender.setText(gender.get(position));



        return customView;


    }
}
