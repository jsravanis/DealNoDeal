package com.example.dealnodeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView chooseCaseText;
    ImageView[] boxImages, rewardImages;
    boolean[] isBoxClicked;
    Button resetButton, dealButton, noDealButton;
    ArrayList<Integer> boxImageViewIds, rewardImageViewIds, boxImageIds, openBoxImageIds, rewardImageIds, canceledRewardImageIds, rewardPrices;
    HashMap<Integer, Integer> rewardPriceIdMap;
    int counter, round;
    DecimalFormat decimalFormat;
    String[] chooseCaseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = 4;
        round=1;
        decimalFormat = new DecimalFormat("#.##");
        chooseCaseArray = getResources().getStringArray(R.array.choose_array);

        chooseCaseText = findViewById(R.id.chooseCaseText);
        resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);
        dealButton = findViewById(R.id.dealButton);
        dealButton.setOnClickListener(this);
        noDealButton = findViewById(R.id.noDealButton);
        noDealButton.setOnClickListener(this);

        chooseCaseText.setText(chooseCaseArray[counter]);
        dealButton.setVisibility(View.INVISIBLE);
        noDealButton.setVisibility(View.INVISIBLE);

        boxImageViewIds = new ArrayList<>(Arrays.asList(R.id.box1, R.id.box2, R.id.box3, R.id.box4, R.id.box5, R.id.box6,
                R.id.box7, R.id.box8, R.id.box9, R.id.box10));

        rewardImageViewIds = new ArrayList<>(Arrays.asList(R.id.reward1, R.id.reward2, R.id.reward3, R.id.reward4,
                R.id.reward5, R.id.reward6, R.id.reward7, R.id.reward8, R.id.reward9, R.id.reward10));

        boxImageIds = new ArrayList<>(Arrays.asList(R.drawable.suitcase_position_1, R.drawable.suitcase_position_2, R.drawable.suitcase_position_3,
                R.drawable.suitcase_position_4, R.drawable.suitcase_position_5, R.drawable.suitcase_position_6, R.drawable.suitcase_position_7,
                R.drawable.suitcase_position_8, R.drawable.suitcase_position_9, R.drawable.suitcase_position_10));

        openBoxImageIds = new ArrayList<>(Arrays.asList(R.drawable.suitcase_open_1, R.drawable.suitcase_open_10, R.drawable.suitcase_open_50,
                R.drawable.suitcase_open_100, R.drawable.suitcase_open_300, R.drawable.suitcase_open_1000, R.drawable.suitcase_open_10000,
                R.drawable.suitcase_open_50000, R.drawable.suitcase_open_100000, R.drawable.suitcase_open_500000));

        rewardImageIds = new ArrayList<>(Arrays.asList(R.drawable.reward_1, R.drawable.reward_10, R.drawable.reward_50,
                R.drawable.reward_100, R.drawable.reward_300, R.drawable.reward_1000, R.drawable.reward_10000,
                R.drawable.reward_50000, R.drawable.reward_100000, R.drawable.reward_500000));

        canceledRewardImageIds = new ArrayList<>(Arrays.asList(R.drawable.reward_open_1, R.drawable.reward_open_10, R.drawable.reward_open_50,
                R.drawable.reward_open_100, R.drawable.reward_open_300, R.drawable.reward_open_1000, R.drawable.reward_open_10000,
                R.drawable.reward_open_50000, R.drawable.reward_open_100000, R.drawable.reward_open_500000));

        rewardPrices = new ArrayList<>(Arrays.asList(1, 10, 50, 100, 300, 1000, 10000, 50000, 100000, 500000));

        rewardPriceIdMap = new HashMap<>();
        for(int i=0;i<10; i++){
            rewardPriceIdMap.put(rewardPrices.get(i), i);
        }

        Collections.shuffle(rewardPrices);

        boxImages = new ImageView[10];
        isBoxClicked = new boolean[10];
        for(int i=0; i<10; i++){
            boxImages[i] = findViewById(boxImageViewIds.get(i));
            boxImages[i].setOnClickListener(this);
            boxImages[i].setClickable(true);
            isBoxClicked[i] = false;
        }

        rewardImages = new ImageView[10];
        for(int i=0; i<10; i++){
            rewardImages[i] = findViewById(rewardImageViewIds.get(i));
        }

    }

    @Override
    public void onClick(View v) {
        if(boxImageViewIds.contains(v.getId())){
            int index = boxImageViewIds.indexOf(v.getId());
            if(boxImages[index].isClickable()){
                Integer rewardPrice = rewardPrices.get(index);
                Integer id = rewardPriceIdMap.get(rewardPrice);
                boxImages[index].setImageResource(openBoxImageIds.get(id));
                boxImages[index].setClickable(false);
                isBoxClicked[index] = true;
                rewardImages[id].setImageResource(canceledRewardImageIds.get(id));
                counter--;
                if(counter != 0){
                    chooseCaseText.setText(chooseCaseArray[counter]);
                }
                else {
                    double finalReward =0, sum=0, count=0;
                    for(int i=0; i<10; i++){
                        if(boxImages[i].isClickable()){
                            sum += rewardPrices.get(i);
                            count++;
                        }
                        boxImages[i].setClickable(false);
                    }
                    if(round < 3){
                        finalReward = (sum/count) * 0.6;
                        chooseCaseText.setText(chooseCaseArray[counter] + decimalFormat.format(finalReward));
                        dealButton.setVisibility(View.VISIBLE);
                        noDealButton.setVisibility(View.VISIBLE);
                    }
                    else{
                        finalReward = sum;
                        chooseCaseText.setText(chooseCaseArray[5] + " $" + finalReward);
                    }
                }
            }
        }
        else if(v.getId() == R.id.resetButton){
            counter = 4;
            round = 1;
            chooseCaseText.setText(chooseCaseArray[counter]);
            for(int i=0; i<10; i++){
                boxImages[i].setImageResource(boxImageIds.get(i));
                boxImages[i].setClickable(true);
                isBoxClicked[i] = false;
            }
            for(int i=0; i<10; i++){
                rewardImages[i].setImageResource(rewardImageIds.get(i));
            }
        }
        else if (v.getId() == R.id.dealButton) {
            String[] reward = chooseCaseText.getText().toString().split(" ");
            chooseCaseText.setText(chooseCaseArray[5] + " " + reward[reward.length - 1]);
            dealButton.setVisibility(View.INVISIBLE);
            noDealButton.setVisibility(View.INVISIBLE);
        }
        else if (v.getId() == R.id.noDealButton) {
            round++;
            counter = round < 3 ? 4 : 1;
            chooseCaseText.setText(chooseCaseArray[counter]);
            for(int i=0; i<10; i++){
                boxImages[i].setClickable(!isBoxClicked[i]);
            }
            dealButton.setVisibility(View.INVISIBLE);
            noDealButton.setVisibility(View.INVISIBLE);
        }
    }
}