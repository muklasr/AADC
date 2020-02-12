/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fragmentexample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.android.fragmentexample.SimpleFragment.CHOICE;
import static com.example.android.fragmentexample.SimpleFragment.RATING;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {
    private Button mButton;
    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";
    static final String BUTTON_CHOICE = "button_choice";
    static final String RATE_VALUE = "rate_value";
    private int mRadioButtonChoice = 2;
    private float mRateValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.open_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFragmentDisplayed) {
                    closeFragment();
                } else {
                    displayFragment();
                }
            }
        });
        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                mButton.setText(R.string.close);
            }
            mRadioButtonChoice = savedInstanceState.getInt(BUTTON_CHOICE);
            mRateValue = savedInstanceState.getFloat(RATE_VALUE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        savedInstanceState.putInt(BUTTON_CHOICE, mRadioButtonChoice);
        savedInstanceState.putFloat(RATE_VALUE, mRateValue);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void displayFragment() {
        Bundle args = new Bundle();
        args.putInt(CHOICE, mRadioButtonChoice);
        args.putFloat(RATING, mRateValue);
        SimpleFragment simpleFragment = SimpleFragment.newInstance(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();
        isFragmentDisplayed = true;
        mButton.setText(R.string.close);
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        isFragmentDisplayed = false;
        mButton.setText(R.string.open);
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
        Toast.makeText(this, "Choice is " + choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRate(float value) {
        mRateValue = value;
        Toast.makeText(this, "My Rating:" + value, Toast.LENGTH_SHORT).show();
    }
}
