package ch.epfl.sweng.vanjel.favoriteList;


import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;
import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.models.DoctorActivity;
import ch.epfl.sweng.vanjel.models.Gender;

import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FavoriteListTest {

    private final String doctor1ID = "doctorid1";
    private final Doctor defDoctor1 = new Doctor("doctor1@test.ch", "fn_dtest1", "ln_dtest1", "11/11/2011", "street_dtest1", "11", "city_dtest1", "country_dtest1", Gender.Male, DoctorActivity.Dentist);

    private final String doctor2ID = "doctorid2";
    private final Doctor defDoctor2 =  new Doctor("doctor2@test.ch", "fn_dtest2", "ln_dtest2", "11/11/2012", "street_dtest2", "12", "city_dtest2", "country_dtest2", Gender.Female, DoctorActivity.Ophthalmologist);

    @Rule
    public final IntentsTestRule<PatientFavoriteListActivity> mActivityRule =
            new IntentsTestRule<>(PatientFavoriteListActivity.class, true, false);

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }

    @Test
    public void oneElementTest(){
        setupNoExtras(PatientFavoriteListActivity.class, mActivityRule, false, true, false, false, false, false, false);
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
        setupNoExtras(PatientFavoriteListActivity.class, mActivityRule, false, true, false, false, false, false, false);
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
        setupNoExtras(PatientFavoriteListActivity.class, mActivityRule, false, true, false, false, false, false, false);
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
