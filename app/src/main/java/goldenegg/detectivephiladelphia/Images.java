package goldenegg.detectivephiladelphia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Images extends AppCompatActivity {
    String urlString;
    ListView listView;
    ImagesAdapter adapter;
    List<String> urls;
    List<Bitmap> listBmps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        listView= findViewById(R.id.listViewI);


        urls= new ArrayList<>();
        listBmps = new ArrayList<>();
        urlString = "https://detectivephiladelphia.000webhostapp.com/index/urls.json";


        HttpFetchData httpFetchData = new HttpFetchData(urlString);


        httpFetchData.onHttpResponseEvent(new HttpFetchData.HttpListener() {
            @Override
            public void onHttpResponseEvent(String res) {
                //Log.d("URL", res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("urls");
                    for (int i = 0; i<jsonArray.length(); i++){
                        urls.add(jsonArray.getString(i));
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        adapter= new ImagesAdapter();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    class ImagesAdapter extends BaseAdapter implements View.OnClickListener{

        LayoutInflater layoutInflater;

        public ImagesAdapter(){
            layoutInflater=(LayoutInflater) getApplicationContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int i) {
            return urls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null)
                view = layoutInflater.inflate(R.layout.list_item, null);


            ImageView imageView= view.findViewById(R.id.itemlistImage);
            Picasso.get().load(urls.get(i)).into(imageView);
            adapter.notifyDataSetChanged();
            view.setTag(new Integer(i));
            view.setClickable(true);
            view.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View view) {



        }
    }


}



