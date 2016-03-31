package com.example.felipe.safadmetro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editTextDate;
    private TextView textViewPercentAngel;
    private TextView textViewPercentDevil;
    private LinearLayout linearLayoutAngel;
    private LinearLayout linearLayoutDevil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editTextDate = (EditText)findViewById(R.id.editTextDate);
        textViewPercentAngel =(TextView)findViewById(R.id.textViewPercentAngel);
        textViewPercentDevil =(TextView)findViewById(R.id.textViewPercentDevil);
        linearLayoutAngel = (LinearLayout)findViewById(R.id.linearLayoutAngel);
        linearLayoutDevil =(LinearLayout)findViewById(R.id.linearLayoutDevil);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_repo) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/felipemfp/safadometro"));
            startActivity(browserIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buttonCalculateClick(View v) {
        hideKeyboard(this, v);

        String date = editTextDate.getText().toString();

        if (date == null || date.equals("")) {
            Toast.makeText(MainActivity.this, "Insira uma data", Toast.LENGTH_SHORT).show();
        }
        else if (checkDateInput(date)) {
            String[] dateSplitted = date.split("/");
            int day = Integer.parseInt(dateSplitted[0]);
            int month = Integer.parseInt(dateSplitted[1]);
            int year = Integer.parseInt(dateSplitted[2]);

            if (day > 0 && day < 32 && month > 0 && month < 13 && year > 0) {
                double devil, angel;

                if (year > 1900) {
                    year -= 1900;
                }

                if (day == 6 && month == 9 && year == 88) {
                    devil = 1.0;
                    angel = 99.0;
                }
                else {
                    devil = Math.floor((sum(month)) + ((year / 100.0) * (50 - day)));
                    angel = 100 - devil;
                }

                textViewPercentAngel.setText((int)angel + "%");
                textViewPercentDevil.setText((int)devil + "%");

                linearLayoutAngel.setVisibility(View.VISIBLE);
                linearLayoutDevil.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(MainActivity.this, "Data inválida", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Formato inválido", Toast.LENGTH_SHORT).show();
        }
    }

    public int sum(int n) {
        return (1 + n) * ((n - 1 + 1) / 2);
    }

    public boolean checkDateInput(String str) {
        return (str.length() == 8 || str.length() == 10)
                && str.indexOf("/") == 2
                && str.lastIndexOf("/") == 5
                && str.split("/").length == 3;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
