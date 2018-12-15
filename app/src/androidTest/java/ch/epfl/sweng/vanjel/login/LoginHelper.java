package ch.epfl.sweng.vanjel.login;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.matcher.ViewMatchers;

import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

class LoginHelper {

    void enterEmail(String email) {
        onView(ViewMatchers.withId(R.id.mailLogin)).perform(replaceText(email));
    }

    void enterPassword(String password){
        onView(withId(R.id.passwordLogin)).perform(replaceText(password));
    }

    /*void signOutIfPossible() {
        try {
            onView(withId(R.id.logoutMainMenu)).perform(scrollTo(), click());
        } catch (NoMatchingViewException e) {
            //
        }

    }*/
}
