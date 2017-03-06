package com.example.bmobdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {


    private Button query;
    private List<Person> mDatas;
    private HomeAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "05a88bad6c86f566c35d0502e6467dff");
        initView();
        initData();

    }

    private void initData() {
        mDatas = new ArrayList<Person>();
        

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

        query = (Button) findViewById(R.id.btnQuery);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
                bmobQuery.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if(e == null){
                            Toast.makeText(getApplicationContext(),"查询成功",Toast.LENGTH_SHORT).show();
                            for (Person p : list){
                                mDatas.add(p);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"查询失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.i("queryError:",e.getMessage()+", "+e.getErrorCode());
                        }
                    }
                });


            }
        });

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTextView.setText(mDatas.get(position).getName()+mDatas.get(position).getAddress());

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView mTextView;
            public MyViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.context);
            }
        }
    }

}
