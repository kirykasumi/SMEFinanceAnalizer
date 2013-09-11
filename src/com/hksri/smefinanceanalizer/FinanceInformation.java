package com.hksri.smefinanceanalizer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FinanceInformation extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_information);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_finance_information, menu);
        return true;
    }
}
