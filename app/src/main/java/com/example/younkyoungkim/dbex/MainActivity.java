package com.example.younkyoungkim.dbex;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editName,editCount,editResultName, editResultCount;
    Button butInit, butInsert,butSelect;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName=(EditText)findViewById(R.id.edit_group_name);
        editCount=(EditText)findViewById(R.id.edit_group_count);
        editResultName=(EditText)findViewById(R.id.edit_result_name);
        editResultCount=(EditText)findViewById(R.id.edit_result_count);
        butInit=(Button)findViewById(R.id.but_init);
        butInsert=(Button)findViewById(R.id.but_insert);
        butSelect=(Button)findViewById(R.id.but_select);

        //DB생성
        myHelper=new MyDBHelper(this);
        //기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.
        butInit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();
            }
        });   //익명 클래스
        butInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getWritableDatabase();
                String sql="insert into idolTable values('"+editName.getText()+"', "+editCount.getText()+")";
                sqlDB.execSQL(sql);
                sqlDB.close();
                Toast.makeText(MainActivity.this, "성공적으로 저장되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
        butSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getReadableDatabase();
                String sql="select * from idolTable";
                Cursor cursor = sqlDB.rawQuery(sql, null);
                String names="Idol 이름"+"\r\n"+"==========="+"\r\n";
                String counts="Idol 인원수"+"\r\n"+"==========="+"\r\n";
                while (cursor.moveToNext()){
                    names += cursor.getString(0)+ "\r\n";  //+= 연결연산자(데이터 연결)
                    counts += cursor.getInt(1)+"\r\n";
                }
                editResultName.setText(names);
                editResultCount.setText(counts);
                cursor.close();
                sqlDB.close();
            }
        });
    }

    class MyDBHelper extends SQLiteOpenHelper{
        // idolDB라는 이름의 데이터 베이스가 생성된다.
        public MyDBHelper(Context context) {
            super(context, "idolDB", null, 1);
        }
        // idolTable이라는 이름의 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            String sql="create table idolTable(idolName text not null primary key, idolCount integer)";
            sqLiteDatabase.execSQL(sql);   //변경을 위해 execSQL을 쓴다.
        }
        //이미 idolTable이 존재한다면 기존의 테이블을 삭제하고 새로 테이블을 만들 때 호출
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String sql="drop table if exists idolTable";
            db.execSQL(sql);
            onCreate(db);
        }
    }
}
