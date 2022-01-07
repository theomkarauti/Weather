package truesoft.app.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;

    String[] item = {"pen","pencil","book"};

    RecyclerView recyclerView;
    ArrayList<TempData> tempList;
    TempAdapter tempAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        recyclerView = findViewById(R.id.recyclerView);


        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        initRecyclerView();

//        tempList = new ArrayList<>();

    }

    private void initRecyclerView() {
        tempList = new ArrayList<>();
        tempAdapter = new TempAdapter(tempList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tempAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(getApplicationContext(),"Selected Item is "+item[position],Toast.LENGTH_SHORT).show();

//        new GetIffcoDatabyChannel().execute();
//        getDataRetrofit("London","59dbf1369e8020e91a40a4bea835732c");
        getDataRetrofit();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getDataRetrofit(){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<TempData> call = service.getAllPhotos();
        call.enqueue(new Callback<TempData>() {
            @Override
            public void onResponse(Call<TempData> call, Response<TempData> response) {
                tempList.clear();
                Log.d("Data",""+response.body());
//                generateDataList(response.body());
                TempData t = response.body();
                Log.d("Data",""+t.getCod());
                Log.d("Data",""+t.getList().get(1).getDtTxt());

                tempList.add(t);
                tempAdapter.notifyDataSetChanged();

//                tempList.clear();
//                tempList.add(t);
//                tempAdapter = new TempAdapter(tempList);
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.setAdapter(tempAdapter);
            }

            @Override
            public void onFailure(Call<TempData> call, Throwable t) {
                Log.d("Error",""+t.getLocalizedMessage().toString());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class GetIffcoDatabyChannel extends AsyncTask<Void, Void, Void> {

        ArrayList<TempData> data ;
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            dialog.setMessage("Please Wait");
            dialog.show();
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            String result = "";

            data = new ArrayList<>();
            try {
                //String temp = (getResources().getString(R.string.service_url) + "/GetIffcoDatabyChannel/" + startDate + "/" + endDate + "/" + countryName + "/" + channel);
//                temp = temp.replaceAll(" ", "%20");
                String temp = "http://api.openweathermap.org/data/2.5/forecast?q=London&appid=59dbf1369e8020e91a40a4bea835732c";
//                String temp = "http://api.openweathermap.org/data/2.5/weather?q=London&appid=59dbf1369e8020e91a40a4bea835732c";
                URL url = new URL(temp);
                Log.e("params", url.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String Line = "";
                    while ((Line = bufferedReader.readLine()) != null) {
                        result += Line;
                    }
                    bufferedReader.close();
                }
            } catch (Exception ex) {
                Log.d("Some Exception occure ", ex.toString());
                // return new String("Exception: " + ex.getMessage());
            }
            // parse json data
            try {

                Log.i("tagconvertstr", "[" + result + "]");

                TempData mainClassObject = new Gson().fromJson(result, TempData.class);

//                JSONObject jsonObj = new JSONObject(result);
//
//                JSONArray ja_data = jsonObj.getJSONArray("list");
//                Log.d("JSONData",""+ja_data);
//                int length = jsonObj .length();
//                JSONArray jArray = new JSONArray(result);
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject jsonObject = jArray.getJSONObject(i);
//
//                    String CountryName = jsonObject.getString("CountryName");
//                    String Tot_Actual_Val = jsonObject.getString("Tot_Actual_Val");
//                    String Tot_Target_Val = jsonObject.getString("Tot_Target_Val");
//                    String AchValue = jsonObject.getString("AchValue");
//                    String Local_Tot_Actual_Val = jsonObject.getString("Local_Tot_Actual_Val");
//                    String Local_Tot_Target_Val = jsonObject.getString("Local_Tot_Target_Val");
//                    String Local_Ach_Val = jsonObject.getString("Local_Ach_Val");
//                    String CategoryName = jsonObject.getString("CategoryName");
//                    String ChannelName = jsonObject.getString("ChannelName");
//                    String Actual_Qty = jsonObject.getString("ActualQty");
//                    String Target_Qty = jsonObject.getString("TargetQty");
//
//                    TempData c1 = new TempData(CountryName, Tot_Actual_Val, Tot_Target_Val, AchValue, Local_Tot_Actual_Val, Local_Tot_Target_Val, Local_Ach_Val, CategoryName, ChannelName, Actual_Qty, Target_Qty);
//
//                    tempList.add(c1);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            dialog.dismiss();
            Log.i("listsize : ", "" + tempList.size());
            tempAdapter.notifyDataSetChanged();

        }
    }
}