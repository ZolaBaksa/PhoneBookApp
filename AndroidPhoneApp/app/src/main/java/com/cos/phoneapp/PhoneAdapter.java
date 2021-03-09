package com.cos.phoneapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private static final String TAG = "PhoneAdapter";
    private final List<Phone> mPhoneList;
    private final MainActivity mMainActivity;

    public PhoneAdapter(List<Phone> mPhoneList, MainActivity mMainActivity) {
        this.mPhoneList = mPhoneList;
        this.mMainActivity = mMainActivity;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: 아이템개수만큼 실행"); //phoneviewholder 정의된 생성자 가져다씀
        Phone data = mPhoneList.get(position);
        holder.mTextName.setText(data.getName());
        holder.mTextTel.setText(data.getTel());
    }

    @Override
    public int getItemCount() {
        return mPhoneList.size();
    }

    class PhoneViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextName;
        private TextView mTextTel;
        private LinearLayout phoneItem;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.name);
            mTextTel = itemView.findViewById(R.id.tel);
            phoneItem = itemView.findViewById(R.id.item);

        }
    }
}
