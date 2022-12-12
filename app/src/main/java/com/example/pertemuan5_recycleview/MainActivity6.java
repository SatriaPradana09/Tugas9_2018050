package com.example.pertemuan5_recycleview;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.example.pertemuan5_recycleview.databinding.ActivityMain6Binding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity6 extends AppCompatActivity implements
        View.OnClickListener{
    //declaration variable
    private ActivityMain6Binding binding;
    String index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup view binding
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fetchButton.setOnClickListener(this);
    }
    //onclik button fetch
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fetch_button){
            index = binding.inputDay.getText().toString();
            try {
                getData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    //get data using api link
    public void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://goweather.herokuapp.com/weather/Indonesian")
                .buildUpon().build();
        URL url = new URL(uri.toString());
        new DOTask().execute(url);
    }
    class DOTask extends AsyncTask<URL, Void, String>{
        //connection request
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls [0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //get data json
        public void parseJson(String data) throws JSONException{
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
            JSONArray cityArray = jsonObject.getJSONArray("forecast");

            for (int i =0; i <cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                String Sobj = obj.get("day").toString();
                if (Sobj.equals(index)){
                    String day = obj.get("day").toString(); binding.resultDay.setText(day);
                    String temp = obj.get("temperature").toString(); binding.resultTemperature.setText(temp);
                    String wind = obj.get("wind").toString(); binding.resultWind.setText(wind);
                    break;
                }
                else{
                    binding.resultDay.setText("Not Found");
                    binding.resultTemperature.setText("Not Found");
                    binding.resultWind.setText("Not Found");
                }
            }
        }
    }
}
