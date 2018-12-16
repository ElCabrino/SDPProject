package ch.epfl.sweng.vanjel.login;

import android.support.test.espresso.matcher.ViewMatchers;

import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

class LoginHelper {

    void enterEmail(String email) {
        onView(ViewMatchers.withId(R.id.mailLogin)).perform(scrollTo(), replaceText(email));
    }

    void enterPassword(String password){
        onView(withId(R.id.passwordLogin)).perform(scrollTo(), replaceText(password));
    }
}
