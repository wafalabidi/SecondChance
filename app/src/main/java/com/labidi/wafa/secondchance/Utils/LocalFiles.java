package com.labidi.wafa.secondchance.Utils;

import android.content.SharedPreferences;
import android.support.v4.util.ArraySet;

import android.util.Log;

import com.labidi.wafa.secondchance.Entities.User;

import java.util.Set;

/**
 * Created by sofien on 20/12/2017.
 */

public class LocalFiles {
    SharedPreferences sharedPreferences;
    public static final String USER_FILE = "user";

    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String Password = "Password";
    public static final String Mail = "Mail";
    public static final String BirthDate = "BirthDate";
    public static final String Eyes = "Eyes";
    public static final String Size = "Size";
    public static final String Weight = "Weight";
    public static final String Shape = "Shape";
    public static final String SkinColour = "SkinColour";
    public static final String Tobaco = "Tobaco";
    public static final String Drug = "Drug";
    public static final String Work = "Work";
    public static final String Studies = "Studies";
    public static final String Alchool = "Alchool";
    public static final String Hobbies = "Hobbies";
    public static final String Id = "Id";
    public static final String kids = "kids";
    public static final String imgprofile = "imgprofile";
    public static final String imgcouverture = "imgcouverture";

//    public static final String  ;
    public static final String LIKES= "LikesSet" ;

    public LocalFiles(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean insertString(String label, String newText) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(label, newText);
        editor.apply();
        return editor.commit();
    }

    public String getString(String label) {
        return sharedPreferences.getString(label, "");
    }

    public boolean insertInt(String label, int newInt) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(label, newInt);
        editor.apply();
        return editor.commit();
    }

    public int getInt(String label) {
        return sharedPreferences.getInt(label, -1);
    }

    public static void LogOut(SharedPreferences sharedPreferences) {

        sharedPreferences.edit().clear().apply();
    }

    public void logIn(User user) {
        this.insertString(LocalFiles.FirstName, user.getFirstName());
        insertString(LocalFiles.LastName, user.getLastName());
        insertString(LocalFiles.Password, user.getPassword());
        insertString(Mail, user.getMail());
        insertString(BirthDate, user.getBirthDate());
        insertString(Eyes, user.getEyes());
        insertString(Shape, user.getShape());
        insertString(SkinColour, user.getSkinColour());
        insertString(Work, user.getWork());
        insertString(Studies, user.getStudies());
        insertString(Hobbies, user.getHobbies());
        insertInt(Id, user.getId());
        insertString(imgprofile, user.getImg_profile());
        insertString(imgcouverture,user.getImg_couverture());
        setStaticUser(sharedPreferences);
    }

    private static void setStaticUser(SharedPreferences sharedPreferences) {
        User.FirstName = sharedPreferences.getString(LocalFiles.FirstName, "");
        User.LastName = sharedPreferences.getString(LocalFiles.LastName, "");
        User.Password = sharedPreferences.getString(LocalFiles.Password, "");
        User.Mail = sharedPreferences.getString(LocalFiles.Password, "");
        User.BirthDate = sharedPreferences.getString(BirthDate, "");
        User.Eyes = sharedPreferences.getString(Eyes, "");
        User.Shape = sharedPreferences.getString(Shape, "");
        User.SkinColour = sharedPreferences.getString(SkinColour, "");
        User.Work = sharedPreferences.getString(Work, "");
        User.Studies = sharedPreferences.getString(Studies, "");
        User.Hobbies = sharedPreferences.getString(Hobbies, "");
        User.Id = sharedPreferences.getInt(Id, 0);
        User.imgprofile = sharedPreferences.getString(imgprofile, "");
        User.imgcouverture= sharedPreferences.getString(imgcouverture,"");

    }
}
