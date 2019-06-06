package com.yuebanquan.statepattern;

import android.widget.Toast;

public class RestState extends State {
    Hero hero;

    RestState(Hero hero) {
        this.hero = hero;
    }

    public void toDo() {
        if (hero.getMP() == hero.MAXMP) { //体力值已满
            //hero.midToast("体力值已满，无需休息", Toast.LENGTH_SHORT);
            hero.toast.toastShow("体力值已满，无需休息");
            hero.setState(hero.getLastState());
        }
        else { //进入休息状态
            hero.setMP(hero.MAXMP); //体力值设为最大值100
            hero.setState(hero.getRestState());
            hero.t_state.setText("休息状态");
            hero.doingGif.setImageResource(R.drawable.rest);
            //hero.midToast("进行休息，体力值达到上限", Toast.LENGTH_SHORT);
            hero.toast.toastShow("进行休息，体力值达到上限");
        }
    }

}
