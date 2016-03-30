package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by Andreea on 3/30/2016.
 */
public class ProcessingThread extends Thread {

    private Context context = null;
    private double geometricMean;
    private double arithmeticMean;
    private boolean isRunning = true;
    private Random random = new Random();

    public  ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;
        arithmeticMean = (firstNumber + secondNumber) / 2;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        Log.d("[Processing thread]","Thread has started");
        while(isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[Processing thread]","Thread has stopped");
    }

    private void sendMessage() {
        String action = Constants.actionTypes[random.nextInt(Constants.actionTypes.length)];
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
