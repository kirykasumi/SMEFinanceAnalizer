package com.hksri.smefinanceanalizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CompanyList extends Activity {

	LinearLayout llCompanyList;
	public final DatabaseAccess dbAccess; 

	public CompanyList(){
		this.dbAccess = new DatabaseAccess();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_list);
		llCompanyList = (LinearLayout)findViewById(R.id.llCompanyList);

		createInitialTables();
		setRegistedCompanyToBtn();

		Button btnNewCompany = (Button)findViewById(R.id.btnNewCompany);
		Button btnDeleteAll = (Button)findViewById(R.id.btnDeleteAll);

		btnNewCompany.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// インテントのインスタンス生成
				Intent intent = new Intent(CompanyList.this, CompanyInformation.class);
				// 次画面のアクティビティ起動
				startActivity(intent);
			}
		});

		btnDeleteAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strSql_DE01 = "DELETE FROM T_COMPANY";
				dbAccess.execSQL(strSql_DE01);
				Toast.makeText(getApplicationContext(), "会社情報を削除しました", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onDestroy(Bundle savedInstanceState) {
		dbAccess.finalize();
	}

	public void onStart(Bundle savedInstanceState) {
		setRegistedCompanyToBtn();
	}

	public void onResume(Bundle savedInstanceState) {
		setRegistedCompanyToBtn();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_company_list, menu);
		return true;
	}

	public void createInitialTables(){
		try{
//			String strSql_S01 = "SELECT * FROM SQLITE_MASTER";
			String strSql_S01 = "select name from sqlite_master where type = 'table'";
//			String strSql_S01 = "SELECT COUNT(*) FROM SQLITE_MASTER WHERE TYPE = 'TABLE'";
			String strSql_D01 = "DROP TABLE IF EXISTS T_COMPANY;";
			String strSql_D02 = "DROP TABLE IF EXISTS T_FINANCIAL_STATEMENT;";
			String strSql_C01 = "CREATE TABLE T_COMPANY(COMPANY_CODE NUMBER PRIMARY KEY, COMPANY_NAME STRING, INDUSTRIAL_CATEGORY STRING, DATA_TYPE NUMBER, DATA_VERSION STRING)";
			String strSql_C02 = "CREATE TABLE T_FINANCIAL_STATEMENT (COMPANY_CODE NUMBER NOT NULL, COMPANY_NAME STRING NOT NULL,ACCOUNT_YEAR INTEGER NOT NULL,SALES FLOAT,SALES_COST FLOAT,OPERATING_COST FLOAT,NONSALES_INCOME FLOAT,NONSALES_COST FLOAT,SPECIAL_INCOME FLOAT,SPECIAL_COST FLOAT,TAX FLOAT,CASH FLOAT,SALES_CREDIT FLOAT,SECURITIES FLOAT,INVENTORY FLOAT,OTHER_LIQUID_ASSETS FLOAT,EQUIPMENT FLOAT,LAND FLOAT,CONSTRUCTION_ACCOUNT FLOAT,INTANGIBLE_FIXED_ASSETS FLOAT,INVESTMENT FLOAT,DEFERRED_ASSETS FLOAT,ACCOUNT_SPAYABLE FLOAT,SHORT_TERM_DEBT FLOAT,OTHER_LIQUID_DEBT FLOAT,LONG_TERM_DEBT FLOAT,OTHER_LONGTERM_DEBT FLOAT,CAPITAL FLOAT,RESERVE_FUND FLOAT,SURPLUS FLOAT, CONSTRAINT T_FINANCIAL_STATEMENT_P PRIMARY KEY (COMPANY_CODE, ACCOUNT_YEAR))";
			String strSql_I01 = "INSERT INTO T_COMPANY(COMPANY_CODE, COMPANY_NAME, INDUSTRIAL_CATEGORY, DATA_TYPE, DATA_VERSION) VALUES(1, '富士通株式会社', '通信情報システム', 0, null)";

			Cursor cr = dbAccess.getSQLResultCursor(strSql_S01);
//			cr.moveToLast();
//			Toast.makeText(getApplicationContext(), Integer.toString(cr.getCount()), Toast.LENGTH_SHORT).show();
			if (cr.getCount() < 0) {
				dbAccess.execSQL(strSql_D01);
				dbAccess.execSQL(strSql_D02);
				dbAccess.execSQL(strSql_C01);
				dbAccess.execSQL(strSql_C02);
//				dbAccess.execSQL(strSql_I01);
				Toast.makeText(getApplicationContext(), "テーブルを初期化しました", Toast.LENGTH_SHORT).show();
			}
		}catch(Exception ex){
			Toast.makeText(getApplicationContext(), "Err:createInitialTables" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void setRegistedCompanyToBtn(){
		try{
			String strSql_S01 = "SELECT COMPANY_CODE, COMPANY_NAME, INDUSTRIAL_CATEGORY, DATA_TYPE FROM T_COMPANY";
			Cursor cr = dbAccess.getSQLResultCursor(strSql_S01);

			if (cr.getCount() > 0) {
				while(cr.moveToNext()){
					final String sCompanyCode=cr.getString(cr.getColumnIndex("COMPANY_CODE"));
					{
						Button bt;
						bt = new Button(this);
						bt.setId(Integer.valueOf(sCompanyCode));
						bt.setText(cr.getString(cr.getColumnIndex("COMPANY_NAME")));
						bt.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// インテントのインスタンス生成
								Intent intent = new Intent(CompanyList.this, FinanceList.class);
								intent.putExtra("keyword", sCompanyCode);

								// 次画面のアクティビティ起動
								startActivity(intent);
							}});
						llCompanyList.addView(bt);
					}
				}

			}else{
			}
		}catch(Exception ex){
			Toast.makeText(getApplicationContext(), "Err:setRegistedCompanyToBtn" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}

	}



}