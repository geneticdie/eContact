package xyz.voltwilz.employee.ClassOnly;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefUserProfile;
    private List<UserProfile> userProfileList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<UserProfile> userProfileList, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mRefUserProfile = mDatabase.getReference("Users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    }

    public void readUserProfile(final DataStatus dataStatus){
        mRefUserProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userProfileList.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    UserProfile userProfile = keyNode.getValue(UserProfile.class);
                    userProfileList.add(userProfile);
                }
                dataStatus.DataIsLoaded(userProfileList,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
