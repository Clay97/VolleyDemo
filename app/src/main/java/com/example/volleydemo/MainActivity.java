package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView result;
    private ImageView imgView;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Button stringRequest = findViewById(R.id.string);
        Button jsonRequest = findViewById(R.id.json);
        Button imageLoader = findViewById(R.id.img);
        imgView = findViewById(R.id.img_view);
        result =findViewById(R.id.result);
        stringRequest.setOnClickListener(this);
        jsonRequest.setOnClickListener(this);
        imageLoader.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // 解析String
            case R.id.string:
                String stringUrl = "https://www.baidu.com";
                StringRequest mStringRequest = new StringRequest(Request.Method.GET, stringUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.setText(error.getMessage());
                    }
                });
                requestQueue.add(mStringRequest);
                break;
            // 解析json
            case R.id.json:
                String jsonUrl = "http://10.0.2.2:8080/get_data.json";
                JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type type = new TypeToken<List<JsonData>>(){}.getType();
                        List<JsonData> dataList = new Gson().fromJson(response.toString(),type);
                        String resultText = "";
                        for (JsonData data : dataList){
                            resultText += data.getName();
                        }
                        result.setText(resultText);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.setText(error.getMessage());
                    }
                });
                requestQueue.add(mJsonArrayRequest);
                break;
            // 解析土拍你
            case  R.id.img:
                String imgUrl = "https://c-ssl.duitang.com/uploads/item/201409/17/20140917095434_aZm2R.jpeg";
                ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                    @Override
                    public Bitmap getBitmap(String url) {
                        return null;
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {

                    }
                });
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(imgView,R.drawable.ic_autorenew_black_24dp,R.drawable.ic_mood_bad_black_24dp);
                imageLoader.get(imgUrl,listener,200,200);
                break;
            default:
                break;
        }
    }
}
