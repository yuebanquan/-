package com.yuebanquan.statepattern;

import android.widget.Toast;

public class NormalAttackState extends State {
    Hero hero;

    NormalAttackState(Hero hero) {
        this.hero = hero;
    }

    public void toDo() {
        if (hero.getMP() < (hero.MINMP + 15)) { //体力值不够
            //hero.midToast("体力值不够，无法普通攻击", Toast.LENGTH_SHORT);
            hero.toast.toastShow("体力值不够，无法普通攻击");
            hero.setState(hero.getLastState());
        } else {  //进入普通攻击状态
            hero.setMP(hero.getMP() - 15); //普通攻击体力值-15
            hero.setState(hero.getNormalAttackState());
            hero.t_state.setText("普通攻击状态");
            hero.doingGif.setImageResource(R.drawable.normal_attack);
            //hero.midToast("发出普通攻击，体力值-15", Toast.LENGTH_SHORT);
            hero.toast.toastShow("发出普通攻击，体力值-15");
        }
    }
}
