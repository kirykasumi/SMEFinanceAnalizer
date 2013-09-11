package com.hksri.smefinanceanalizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FinanceList extends Activity {
	public final DatabaseAccess dbAccess; 

	public FinanceList(){
		this.dbAccess = new DatabaseAccess();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_list);
        Button btnTest = (Button)findViewById(R.id.Button02);
        TextView textCompanyName = (TextView)findViewById(R.id.textCompanyName);
        TextView textTitleIndustrialCategory = (TextView)findViewById(R.id.textTitleIndustrialCategory);

		// インテントを取得
		Intent intent = getIntent();
		// インテントに保存されたデータを取得
		String data = intent.getStringExtra("keyword");
		
		Toast.makeText(getApplicationContext(), "インテントから取得したデータ：" + data, Toast.LENGTH_SHORT).show();
		
		String strSql_S01 = "SELECT COMPANY_CODE, COMPANY_NAME, INDUSTRIAL_CATEGORY, DATA_TYPE FROM T_COMPANY WHERE COMPANY_CODE=" + data;
		Cursor cr = dbAccess.getSQLResultCursor(strSql_S01);
		if (cr.moveToNext()) {
			textCompanyName.setText(cr.getString(cr.getColumnIndex("COMPANY_NAME")));
			textTitleIndustrialCategory.setText(cr.getString(cr.getColumnIndex("INDUSTRIAL_CATEGORY")));
		}
		
		btnTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// インテントのインスタンス生成
				Intent intent = new Intent(FinanceList.this, FinanceInformation.class);
				// 次画面のアクティビティ起動
				startActivity(intent);
			}
		});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_finance_list, menu);
        return true;
    }
}
