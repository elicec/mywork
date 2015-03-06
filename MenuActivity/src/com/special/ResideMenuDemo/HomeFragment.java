package com.special.ResideMenuDemo;

import android.os.Bundle;


import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elicec.maincode.MySqlLiteDb;
import com.elicec.maincode.ShakeListener;
import com.special.ResideMenu.ResideMenu;


public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
	private TextView jokeContent;
	private  MySqlLiteDb mDataBase;
	private ImageView img_shake;
	private Animation shake;
	private ShakeListener shakeListener;
	private Animation text_in;
	private Vibrator vibrator;
	private RelativeLayout btn_favourites;
	private RelativeLayout btn_copy;
	private RelativeLayout btn_share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home_laugheveryday, container, false);
        this.mDataBase = new MySqlLiteDb(getActivity());
        this.vibrator = ((Vibrator)getActivity().getApplication().getSystemService("vibrator"));
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        
        jokeContent = ((TextView)this.parentView.findViewById(R.id.joke_content));
        jokeContent.setText(this.mDataBase.getJokeContent());
        img_shake = ((ImageView)this.parentView.findViewById(R.id.shake));
        this.text_in = AnimationUtils.loadAnimation(getActivity(), R.anim.flip_vertical_in);
        this.shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shakeoneshake);
        this.shake.setFillAfter(true);
        this.img_shake.startAnimation(this.shake);
        this.shakeListener = new ShakeListener(getActivity());
        
        this.shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener()
        {
          public void onShake()
          {
            HomeFragment.this.shake.setFillAfter(true);
            HomeFragment.this.img_shake.startAnimation(HomeFragment.this.shake);
            HomeFragment.this.vibrator.vibrate(new long[] { 300, 200, 300, 200 }, -1);
            HomeFragment.this.jokeContent.setText(HomeFragment.this.mDataBase.getJokeContent());
            HomeFragment.this.jokeContent.startAnimation(HomeFragment.this.text_in);
            HomeFragment.this.shakeListener.stop();
            new Handler().postDelayed(new Runnable()
            {
              public void run()
              {
                HomeFragment.this.vibrator.cancel();
                HomeFragment.this.shakeListener.start();
              }
            }
            , 1500);
          }
        });
        
        this.btn_favourites = ((RelativeLayout)this.parentView.findViewById(R.id.joke_btn_favorites));
        this.btn_copy = ((RelativeLayout)this.parentView.findViewById(R.id.joke_btn_copy));
        this.btn_share = ((RelativeLayout)this.parentView.findViewById(R.id.joke_btn_share));

        this.btn_favourites.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setBackgroundResource(R.drawable.bg_list_item_btn);
          }
        });
        this.btn_copy.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setBackgroundResource(R.drawable.bg_list_item_btn);
          }
        });
        this.btn_share.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setBackgroundResource(R.drawable.bg_list_item_btn);
          }
        });
    }

}
