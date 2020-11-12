package com.tio.loginregis.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tio.loginregis.R;
import com.tio.loginregis.server.BaseURL;

public class DetailBaju extends AppCompatActivity {


    EditText edtKodeBaju, edtStok, edtHarga, edtJenisBaju;
    ImageView imgGambarBaju;

    String strKodeBaju, strStok, strHarga, strJenisBaju, strGambar, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_baju);

        edtKodeBaju = (EditText) findViewById(R.id.edtKodeBaju);
        edtStok = (EditText) findViewById(R.id.edtStok);
        edtHarga = (EditText) findViewById(R.id.edtHarga);
        edtJenisBaju = (EditText) findViewById(R.id.edtJenisBaju);

        imgGambarBaju = (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strKodeBaju = i.getStringExtra("kodeBaju");
        strStok = i.getStringExtra("stok");
        strHarga = i.getStringExtra("harga");
        strJenisBaju = i.getStringExtra("jenisBaju");
        strGambar = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtKodeBaju.setText(strKodeBaju);
        edtStok.setText(strStok);
        edtHarga.setText(strHarga);
        edtJenisBaju.setText(strJenisBaju);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGambar)
                .into(imgGambarBaju);
    }
}