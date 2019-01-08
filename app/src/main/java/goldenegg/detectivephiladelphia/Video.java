package goldenegg.detectivephiladelphia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class Video extends AppCompatActivity implements View.OnClickListener {
    VideoView videoView;
    Button Play;
    Button Restart;
    boolean playing;
    int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        playing=true;
        Play= findViewById(R.id.btnPlay);
        Play.setOnClickListener(this);
        Restart= findViewById(R.id.btnRestart);
        Restart.setOnClickListener(this);
        videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setVideoPath("https://detectivephiladelphia.000webhostapp.com/index/1164215802.mp4");
        videoView.start();
        Play.setText("Pause");
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnPlay){
            if(playing==true){
                videoView.pause();
                length=videoView.getCurrentPosition();
                Play.setText("Play");
                playing=!playing;
            }
            else if (playing==false){
                videoView.seekTo(length);
                videoView.start();
                Play.setText("Pause");
                playing=!playing;
            }
        }
       else if(v.getId()==R.id.btnRestart){
            videoView.seekTo(0);
            videoView.start();
        }
    }
}
