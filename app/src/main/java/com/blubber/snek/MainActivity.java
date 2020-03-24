package com.blubber.snek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blubber.snek.entity.EntityDirection;
import com.blubber.snek.enums.GameColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    GameBoardView gameBoardView;
    DPadListener dPadListener = new DPadListener();
    Map<ImageView, EntityDirection> buttonDirectionMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameBoardView = findViewById(R.id.game_board);
        gameBoardView.getRootView().setBackgroundColor(GameColor.PURPLE.getRgb());
        buttonDirectionMap.put((ImageView)(findViewById(R.id.up_button)), EntityDirection.UP);
        buttonDirectionMap.put((ImageView)(findViewById(R.id.down_button)), EntityDirection.DOWN);
        buttonDirectionMap.put((ImageView)(findViewById(R.id.left_button)), EntityDirection.LEFT);
        buttonDirectionMap.put((ImageView)(findViewById(R.id.right_button)), EntityDirection.RIGHT);

        for (ImageView imageView : buttonDirectionMap.keySet()){
            imageView.setBackgroundColor(GameColor.PEACH.getRgb());
            imageView.setOnClickListener(dPadListener);
        }
    }

    private class DPadListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            gameBoardView.queueSnakeDirection(Objects.requireNonNull(buttonDirectionMap.get((ImageView) v)));
        }
    }
}
