package ch.epfl.sweng.vanjel;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

class LoginHelper {

    void enterEmail(String email) {
        onView(withId(R.id.mailLogin)).perform(replaceText(email));
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
