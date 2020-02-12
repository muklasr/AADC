package com.example.android.fragmentexample;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;
    static final String CHOICE = "choice";
    static final String RATING = "rating";
    private int mRadioButtonChoice = NONE;
    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);

        void onRate(float value);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static SimpleFragment newInstance(Bundle args) {
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        final TextView textView = rootView.findViewById(R.id.fragment_header);
        final RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);
        final LinearLayout rating = rootView.findViewById(R.id.rating);

        float mRateValue;

        if (getArguments().containsKey(CHOICE)) {
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            mRateValue = getArguments().getFloat(RATING);
            if (mRadioButtonChoice != 2) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
                ratingBar.setRating(mRateValue);
                switch (mRadioButtonChoice) {
                    case YES:
                        rating.setVisibility(View.VISIBLE);
                        break;
                    case NO:
                        rating.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                switch (index) {
                    case YES:
                        textView.setText(R.string.yes_message);
                        rating.setVisibility(View.VISIBLE);
                        mRadioButtonChoice = YES;
                        mListener.onRadioButtonChoice(YES);
                        break;
                    case NO:
                        textView.setText(R.string.no_message);
                        rating.setVisibility(View.GONE);
                        mRadioButtonChoice = NO;
                        mListener.onRadioButtonChoice(NO);
                        break;
                    default:
                        mRadioButtonChoice = NONE;
                        mListener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mListener.onRate(v);
            }
        });

        return rootView;
    }


}
