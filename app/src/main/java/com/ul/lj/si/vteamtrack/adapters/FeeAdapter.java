package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.util.Date;
import java.util.List;


import entities.Fee;

import entities.User;

import viewModels.FeeModel;

import viewModels.UserModel;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.ViewHolder> {

    private Activity activity;
    private List<Fee> fees;
    private UserModel userModel;
    private FeeModel feeModel;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userName;
        public TextView userSurname;
        public CheckBox payed;

        public ViewHolder(View itemView) {

            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.fee_list_user_name);
            userSurname = (TextView) itemView.findViewById(R.id.fee_list_user_surname);
            payed = (CheckBox) itemView.findViewById(R.id.checkBox_fee_list);
        }
    }
    public FeeAdapter(List<Fee> fees, Activity activity) {

        this.activity = activity;
        this.fees=fees;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        feeModel = new ViewModelProvider((FragmentActivity) activity).get(FeeModel.class);
        userModel = new ViewModelProvider((FragmentActivity) activity).get(UserModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_fee_list, parent, false);

        // Return a new holder instance
        //
        FeeAdapter.ViewHolder viewHolder = new FeeAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Fee fee = fees.get(position);
        User player = userModel.getUser(fee.getPlayerId());
        TextView userName = holder.userName;
        TextView userSurname = holder.userSurname;
        CheckBox payed = holder.payed;
        userName.setText(player.getFirstName());
        userSurname.setText(player.getLastName());

        boolean checked = fee.isPayed();
        payed.setChecked(checked);
            if(PreferenceData.getUserRole(activity.getApplicationContext()).equals("trainer")){
                payed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!checked){
                            fee.setPayed(true);
                        }
                        else {
                            fee.setPayed(false);

                        }
                        feeModel.update(fee);
                    }
                });
            }
            else {
                payed.setEnabled(false);
            }

    }

    @Override
    public int getItemCount() {
        return fees.size();
    }
}
