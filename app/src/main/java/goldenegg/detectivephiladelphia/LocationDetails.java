package goldenegg.detectivephiladelphia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LocationDetails extends AppCompatActivity {


    TextView locationName;
    TextView locationDescription;
    String locationNameBundle;
    String locstionDescriptionBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        locationName= findViewById(R.id.txtlocationName);
        locationDescription = findViewById(R.id.txtlocationDescription);
        Bundle bundle = getIntent().getExtras();
        locationNameBundle = (String) bundle.get("location_name"); // model
        locstionDescriptionBundle = (String) bundle.get("location_description"); // model
        locationName.setText(locationNameBundle);
        locationDescription.setText(locstionDescriptionBundle);

    }
}
