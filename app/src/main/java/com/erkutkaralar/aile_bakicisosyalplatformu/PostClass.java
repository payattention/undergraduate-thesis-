package com.erkutkaralar.aile_bakicisosyalplatformu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {

    private final ArrayList<String> userName;
    private final ArrayList<String> userSurname;
    private final ArrayList<String> userImage;
    private final ArrayList<String> userAge;
    private final ArrayList<String> userCity;
    private final ArrayList<String> userSchool;
    private final ArrayList<String> userDescription;
    private final ArrayList<String> userMail;
    private final Activity context;


    public PostClass(ArrayList<String> userName, ArrayList<String> userSurname, ArrayList<String> userImage,ArrayList<String> userAge,ArrayList<String> userCity,ArrayList<String> userSchool,ArrayList<String> userDescription,ArrayList<String> userMail, Activity context) {
        super(context,R.layout.customview,userName);
        this.userName = userName;
        this.userSurname = userSurname;
        this.userImage = userImage;
        this.userAge = userAge;
        this.userCity = userCity;
        this.userSchool = userSchool;
        this.userDescription = userDescription;
        this.userMail = userMail;
        this.context = context;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.customview,null,true);

        TextView userNameText = customView.findViewById(R.id.textName);
        TextView userSurnameText = customView.findViewById(R.id.textSurname);
        TextView userAgeText = customView.findViewById(R.id.TextAge);
        TextView userCityText = customView.findViewById(R.id.TextCity);
        TextView userSchoolText = customView.findViewById(R.id.TextSchool);
        TextView userDescriptionText = customView.findViewById(R.id.TextDescription);
        ImageView imageView = customView.findViewById(R.id.imageViewCustomView);
        TextView userEmailText = customView.findViewById(R.id.emailText);


        userNameText.setText(userName.get(position));
        userSurnameText.setText(userSurname.get(position));
        userAgeText.setText(userAge.get(position));
        userCityText.setText(userCity.get(position));
        userSchoolText.setText(userSchool.get(position));
        userDescriptionText.setText(userDescription.get(position));
        userEmailText.setText(userMail.get(position));
        Picasso.get().load(userImage.get(position)).into(imageView);



        return customView;


    }
}