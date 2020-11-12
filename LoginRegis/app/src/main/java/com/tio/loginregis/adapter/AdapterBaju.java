package com.tio.loginregis.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tio.loginregis.R;
import com.tio.loginregis.model.ModelBaju;
import com.tio.loginregis.server.BaseURL;

import java.util.List;

public class AdapterBaju extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelBaju> item;

    public AdapterBaju(Activity activity, List<ModelBaju> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.conten_baju, null);


        TextView stok     = (TextView) convertView.findViewById(R.id.txtStok);
        TextView harga         = (TextView) convertView.findViewById(R.id.txtHarga);
        TextView jenisBaju         = (TextView) convertView.findViewById(R.id.txtJenisBaju);
        ImageView gambar        = (ImageView) convertView.findViewById(R.id.gambar);

        stok.setText(item.get(position).getStok());
        harga.setText("Rp." + item.get(position).getHarga());
        jenisBaju.setText(item.get(position).getJenisBaju());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }
}
