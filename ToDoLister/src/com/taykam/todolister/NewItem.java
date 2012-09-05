package com.taykam.todolister;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NewItem extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_item, menu);
        return true;
    }
}
