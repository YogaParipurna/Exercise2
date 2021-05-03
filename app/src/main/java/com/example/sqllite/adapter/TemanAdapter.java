package com.example.sqllite.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqllite.EditTeman;
import com.example.sqllite.MainActivity;
import com.example.sqllite.R;
import com.example.sqllite.ShowTeman;
import com.example.sqllite.database.DBController;
import com.example.sqllite.database.Teman;



import java.util.ArrayList;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {

    private ArrayList<Teman> listData;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nm,tlp;

        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(18);
        holder.namaTxt.setText(nm);
        holder.telponTxt.setText(tlp);
    }

    @Override
    public int getItemCount() {

        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt,telponTxt;
        private Context context;
        Bundle bundle = new Bundle();
        DBController dbController;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
            context = itemView.getContext();
            dbController = new DBController(context);

            cardku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nama = listData.get(getAdapterPosition()).getNama();
                    String telpon = listData.get(getAdapterPosition()).getTelpon();

                    bundle.putString("nama", nama.trim());
                    bundle.putString("telpon", telpon.trim());

                    Intent intent = new Intent(context, ShowTeman.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            cardku.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu pm = new PopupMenu(context, view);
                    pm.inflate(R.menu.pop_upmenu);
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mnEdit:
                                    String id = listData.get(getAdapterPosition()).getId();
                                    String nama = listData.get(getAdapterPosition()).getNama();
                                    String telpon = listData.get(getAdapterPosition()).getTelpon();

                                    bundle.putString("id", id.trim());
                                    bundle.putString("nama", nama.trim());
                                    bundle.putString("telpon", telpon.trim());

                                    Intent intent = new Intent(context, EditTeman.class);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    break;
                                case R.id.mnDelete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Hapus Data");
                                    builder.setMessage("Apakah Anda yakin?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String id = listData.get(getAdapterPosition()).getId();
                                                    dbController.deleteData(id);

                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    context.startActivity(intent);
                                                }
                                            }
                                    );
                                    builder.setNegativeButton("Batal",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            }
                                    );
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    break;

                                case R.id.mnView:
                                    String Nama = listData.get(getAdapterPosition()).getNama();
                                    String Telpon = listData.get(getAdapterPosition()).getTelpon();

                                    bundle.putString("nama", Nama.trim());
                                    bundle.putString("telpon", Telpon.trim());

                                    Intent Intent = new Intent(context, ShowTeman.class);
                                    Intent.putExtras(bundle);
                                    context.startActivity(Intent);
                                    break;
                            }
                            return false;
                        }
                    });
                    pm.show();
                    return false;
                }
            });
        }
    }
}