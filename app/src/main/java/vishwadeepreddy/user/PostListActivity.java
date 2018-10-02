package vishwadeepreddy.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostListActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabse;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        //declaring the database reference object, this is what we use to access the database

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabse.getReference("Users");
        FirebaseUser user = mAuth.getCurrentUser();

        userID = user.getUid();

        listView = (ListView) findViewById(R.id.user);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);


        myRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String fname = dataSnapshot.child("First Name").getValue(String.class);
                String lname = dataSnapshot.child("Last Name").getValue(String.class);
                String email = dataSnapshot.child("Email").getValue(String.class);
                String dob = dataSnapshot.child("Date of Birth").getValue(String.class);
                String city = dataSnapshot.child("City").getValue(String.class);
                String country = dataSnapshot.child("Country").getValue(String.class);
                String pnum = dataSnapshot.child("Phone Number").getValue(String.class);

                Log.d("user_info", fname +lname +email +dob +city +country +pnum);

                arrayList.add(fname);
                arrayList.add(lname);
                arrayList.add(email);
                arrayList.add(dob);
                arrayList.add(city);
                arrayList.add(country);
                arrayList.add(pnum);

                adapter.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setAdapter(adapter);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
