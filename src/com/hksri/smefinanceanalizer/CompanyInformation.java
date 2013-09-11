package com.hksri.smefinanceanalizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CompanyInformation extends Activity {
	public final DatabaseAccess dbAccess;

	EditText editCompanyName;
	Spinner spinIndustrialCategory;

	public CompanyInformation(){
		this.dbAccess = new DatabaseAccess();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_information);

		Button btnRegistCompany = (Button)findViewById(R.id.btnRegistCompany);

		editCompanyName = (EditText)findViewById(R.id.editCompanyName);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// アイテムを追加します
		adapter.add("建設業");
		adapter.add("製造業");
		adapter.add("サービス業");
		spinIndustrialCategory = (Spinner) findViewById(R.id.spinIndustrialCategory);
		// アダプターを設定します
		spinIndustrialCategory.setAdapter(adapter);

		btnRegistCompany.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					String strSql_S01 = "SELECT COALESCE(MAX(COMPANY_CODE) + 1, 1) INC_MAX_CODE FROM T_COMPANY";
					String sCompanyKey=dbAccess.getSQLFirstResultValue("INC_MAX_CODE", strSql_S01);
					String sCompanyName = editCompanyName.getText().toString();
					String sIndustrialCategory = (String) spinIndustrialCategory.getSelectedItem();
					String strSql_I01 = "INSERT INTO T_COMPANY(COMPANY_CODE, COMPANY_NAME, INDUSTRIAL_CATEGORY, DATA_TYPE, DATA_VERSION) VALUES(" + sCompanyKey + ", '" + sCompanyName + "', '" + sIndustrialCategory + "', 0, null)";
					dbAccess.execSQL(strSql_I01);
					Toast.makeText(getApplicationContext(), "会社名を登録しました", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(CompanyInformation.this, CompanyList.class);
					// 次画面のアクティビティ起動
					startActivity(intent);
				}catch(Exception ex){
					Toast.makeText(getApplicationContext(), "Err:btnRegistCompany.setOnClickListener" + ex.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_company_information, menu);
		return true;
	}
}
