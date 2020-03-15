package com.blubber.snek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blubber.snek.entity.EntityDirection;
import com.blubber.snek.enums.ColorPalette;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GameBoardView gameBoardView;
    DPadListener dPadListener = new DPadListener();
    Map<ImageView, EntityDirection> buttonDirectionMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameBoardView = findViewById(R.id.game_board);
        gameBoardView.getRootView().setBackgroundColor(ColorPalette.PURPLE.getRgb());
        buttonDirectionMap.put((ImageView)(findViewById(R.id.up_button)), EntityDirection.UP);
        buttonDirectionMap.put((ImageView)(findViewById(R.id.down_button)), EntityDirection.DOWN);
        buttonDirectionMap.put((ImageView)(findViewById(R.id.left_button)), EntityDirection.LEFT);
        buttonDirectionMap.put((ImageView)(findViewById(R.id.right_button)), EntityDirection.RIGHT);

        for (ImageView imageView : buttonDirectionMap.keySet()){
            imageView.setBackgroundColor(ColorPalette.PEACH.getRgb());
            imageView.setOnClickListener(dPadListener);
        }
    }

    private class DPadListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            gameBoardView.changeSnakeDirection(buttonDirectionMap.get((ImageView) v));
            gameBoardView.move();
            gameBoardView.invalidate();
        }
    }
}
