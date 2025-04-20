package com.main.lutemon12;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Storage storage = Storage.getInstance();
        Lutemon testLutemon1 = new FireLutemon(Storage.getNextId(),"fireball");
        Lutemon testLutemon2 = new WaterLutemon(Storage.getNextId(),"waterball");
        storage.addLutemon(testLutemon1);
        storage.addLutemon(testLutemon2);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    Fragment selectedFragment = null;
                    boolean isBattleFragment = false;


                    if (itemId == R.id.nav_home) {
                        selectedFragment = new HomeFragment();
                    } else if (itemId == R.id.nav_garden) {
                        selectedFragment = new GardenFragment();
                    } else if (itemId == R.id.nav_battle) {
                        selectedFragment = new BattleFragment();
                        isBattleFragment = true;
                    } else if (itemId == R.id.nav_training) {
                        selectedFragment = new TrainingFragment();
                    }
                    if (!isBattleFragment) {
                        Storage.getInstance().restoreAllHealth();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                        return true;
                    }
                    return false;
                }
            };
}