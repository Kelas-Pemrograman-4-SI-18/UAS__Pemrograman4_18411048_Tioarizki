package com.tio.loginregis.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tio.loginregis.R;
import com.tio.loginregis.adapter.AdapterBaju;
import com.tio.loginregis.admin.ActivityDataBaju;
import com.tio.loginregis.admin.EditBajuDanHapusActivity;
import com.tio.loginregis.admin.HomeAdminActivity;
import com.tio.loginregis.model.ModelBaju;
import com.tio.loginregis.server.BaseURL;
import com.tio.loginregis.session.PrefSetting;
import com.tio.loginregis.session.SessionManager;
import com.tio.loginregis.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeli extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterBaju adapter;
    ListView list;

    ArrayList<ModelBaju> newsList = new ArrayList<ModelBaju>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreference();

        session = new SessionManager(HomePembeli.this);

        prefSetting.islogin(session, prefs);

        getSupportActionBar().setTitle("Data Baju");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeli.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        newsList.clear();
        adapter = new AdapterBaju(HomePembeli.this, newsList);
        list.setAdapter(adapter);
        getAllBaju();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(HomePembeli.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllBaju() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataBaju, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data baju = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelBaju baju = new ModelBaju();
                                    final String _id = jsonObject.getString("_id");
                                    final String kodeBaju = jsonObject.getString("kodeBaju");
                                    final String stok = jsonObject.getString("stok");
                                    final String harga = jsonObject.getString("harga");
                                    final String jenisBaju = jsonObject.getString("jenisBaju");
                                    final String gamabr = jsonObject.getString("gambar");
                                    baju.setKodeBaju(kodeBaju);
                                    baju.setStok(stok);
                                    baju.setHarga(harga);
                                    baju.setJenisBaju(jenisBaju);
                                    baju.setGambar(gamabr);
                                    baju.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(HomePembeli.this, DetailBaju.class);
                                            a.putExtra("kodeBaju", newsList.get(position).getKodeBaju());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("stok", newsList.get(position).getStok());
                                            a.putExtra("harga", newsList.get(position).getHarga());
                                            a.putExtra("jenisBaju", newsList.get(position).getJenisBaju());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(baju);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}