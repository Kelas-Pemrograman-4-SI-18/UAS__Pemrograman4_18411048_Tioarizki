package com.tio.loginregis.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ornach.nobobutton.NoboButton;
import com.tio.loginregis.R;
import com.tio.loginregis.server.BaseURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisActivity extends AppCompatActivity {

    Button btnBackLogin;
    NoboButton btnregistrasi;
    EditText edtUserName, edtNamaLengkap, edtEmail, edtNotelp, edtPassword;
    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtNamaLengkap = (EditText)findViewById(R.id.edtNmLengkap);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtNotelp =(EditText)findViewById(R.id.edtNotelp);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        btnBackLogin = (Button) findViewById(R.id.btnBackLogin);
        btnregistrasi = (NoboButton)findViewById(R.id.btnRegistrasi);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnregistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName      = edtUserName.getText().toString();
                String strNamaLengkap   = edtNamaLengkap.getText().toString();
                String strEmail         = edtEmail.getText().toString();
                String strNotelp        = edtNotelp.getText().toString();
                String strPassword      = edtPassword.getText().toString();

                if (strUserName.isEmpty()){
                    Toast.makeText(getApplicationContext(),"User Name Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if (strNamaLengkap.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Nama Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if (strEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if (strNotelp.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Nomor Telphone Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if (strPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else {
                    registrasi(strUserName,strNamaLengkap, strEmail,strNotelp,strPassword);
                }

            }
        });
    }

    public void registrasi(String userName, String namaLengkap, String email, String noTelp, String password){


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("namaLengkap", namaLengkap);
        params.put("email", email);
        params.put("noTelp", noTelp);
        params.put("role", "2");
        params.put("password", password);

        pDialog.setMessage("Mohon Tunggu .....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.register, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");

                            if (status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegisActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        mRequestQueue.add(req);

    }


    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}