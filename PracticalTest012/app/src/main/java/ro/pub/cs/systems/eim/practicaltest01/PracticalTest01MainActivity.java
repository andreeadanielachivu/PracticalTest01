package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private EditText left_text = null;
    private EditText right_text = null;
    private Button leftBtn = null;
    private Button rightBtn = null;
    private Button goBtn = null;
    private int leftButtonInc = 0;
    private int rightButtonInc = 0;
    private int serviceStatus = Constants.SERVICE_STOPPED;
    private ButtonListener btnListener = new ButtonListener();
    private final static int SECONDARY_ACTIVITY = 1;

    private IntentFilter intentFilter = new IntentFilter();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    public class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Broadcast receiver]", intent.getStringExtra("message"));
        }
    }

    public class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.left_button:
                    leftButtonInc++;
                    left_text.setText(String.valueOf(leftButtonInc));
                    break;
                case R.id.right_button:
                    rightButtonInc++;
                    right_text.setText(String.valueOf(rightButtonInc));
                    break;
                case R.id.goToSecondaryMenu:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    int sum = leftButtonInc + rightButtonInc;
                    intent.putExtra("clicks", sum);
                    startActivityForResult(intent, SECONDARY_ACTIVITY);
                    break;
                default:
                    break;
            }
            if (leftButtonInc + rightButtonInc > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", leftButtonInc);
                intent.putExtra("secondNumber", rightButtonInc);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
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

        left_text = (EditText)findViewById(R.id.left_edit_text);
        right_text = (EditText)findViewById(R.id.right_edit_text);
        leftBtn = (Button)findViewById(R.id.left_button);
        rightBtn = (Button)findViewById(R.id.right_button);
        goBtn = (Button)findViewById(R.id.goToSecondaryMenu);
        leftBtn.setOnClickListener(btnListener);
        rightBtn.setOnClickListener(btnListener);
        goBtn.setOnClickListener(btnListener);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("leftText")) {
                left_text.setText(savedInstanceState.get("leftText").toString());
            } else {
                left_text.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("rightText")) {
                right_text.setText(savedInstanceState.get("rightText").toString());
            } else {
                right_text.setText(String.valueOf(0));
            }
        }
        int index;
        for (index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState == null) {
            return;
        }
        outState.putString("leftText", left_text.getText().toString());
        outState.putString("rightText", right_text.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState == null) {
            return;
        }
        if (savedInstanceState.containsKey("leftText")) {
            left_text.setText(savedInstanceState.get("leftText").toString());
        } else {
            left_text.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightText")) {
            right_text.setText(savedInstanceState.get("rightText").toString());
        } else {
            right_text.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SECONDARY_ACTIVITY) {
            Toast.makeText(getApplicationContext(), "The result code was" + resultCode + " and the result of clicks: " + data.getExtras().getInt("numberClicks"), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
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
}
