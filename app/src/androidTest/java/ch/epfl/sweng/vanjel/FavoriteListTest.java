package ch.epfl.sweng.vanjel;


import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;
import ch.epfl.sweng.vanjel.favoriteList.PatientFavoriteListActivity;
import ch.epfl.sweng.vanjel.model.Doctor;
import ch.epfl.sweng.vanjel.model.DoctorActivity;
import ch.epfl.sweng.vanjel.model.Gender;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FavoriteListTest {

    private String doctor1ID = "doctorid1";
    private Doctor defDoctor1 = new Doctor("doctor1@test.ch", "fn_dtest1", "ln_dtest1", "11/11/2011", "street_dtest1", "11", "city_dtest1", "country_dtest1", Gender.Male, DoctorActivity.Dentist);

    private String doctor2ID = "doctorid2";
    private Doctor defDoctor2 =  new Doctor("doctor2@test.ch", "fn_dtest2", "ln_dtest2", "11/11/2012", "street_dtest2", "12", "city_dtest2", "country_dtest2", Gender.Female, DoctorActivity.Ophthalmologist);

    @Rule
    public final IntentsTestRule<PatientFavoriteListActivity> mActivityRule =
            new IntentsTestRule<>(PatientFavoriteListActivity.class);

    @Test
    public void oneElementTest(){
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        l.nuke();
        l.save(defDoctor1, doctor1ID);
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(new Intent());
        assertEquals(1, mActivityRule.getActivity().getAdapterCount());
        l.nuke();
    }

    @Test
    public void twoElementTest(){
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        l.nuke();
        l.save(defDoctor1, doctor1ID);
        l.save(defDoctor2, doctor2ID);
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(new Intent());
        assertEquals(2, mActivityRule.getActivity().getAdapterCount());
        l.nuke();
    }

    @Test
    public void twoElementAndDeleteOne(){
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        l.nuke();
        l.save(defDoctor1, doctor1ID);
        l.save(defDoctor2, doctor2ID);
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(new Intent());
        assertEquals(2, mActivityRule.getActivity().getAdapterCount());
        l.delete(defDoctor2, doctor2ID);
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(new Intent());
        assertEquals(1, mActivityRule.getActivity().getAdapterCount());
    }
}
