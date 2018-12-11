package ch.epfl.sweng.vanjel.firebase;

import com.google.firebase.database.DataSnapshot;

public class FirebaseHelper {

    public static String dataSnapshotChildToString(DataSnapshot snapshot, String child) {
        Object databaseObject = snapshot.child(child).getValue();
        String databaseString;
        if (databaseObject !=null) {
            databaseString = databaseObject.toString();
        } else {databaseString = "";} //TODO: throw exception
        return databaseString;
    }
}
