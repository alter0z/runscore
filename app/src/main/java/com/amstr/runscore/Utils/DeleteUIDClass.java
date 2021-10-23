package com.amstr.runscore.Utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteUIDClass {
    public static Task<Void> removeUserID(String UserID){
        Task<Void> task = FirebaseDatabase.getInstance().getReference("users").child(UserID).setValue(null);
        return task;
    }
}
