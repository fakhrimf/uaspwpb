package com.pwpb.uas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {

    Context context;
    Click listener;
    List<Notes> notesList;

    public RvAdapter(Context context, Click listener, List<Notes> notesList) {
        this.context = context;
        this.listener = listener;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rv_list,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder h,final int i) {
        final Notes notes = notesList.get(i);
        h.tvJudul.setText(notes.getJudul());
        h.tvDesc.setText(notes.getDesc());
        h.tvDate.setText(notes.getWaktu());
        h.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(notes.getId(),notes.getJudul(),notes.getDesc(),"edit");
            }
        });
    }

    public interface Click {
        void click(String id,String judul, String desc, String act);
    }

    @Override
    public int getItemCount(){
        return notesList.size();
    }

    public class RvViewHolder extends RecyclerView.ViewHolder{
        TextView tvJudul,tvDate,tvDesc;
        ConstraintLayout bg;

        public RvViewHolder(@NonNull View v){
            super(v);
            tvJudul = v.findViewById(R.id.judul);
            tvDate = v.findViewById(R.id.date);
            tvDesc = v.findViewById(R.id.desc);
            bg = v.findViewById(R.id.bg);
        }
    }
}
