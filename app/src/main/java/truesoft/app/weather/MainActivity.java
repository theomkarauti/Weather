package truesoft.app.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import truesoft.app.weather.Adapter.TempAdapter;
import truesoft.app.weather.Retrofit.GetDataService;
import truesoft.app.weather.Retrofit.RetrofitClientInstance;
import truesoft.app.weather.model.TempData;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


//All The main Component initialization
    Spinner spinner;
    String[] item = {"New York City","Tokyo","Mexico City","London", "New Delhi"};

    RecyclerView recyclerView;

    //Temperature  list...

    ArrayList<TempData> tempList;


    //Temperature adapter....

    TempAdapter tempAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);//find spinner
        spinner.setOnItemSelectedListener(this);

        recyclerView = findViewById(R.id.recyclerView);


        //pass data to list view via adapter or array..

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


        getDataRetrofit(item[position].toString(),"59dbf1369e8020e91a40a4bea835732c");
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //used to call Retrofit or it's data...

    private void getDataRetrofit(String city, String appid){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<TempData> call = service.getAllPhotos(city, appid);
        call.enqueue(new Callback<TempData>() {
            @Override
            public void onResponse(Call<TempData> call, Response<TempData> response) {
                tempList.clear();
                Log.d("Data",""+response.body());
//                generateDataList(response.body());
                TempData t = response.body();


                tempList.add(t);
                tempAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<TempData> call, Throwable t) {
                Log.d("Error",""+t.getLocalizedMessage().toString());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}