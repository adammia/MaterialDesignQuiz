package adammia.example.com.mdquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import static java.lang.Boolean.FALSE;

/**
 * Created by adammia on 2017. 03. 21..
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //**Insert the intro text into WebView
        WebView webview = (WebView) findViewById(R.id.webview_intro1);
        String intro = "<p align='justify'>" + getString(R.string.intro1) + "</p>";
        webview.setVerticalScrollBarEnabled(FALSE);
        webview.loadData(intro, "text/html", null);

        webview = (WebView) findViewById(R.id.webview_intro2);
        String intro1 = "<p align='justify'>" + getString(R.string.intro2) + "</p>";
        webview.setVerticalScrollBarEnabled(FALSE);
        webview.loadData(intro1, "text/html", null);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mMenuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.material_io) {
            Uri webpage = Uri.parse(getResources().getString(R.string.material_io_link));
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
        }
        if (item.getItemId() == R.id.md_assets) {
            Uri webpage = Uri.parse(getResources().getString(R.string.material_tools_link));
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void clickedButtonStart(View view) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }
}