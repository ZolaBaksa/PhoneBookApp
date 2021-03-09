package com.cos.phoneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cos.phoneapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity2";

    private RecyclerView mMainRecyclerview;

    //리사이클러뷰 어댑터
    private PhoneAdapter mPhoneAdapter;
    private List<Phone> mPhoneList = new ArrayList<>();

    private PhoneService phoneService;

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAll();

        fab = findViewById(R.id.fab_save);
        fab.setOnClickListener(view -> {
            View dialog = view.inflate(view.getContext(),R.layout.dialog,null);
            EditText name = dialog.findViewById(R.id.edit_name);
            EditText tel = dialog.findViewById(R.id.edit_tel);

            AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
            dlg.setTitle("신규 연락처 등록");
            dlg.setView(dialog);
            dlg.setNegativeButton("취소",null);
            dlg.setPositiveButton("등록",(dialog1, which) -> {
               Phone phone = new Phone(name.getText().toString(),tel.getText().toString());

               saveAddress(phone);
            });
            dlg.show();
        });
    }

    private void init(){

        mMainRecyclerview = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMainRecyclerview.setLayoutManager(layoutManager);

        Log.d(TAG, "getData: findAll() " + mPhoneList);
        mPhoneAdapter = new PhoneAdapter(mPhoneList, this);
        mMainRecyclerview.setAdapter(mPhoneAdapter);
    }

    public void findAll(){
        phoneService = PhoneService.retrofit.create(PhoneService.class);
        Call<CMRespDto<List<Phone>>> call = phoneService.findAll();

        call.enqueue(new Callback<CMRespDto<List<Phone>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Phone>>> call, Response<CMRespDto<List<Phone>>> response) {
                CMRespDto<List<Phone>> cmRespDto = response.body();
                mPhoneList = cmRespDto.getData();
                // 어댑터에게 넘기기
                Log.d(TAG, "onResponse: callback data: " + mPhoneList);

                init();
            }

            @Override
            public void onFailure(Call<CMRespDto<List<Phone>>> call, Throwable t) {
                Log.d(TAG, "onFailure: findAll() 실패" + t.getMessage());
            }


        });
    }

    private void saveAddress(Phone phone){
        phoneService = PhoneService.retrofit.create(PhoneService.class);
        Call<CMRespDto<Phone>> save = phoneService.save(phone);
        save.enqueue(new Callback<CMRespDto<Phone>>() {
            @Override
            public void onResponse(Call<CMRespDto<Phone>> call, Response<CMRespDto<Phone>> response) {
                mPhoneList.add(phone);
                Toast.makeText(MainActivity.this,"등록 완료",Toast.LENGTH_SHORT).show();
                findAll();
            }

            @Override
            public void onFailure(Call<CMRespDto<Phone>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"등록 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }

}