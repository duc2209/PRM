package com.example.quizzz;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbQuery {
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseFirestore g_frirestore;
    public static List<CategoryModel> g_catList = new ArrayList<>();
    public static ProfileModel myProfile = new ProfileModel("NA",null);
    public static void getUserData(final MyCompleteListener completeListener){
        g_frirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        myProfile.setName(documentSnapshot.getString("NAME"));
                        myProfile.setEmail(documentSnapshot.getString("EMAIL_ID"));
                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }

    public static void loadData(final MyCompleteListener completeListener){
        loadCategoties(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                getUserData(completeListener);
            }

            @Override
            public void onFailure() {
                completeListener.onFailure();
            }
        });
    }
    public static int g_select_cat_index = 0;
    public static List<TestModel> g_testList = new ArrayList<>();
    public static void createUserData(String email, String name, String pass,final MyCompleteListener completeListener){
        Map<String, Object> userData =  new ArrayMap<>();
        userData.put("EMAIL_ID", email);
        userData.put("NAME", name);
        userData.put("PASS", pass);
        userData.put("TOTAL_SCORE", 0);

        DocumentReference userDoc = g_frirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        WriteBatch batch = g_frirestore.batch();
        batch.set(userDoc,userData);

        DocumentReference countDoc = g_frirestore.collection("USERS").document("TOTAL_USERS");
        batch.update(countDoc,"COUNT", FieldValue.increment(1));
        batch.commit()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        completeListener.onSuccess();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();


                    }
                });
    }
    public static void loadCategoties(final MyCompleteListener completeListener){
        g_catList.clear();
        g_frirestore.collection("QUIZ").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    docList.put(doc.getId(), doc);
                }
                QueryDocumentSnapshot catListDooc = docList.get("Categories");
                long catCount = catListDooc.getLong("COUNT");
                for( int i = 1; i <= catCount; i++){
                    String catID = catListDooc.getString("CAT" + String.valueOf(i) + "_ID");
                    QueryDocumentSnapshot catDoc = docList.get(catID);
                    int noOfTest = catDoc.getLong("NO_OF_TEST").intValue();
                    String catName = catDoc.getString("NAME");
                    g_catList.add(new CategoryModel(catID, catName, noOfTest));
                }
                completeListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                completeListener.onFailure();

            }
        });

    }

    public static void loadTestData(final  MyCompleteListener completeListener){
        g_testList.clear();

        g_frirestore.collection("QUIZ").document(g_catList.get(g_select_cat_index).getDocId())
                .collection("TESTS_LIST").document("TESTS_INFO")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        int noOpTests = g_catList.get(g_select_cat_index).getNoOfTests();
                        for(int i = 1; i<= noOpTests; i++){
                            g_testList.add(new TestModel(documentSnapshot.getString("TEST"+String.valueOf(i) + "_ID"),
                                    0,documentSnapshot.getLong("TEST" + String.valueOf(i) + "_TIME").intValue()));

                        }
                        completeListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completeListener.onFailure();
                    }
                });
    }
}
