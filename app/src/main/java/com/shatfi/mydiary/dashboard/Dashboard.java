package com.shatfi.mydiary.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shatfi.mydiary.Signin;
import com.shatfi.mydiary.databinding.ActivityDashboardBinding;
import com.shatfi.mydiary.model.ModelDiary;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements UpdateDiary{

    ActivityDashboardBinding binding;
    ArrayList<ModelDiary> diaries;
    DatabaseReference reference;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayoutManager linearLayoutManager;
    AdapterDiary adapterDiary;
    ModelDiary modalDiary;
    ProgressDialog dialog;
    String unique;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        preferences= getSharedPreferences("diaryapp", MODE_PRIVATE);
        unique = preferences.getString("unique", "");
        reference = FirebaseDatabase.getInstance().getReference("DataUser").child(unique);
        diaries = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        dialog = new ProgressDialog(this);

        binding.rvDiary.setLayoutManager(linearLayoutManager);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, AddDiary.class));
            }
        });

        showDiary();
    }
    private  void showDiary(){
        dialog.setMessage("Tunggu..");
        dialog.show();
        diaries.clear();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    modalDiary = ds.getValue(ModelDiary.class);
                    diaries.add(modalDiary);
                }
                adapterDiary = new AdapterDiary(Dashboard.this, diaries);
                binding.rvDiary.setAdapter(adapterDiary);
                binding.noItem.setVisibility(View.GONE);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.noItem.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void UpdateDiary(ModelDiary diary) {
        reference.child(modalDiary.getUserid()).setValue(diary).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Dashboard.this,"Update Berhasil", Toast.LENGTH_SHORT).show();
                    showDiary();
                }else{
                    Toast.makeText(Dashboard.this,"Update Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void DeleteDiary(ModelDiary diary) {

    }
    protected  void onRestart(){
        super.onRestart();
        showDiary();
    }
}
