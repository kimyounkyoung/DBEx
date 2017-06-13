package com.example.younkyoungkim.dbex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    class MyDBHelper extends SQLiteOpenHelper{

        public MyDBHelper(Context context) {
            super(context, "idolDB", null, 1);
        }
        // idolTable이라는 이름의 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            String sql="craete table idolTable(idolName text not null primary key, idolCount integer)";
            sqLiteDatabase.execSQL(sql);   //변경을 위해 execSQL을 쓴다.
        }
        //이미 idolTable이 존재한다면 기존의 테이블을 삭제하고 새로 테이블을 만들 때 호출
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String sql="drop table if exist idolTable";
            db.execSQL(sql);
            onCreate(db);
        }
    }
}
