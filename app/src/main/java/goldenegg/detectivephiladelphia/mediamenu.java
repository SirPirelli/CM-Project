package goldenegg.detectivephiladelphia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class mediamenu extends AppCompatActivity implements View.OnClickListener {
ImageButton trailer;
ImageButton images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediamenu);
        trailer=findViewById(R.id.btnTrailer);
        trailer.setOnClickListener(this);
        images=findViewById(R.id.btnImages);
        images.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnImages){

            Log.d("Launcher","StoryLaunched");


            Intent startimages = new Intent(this,Images.class);
            startActivity(startimages);
        }
        if(v.getId()==R.id.btnTrailer){


            Intent startVideo = new Intent(this,Video.class);
            startActivity(startVideo);
        }

    }
}
