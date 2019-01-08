package goldenegg.detectivephiladelphia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class Locations extends AppCompatActivity implements View.OnClickListener {


    ImageButton location1;
    ImageButton location2;
    ImageButton location3;
    ScrollView scrollView;
    LinearLayout lnLayout;
    List<LocationModel> locationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        location1= findViewById(R.id.btnTowers);
        location2= findViewById(R.id.btnAlley);
        location3= findViewById(R.id.btnBAY);
         locationList= new ArrayList<>();
         LocationModel location33= new LocationModel("Bay"," Located in the southern part of the city the Philadelphia docks have a rich history of trading in the 19th century bringing about it’s fame and high regard as one of its historical landmarks.\n" +
                 "It was in this location that several officers from the Marine Corps were found dead and scattered about, making it one of the key investigation locales in this case.");
        LocationModel location22= new LocationModel("Alley"," The alley is where the body of Sargent Winters was found alongside some of his belongings that have already been taken by the police as evidence. There still hasn’t been made any connection between the crime scene and the victim.");
        LocationModel location11= new LocationModel("Towers","Also known as the Philadelphia Business Center, the towers hold most of the bigger and most successful companies in the entire city, giving it a certain flow of important busy businessman and overall workers of a higher class. It’s also home to the Philadelphia Police Department.");
        locationList.add(location11);
        locationList.add(location22);
        locationList.add(location33);
        location1.setOnClickListener(this);
        location2.setOnClickListener(this);
        location3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        Intent intent;

        switch (id)
        {
            case R.id.btnTowers:
                intent = new Intent(this, LocationDetails.class);
                intent.putExtra("location_name",locationList.get(0).getLocationName());
                intent.putExtra("location_description",locationList.get(0).getDescription());
                startActivity(intent);
                break;

            case R.id.btnAlley:
                intent = new Intent(this, LocationDetails.class);
                intent.putExtra("location_name",locationList.get(1).getLocationName());
                intent.putExtra("location_description",locationList.get(1).getDescription());
                startActivity(intent);
                break;

            case R.id.btnBAY:
                intent = new Intent(this, LocationDetails.class);
                intent.putExtra("location_name",locationList.get(2).getLocationName());
                intent.putExtra("location_description",locationList.get(2).getDescription());
                startActivity(intent);
                break;
        }
    }
}






