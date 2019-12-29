package com.app.instagramclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    View bottomBadgeView;
    ViewPager viewPager;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottombar);
        bottomBadgeView = getBottomNavigationItemView();
        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setListenerToBottomNavigationView();
        setNotificationBadge(100);
        viewPager.setAdapter(new ScreenPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;
            @Override
            public void transformPage(@NonNull View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0f);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0f);
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_search);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_add);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_activty);
                        break;
                    case 4:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.instagram_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // inflate notification layout
    private View getBottomNavigationItemView() {
        return LayoutInflater.from(this).inflate(R.layout.notification_badge, bottomNavigationView, false);
    }

    // set notification badge to bottom_activity
    public void setNotificationBadge(int numberOfNotification) {
        BottomNavigationItemView bottomNavigationItemView;
        View bottomActivityView = findViewById(R.id.bottom_activty);
        bottomNavigationItemView = (BottomNavigationItemView) bottomActivityView;
        if (numberOfNotification == 0) {
            // remove notification
            bottomNavigationItemView.removeView(bottomBadgeView);
        }
        if (numberOfNotification >= 1) {

            ((TextView) bottomBadgeView.findViewById(R.id.badge_textview)).setText(String.valueOf(numberOfNotification));
            bottomNavigationItemView.addView(bottomBadgeView, 100, 90);
        }
    }

    // listener for BottomNavigationView
    public void setListenerToBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.bottom_activty:
                        viewPager.setCurrentItem(3);
                        setNotificationBadge(0);
                        break;
                    case R.id.bottom_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_add:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.bottom_profile:
                        viewPager.setCurrentItem(4);
                        break;
                    case R.id.bottom_search:
                        viewPager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });
    }

    private class ScreenPagerAdapter extends FragmentStatePagerAdapter {

        public ScreenPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new InstagramFragment(R.layout.home_fragment);
                case 1:
                    return new InstagramFragment(R.layout.search_fragment);
                case 2:
                    return new InstagramFragment(R.layout.add_fragment);
                case 3:
                    return new InstagramFragment(R.layout.activity_fragment);
                case 4:
                    return new InstagramFragment(R.layout.profile_fragment);


            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}