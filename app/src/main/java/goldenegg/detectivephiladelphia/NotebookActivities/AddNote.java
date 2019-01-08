package goldenegg.detectivephiladelphia.NotebookActivities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import goldenegg.detectivephiladelphia.LoginActivity;
import goldenegg.detectivephiladelphia.Models.Notes;
import goldenegg.detectivephiladelphia.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNote extends AppCompatActivity {

    public static final String TAG = "ADDNOTE";

    EditText editTextTitle;
    EditText editTextDescription;
    String keyID;
    boolean isEditable;

    /* FIREBASE */
    DatabaseReference myRef;
    FirebaseUser currUser;
    /* -------- */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_note);
        editTextTitle = findViewById(R.id.TitleEdit);
        editTextDescription = findViewById(R.id.DescriptionEdit);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) //quer dizer que estamos a visualizar uma nota existente
        {
            keyID = (String)bundle.get("keyID");
            isEditable = false;
        }
        else isEditable = true;

        if(!isEditable)
        {
            SetTextIsEditable(false);
        }

        currUser = AuthenticateUser();
        myRef = GetDatabaseReference(currUser);

        if(keyID != null)
        {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot keyNode : dataSnapshot.getChildren())
                    {
                        Notes n = keyNode.getValue(Notes.class);
                        if(n.getKeyID().equals(keyID))
                        {
                            SetTitleAndDescription(n);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            });
        }
    }

    void SetTextIsEditable(boolean condition)
    {
        editTextTitle.setEnabled(condition);
        editTextDescription.setEnabled(condition);
    }

    void SetTitleAndDescription(Notes note)
    {
        editTextTitle.setText(note.getTitle());
        editTextDescription.setText(note.getDescription());
    }

    /* FIREBASE METHODS */
    FirebaseUser AuthenticateUser()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser();
    }

    DatabaseReference GetDatabaseReference(FirebaseUser currUser)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(currUser.getUid()).child("Notes");
    }
/* -------------------------------------------*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_notebook_add, menu);

        if(keyID == null) menu.getItem(1).setVisible(false);
        else menu.getItem(1).setIcon( ContextCompat.getDrawable(this,R.drawable.deleteicon));


        if(isEditable)
        {
            menu.getItem(0).setIcon( ContextCompat.getDrawable(this,R.drawable.saveicon));
        }
        else menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.editnote));


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {

            case R.id.action_save_edit:

                if (isEditable) {
                    Notes note = new Notes();

                    String title = editTextTitle.getText().toString().trim();
                    String description = editTextDescription.getText().toString().trim();

                    if(title.isEmpty() || description.isEmpty())
                    {
                        Toast.makeText(this,"Title or description has no content.",Toast.LENGTH_LONG).show();
                        break;
                    }

                    note.setTitle(title);
                    note.setDescription(description);

                    if (keyID == null) note.setKeyID(UUID.randomUUID().toString());
                    else note.setKeyID(keyID);

                    myRef.child(note.getKeyID()).setValue(note);
                    finish();
                }
                else
                {
                    SetTextIsEditable(true);
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.saveicon));
                    isEditable = true;
                }

                break;

            case R.id.action_delete:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you sure you want to delete this note?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                myRef.child(keyID).removeValue();
                                finish();

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                break;

        }

        return super.onOptionsItemSelected(item);

    }
}
