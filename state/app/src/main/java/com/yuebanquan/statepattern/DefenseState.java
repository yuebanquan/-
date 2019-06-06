package com.yuebanquan.statepattern;

import android.widget.Toast;

public class DefenseState extends State {
    Hero hero;

    DefenseState(Hero hero) {
        this.hero = hero;
    }

    public void toDo() {
        if (hero.getMP() < (hero.MINMP + 10)) { //体力值不够
            //hero.midToast("体力值不够，无法防御", Toast.LENGTH_SHORT);
            hero.toast.toastShow("体力值不够，无法防御");
            hero.setState(hero.getLastState());
        }
        else {  //进入防御状态
            hero.setMP(hero.getMP() - 10); //防御体力值-10
            hero.setState(hero.getDefenseState());
            hero.t_state.setText("防御状态");
            hero.doingGif.setImageResource(R.drawable.defense);
            //hero.midToast("进行防御，体力值-10", Toast.LENGTH_SHORT);
            hero.toast.toastShow("进行防御，体力值-10");
        }
    }

}
