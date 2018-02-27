package view.web.com.br.webview.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import view.web.com.br.webview.R;

public class Splash extends Activity {

    public TextView textViewSplash;

    private Class[] paramTypes = { Integer.TYPE, Integer.TYPE };
    private Method overrideAnimation = null;
    public static final Integer SPLASH_DURATION = 2600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        textViewSplash = (TextView) findViewById(R.id.text_splash);

        try {
            Class<?> activityClass = Class.forName("android.app.Activity");
            overrideAnimation = activityClass.getDeclaredMethod(
                    "overridePendingTransition", paramTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this,
                        Main.class);
                startActivity(i);
                finish();
                if (overrideAnimation != null) {
                    try {
                        overrideAnimation.invoke(Splash.this, android.R.anim.fade_in,
                                android.R.anim.slide_out_right);
                    } catch (IllegalArgumentException e) {
                        Log.v("IllegalArgumentExp: ", e.getMessage());
                    } catch (IllegalAccessException e) {
                        Log.v("IllegalAccessExp: ", e.getMessage());
                    } catch (InvocationTargetException e) {
                        Log.v("InvocationTargetExp: ", e.getMessage());
                    }
                }
            }
        }, SPLASH_DURATION);
    }
}
