package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private TextView sumText = null;
    private Button okBtn = null;
    private Button cancelBtn = null;
    private int numberOfClicks = 0;

    private ButtonListener buttonListener = new ButtonListener();
    public class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.leftBtnSec:
                    Intent intent = new Intent();
                    intent.putExtra("numberClicks", numberOfClicks);
                    setResult(RESULT_OK, intent);
                    break;
                case R.id.rightBtnSec:
                    intent = new Intent();
                    intent.putExtra("numberClicks", numberOfClicks);
                    setResult(RESULT_CANCELED, intent);
                    break;
            }
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        sumText = (TextView)findViewById(R.id.counter_view);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("clicks")) {
            numberOfClicks = intent.getIntExtra("clicks", -1);
            sumText.setText(String.valueOf(numberOfClicks));
        }

        okBtn = (Button)findViewById(R.id.leftBtnSec);
        cancelBtn = (Button)findViewById(R.id.rightBtnSec);
        okBtn.setOnClickListener(buttonListener);
        cancelBtn.setOnClickListener(buttonListener);
    }

}
