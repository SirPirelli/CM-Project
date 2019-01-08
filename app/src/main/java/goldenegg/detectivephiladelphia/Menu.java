package goldenegg.detectivephiladelphia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import goldenegg.detectivephiladelphia.NotebookActivities.MainNotebook;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    ImageButton story;
    ImageButton locations;
    ImageButton chars;
    ImageButton cases;
    ImageButton media;
    ImageButton notebook;
    ImageButton ask;
    ImageButton website;
    ImageButton logout;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        story =findViewById(R.id.story);
        story.setOnClickListener(this);

        locations =findViewById(R.id.locations);
        locations.setOnClickListener(this);

        chars =findViewById(R.id.chars);
        chars.setOnClickListener(this);

        cases =findViewById(R.id.cases);
        cases.setOnClickListener(this);

        media =findViewById(R.id.media);
        media.setOnClickListener(this);

        notebook =findViewById(R.id.notebook);
        notebook.setOnClickListener(this);

        ask =findViewById(R.id.ask);
        ask.setOnClickListener(this);

        website = findViewById(R.id.websitebtn);
        website.setOnClickListener(this);

        logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.story){
            Intent startStory = new Intent(this,Story.class);
            startActivity(startStory);

        }
       else if(v.getId()==R.id.locations){
            Log.d("Launcher","locationsLaunched");

            Intent startlocations = new Intent(this,Locations.class);
            startActivity(startlocations);

        }
       else if(v.getId()==R.id.chars){

            Intent startP = new Intent(this,Personas.class);
            startActivity(startP);
        }
       else if(v.getId()==R.id.cases){

            Intent startCases = new Intent(this,Cases.class);
            startActivity(startCases);
        }
       else if(v.getId()==R.id.media){
            Log.d("Launcher","MediaLaunched");
            Intent startMedia = new Intent(this,mediamenu.class);
            startActivity(startMedia);
        }
       else  if(v.getId()==R.id.notebook){
            Log.d("Launcher","NotebookLaunched");
            Intent notebookMain = new Intent(this, MainNotebook.class);
            startActivity(notebookMain);
        }
        else  if(v.getId()==R.id.ask){

            Toast.makeText(this, "      Developers\n\n\n     José Pereira \n\n\n     Igor Lima \n\n\n     Manuel Oliveira \n\n\n     Pedro Fernandes\n",
                   Toast.LENGTH_LONG).show();
        }
        else if(v.getId() == R.id.websitebtn)
        {
            Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
            openURL.setData(Uri.parse(getString(R.string.websiteURL)));
            startActivity(openURL);
        }
        else if(v.getId() == R.id.btnLogout)
        {
            auth = FirebaseAuth.getInstance();
            auth.signOut();
            Toast.makeText(Menu.this,"Logged out",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
