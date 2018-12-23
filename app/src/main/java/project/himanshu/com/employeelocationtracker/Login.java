package project.himanshu.com.employeelocationtracker;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Fragment {

    EditText usercontact, pass;
    Button submit;
    ProgressDialog progressDialog;
    RequestQueue queue;
    String url="http://www.xplosion.in/xplosionapi/api/emplogin";
    SharedPref sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref =SharedPref.getInstance(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        usercontact = view.findViewById(R.id.userid);
        pass = view.findViewById(R.id.userpassword);

        queue = Volley.newRequestQueue(getContext());
        submit = view.findViewById(R.id.submitbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                JSONObject object = new JSONObject();
                try {

                    object.put("contact",usercontact.getText().toString().trim());
                    object.put("password",pass.getText().toString().trim());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        String successValue = "";
                        try {
                             successValue = response.getString("success");
                            if(successValue.equals("login Successfully")) {
                                sharedPref.setLogin(true);
                                sharedPref.setName(usercontact.getText().toString().trim());
                                Toast.makeText(getContext(), "" + successValue, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(),MainHome.class));

                            } else  {
                                Toast.makeText(getContext(), "Incorrect Login", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), ""+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

                queue.add(request);

            }
        });

    }
}
