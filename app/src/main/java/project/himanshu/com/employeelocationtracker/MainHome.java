package project.himanshu.com.employeelocationtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainHome extends AppCompatActivity {

    Handler handler = new Handler();
    TextView username, userid, contact;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    String url="http://www.xplosion.in/xplosionapi/api/empread";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        requestQueue = Volley.newRequestQueue(MainHome.this);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(MainHome.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        username = findViewById(R.id.name);
        userid = findViewById(R.id.id);
        contact = findViewById(R.id.contact);


        sharedPref = SharedPref.getInstance(MainHome.this);
        JSONObject object = new JSONObject();
        try {

            object.put("contact",sharedPref.getName().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, object, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                for (int i = 0; i < response.length();i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        userid.setText(jsonObject.getString("id"));
                        username.setText(jsonObject.getString("name"));
                        contact.setText(jsonObject.getString("contact"));

                        startService(new Intent(MainHome.this,LocationService.class));
                        startActivity(new Intent(MainHome.this,MapsActivity.class));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mymenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1 :

                stopService(new Intent(MainHome.this,LocationService.class));
                sharedPref.clearPreference();
                startActivity(new Intent(MainHome.this,HomeActivity.class));
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
