package ch.epfl.sweng.vanjel.patientInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;

/**
 * @author Vincent CABRINI
 * @reviewer Nicolas BRANDT
 */
class PatientInfoDatabaseService {


    private String UserID ; //FirebaseAuth.getInstance().getUid();
    private AppCompatActivity activity;
    private DatabaseReference userDatabaseReference;
    final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();


    //TEMPORARY ID
    /*TODO: put the user ID of the logged user*/
    PatientInfoDatabaseService(AppCompatActivity activity, String patientID) {
        this.activity = activity;
        //String s = auth.getCurrentUser().getUid();
        String s = patientID;
        this.userDatabaseReference = database.getReference("Patient").child(s);
    }



        //LISTENERS
    <T> void addListListener(final List<T> typeList, final ListView listView, final String category, final Class c, final ArrayAdapter<T> adapter) {
        DatabaseReference db = userDatabaseReference.child(category);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    T item = (T) snap.getValue(c);

                    typeList.add((T) item);

                }

                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addAmountListener(final TextView textView, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        dbCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = dataSnapshot.getValue(String.class);
                    textView.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //LISTVIEW LISTENERS FOR UPDATING ITEMS
    void listViewListener(ListView listView, final List<? extends Info> typeList, final String category, final Context context) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String oldInfo = typeList.get(i).getAndroidInfo();
                if (category == "Condition" || category == "Allergy" || category == "Substance") {
                    final View dialogView = inflater.inflate(R.layout.activity_patient_info_update,null);
                    showUpdateInfoString(oldInfo, category, dialogView,dialogBuilder);
                }
                if (category == "Surgery") {
                    final View dialogView = inflater.inflate(R.layout.activity_patient_info_update_surgery,null);
                    showUpdateSurgery(oldInfo, category, dialogView,dialogBuilder);
                }
                if (category == "DrugReaction") {
                    final View dialogView = inflater.inflate(R.layout.activity_patient_info_update_drug_reaction,null);
                    showUpdateDrugReaction(oldInfo, category, dialogView,dialogBuilder);
                }
                if (category == "Drug") {
                    final View dialogView = inflater.inflate(R.layout.activity_patient_info_update_drug,null);
                    showUpdateDrug(oldInfo, category, dialogView,dialogBuilder);
                }
                return false;
            }
        });
    }

    void showUpdateInfoString(final String oldInfo, final String category, View dialogView, AlertDialog.Builder dialogBuilder) {

        //final View dialogView = inflater.inflate(R.layout.activity_patient_info_update,null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.patientInfoUpdateEditView);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonPatientInfoUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonPatientInfoDelete);

        dialogBuilder.setTitle(String.format("Updating %s",category.toLowerCase()));

        final AlertDialog alertDialog = dialogBuilder.create();
        //final String info = editTextName.getText().toString().trim();
        alertDialog.show();


        /*if (!isSingleField) {
            buttonUpdate.setVisibility(View.INVISIBLE);
        }
        else {*/
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editTextName.getText().toString().trim();
                if (TextUtils.isDigitsOnly(info)) {
                    editTextName.setError("Information required");
                    return;
                }
                deleteItem(oldInfo, category);
                addItemToDatabase(info, category, new InfoString(info));
                //patientInfoDatabaseService.updateCondition(oldInfo.getInfo(),category);

                alertDialog.dismiss();
            }
        });
        //}

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(oldInfo,category);
                alertDialog.dismiss();
            }
        });

    }


    void showUpdateSurgery(final String oldInfo, final String category,View dialogView, AlertDialog.Builder dialogBuilder) {

        //final View dialogView = inflater.inflate(R.layout.activity_patient_info_update_surgery,null);

        dialogBuilder.setView(dialogView);

        final EditText surgeryUpdateType = (EditText) dialogView.findViewById(R.id.patientInfoUpdateSurgeryType);
        final EditText surgeryUpdateYear = (EditText) dialogView.findViewById(R.id.patientInfoUpdateSurgeryYear);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonPatientInfoUpdateSurgery);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonPatientInfoDeleteSurgery);

        dialogBuilder.setTitle("Updating surgery");

        final AlertDialog alertDialog = dialogBuilder.create();
        //final Surgery chir = new Surgery(getTextFromField(surgeryUpdateType), getTextFromField(surgeryUpdateYear));
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: check not null
                Surgery chir = new Surgery(PatientInfo.getTextFromField(surgeryUpdateType), PatientInfo.getTextFromField(surgeryUpdateYear));

                deleteItem(oldInfo, category);
                addItemToDatabase(chir.getAndroidInfo(), category, chir);

                alertDialog.dismiss();
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(oldInfo,category);
                alertDialog.dismiss();
            }
        });


    }

    void showUpdateDrugReaction(final String oldInfo, final String category,View dialogView, AlertDialog.Builder dialogBuilder) {

        //final View dialogView = inflater.inflate(R.layout.activity_patient_info_update_drug_reaction,null);

        dialogBuilder.setView(dialogView);

        final EditText drUpdateType = (EditText) dialogView.findViewById(R.id.patientInfoUpdateDrugReactionDrug);
        final EditText drUpdateYear = (EditText) dialogView.findViewById(R.id.patientInfoUpdateDrugReactionReaction);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonPatientInfoUpdateDrugReaction);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonPatientInfoDeleteDrugReaction);

        dialogBuilder.setTitle("Updating drug reaction");

        final AlertDialog alertDialog = dialogBuilder.create();
        /*final DrugReaction dr = new DrugReaction(PatientInfo.getTextFromField(drUpdateType), PatientInfo.getTextFromField(drUpdateYear));
        */alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: check not null
                DrugReaction dr = new DrugReaction(PatientInfo.getTextFromField(drUpdateType), PatientInfo.getTextFromField(drUpdateYear));

                deleteItem(oldInfo, category);
                addItemToDatabase(dr.getAndroidInfo(), category, dr);
                //patientInfoDatabaseService.updateCondition(oldInfo.getInfo(),category);

                alertDialog.dismiss();
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(oldInfo,category);
                alertDialog.dismiss();
            }
        });

    }


    void showUpdateDrug(final String oldInfo, final String category,View dialogView , AlertDialog.Builder dialogBuilder) {

        //final View dialogView = inflater.inflate(R.layout.activity_patient_info_update_drug,null);

        dialogBuilder.setView(dialogView);
        //final AlertDialog alertDialog = initView(R.layout.activity_patient_info_update_drug,this);
        final EditText drugUpdateDrug = (EditText) dialogView.findViewById(R.id.patientInfoUpdateDrugDrug);
        final EditText drugUpdateDosage = (EditText) dialogView.findViewById(R.id.patientInfoUpdateDrugDosage);
        final EditText drugUpdateFrequency = (EditText) dialogView.findViewById(R.id.patientInfoUpdateDrugFrequency);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonPatientInfoUpdateDrug);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonPatientInfoDeleteDrug);

        //dialogBuilder.setTitle("Updating drug");

        final AlertDialog alertDialog = dialogBuilder.create();
        /*final Drug drug = new Drug(PatientInfo.getTextFromField(drugUpdateDrug),
                PatientInfo.getTextFromField(drugUpdateDosage),PatientInfo.getTextFromField(drugUpdateFrequency));*/
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: check not null
                Drug drug = new Drug(PatientInfo.getTextFromField(drugUpdateDrug),
                        PatientInfo.getTextFromField(drugUpdateDosage),PatientInfo.getTextFromField(drugUpdateFrequency));

                deleteItem(oldInfo, category);
                addItemToDatabase(drug.getAndroidInfo(), category, drug);
                //patientInfoDatabaseService.updateCondition(oldInfo.getInfo(),category);

                alertDialog.dismiss();
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(oldInfo,category);
                alertDialog.dismiss();
            }
        });


    }





    //SETTERS

    <T> void addItemToDatabase(String item, String category, T itemObject) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        String toastText = category;
        // for correct string format
        if (category == "DrugReaction") {
            toastText = "Drug reaction";
        }

        if (!TextUtils.isEmpty(item)) {
            dbCat.child(item).setValue(itemObject);
            Toast.makeText(this.activity, String.format("%s added.",toastText), Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity, String.format("Please enter the %s information you want to add.",toastText.toLowerCase()), Toast.LENGTH_LONG).show();
        }
    }

    void addAmount(String amount, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        if(!TextUtils.isEmpty(amount)) {
            dbCat.setValue(amount);
            Toast.makeText(this.activity,String.format("%s amount added.",category),Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity, String.format("Please enter the %s amount.",category.toLowerCase()), Toast.LENGTH_LONG).show();
        }
    }

    //TODO: check if method needed
    void updateCondition(String info, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category).child(info);
        InfoString cond = new InfoString(info);
        dbCat.setValue(cond);
        Toast.makeText(this.activity,"Condition updated",Toast.LENGTH_LONG).show();

    }

    void deleteItem(String info, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category).child(info);
        dbCat.removeValue();
        Toast.makeText(this.activity,String.format("%s deleted",category),Toast.LENGTH_LONG).show();
    }



}
