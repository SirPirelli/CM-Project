package goldenegg.detectivephiladelphia.NotebookActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import goldenegg.detectivephiladelphia.Models.Notes;
import goldenegg.detectivephiladelphia.R;

public class MainNotebook extends AppCompatActivity {

    public static final String TAG = "NOTEBOOK";
    NotebookAdapter adapter;
    ListView listView;
    List<Notes> notesArray = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notebook_main);

        adapter = new NotebookAdapter();
        listView = findViewById(R.id.listViewNotes);
        listView.setCacheColorHint(Color.WHITE);

        ImageButton fab = findViewById(R.id.fab);
        listView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainNotebook.this, AddNote.class);
                startActivity(intent);
            }
        });
/* ----------------------------------------------------------*/
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currUser = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(currUser.getUid()).child("Notes");

        /* READ VALUES FROM DATABASE */
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                notesArray.clear();
                for(DataSnapshot keyNode : dataSnapshot.getChildren())
                {
                    Notes n = keyNode.getValue(Notes.class);
                    notesArray.add(n);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }

        });


        /* -----------------*/
    }
    /* HERE WE DEFINE HOW WE ARE GOING TO PRESENT THE INFORMATION, BY CREATING A ADAPTER TO SHOW THE NOTESARRAY */
    class NotebookAdapter extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return notesArray.size();
        }

        @Override
        public Object getItem(int position) {
            return notesArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null)
                convertView = getLayoutInflater().inflate(R.layout.rowviews_notes,null);

            TextView textViewDescription = convertView.findViewById(R.id.titleTextView);

            textViewDescription.setText(notesArray.get(position).getTitle());

            convertView.setTag(position);
            convertView.setClickable(true);
            convertView.setOnClickListener(this);

            return convertView;
        }

        @Override
        public void onClick(View v) {
            int position = (int)v.getTag();

            Intent intent = new Intent(MainNotebook.this, AddNote.class);
            intent.putExtra("keyID", notesArray.get(position).getKeyID());
            startActivity(intent);
        }
    }
}

