package com.html5gamebook.android;

import playn.android.*;
import playn.core.PlayN;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import com.html5gamebook.core.IntegratedView;
import android.util.Log;

public class IntegratedViewActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("com/html5gamebook/resources");
    PlayN.run(new IntegratedView());
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    LinearLayout linearLayout = viewLayout();
    linearLayout.setOrientation(LinearLayout.VERTICAL);    
    TextView txtView = new TextView(context());
    txtView.setText("This is a text view.");
    linearLayout.addView(txtView, 0);

    Button btn = new Button(context());
    btn.setText("Click Me");
    linearLayout.addView(btn);
    gameView().setLayoutParams(new LinearLayout.LayoutParams(600, 600));

  }

 }
