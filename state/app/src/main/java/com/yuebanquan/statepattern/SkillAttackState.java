package com.yuebanquan.statepattern;

import android.widget.Toast;

public class SkillAttackState extends State {
    Hero hero;

    SkillAttackState(Hero hero) {
        this.hero = hero;
    }

    public void toDo() {
        if (hero.getMP() < (hero.MINMP + 20)) { //体力值不够
            //hero.midToast("体力值不够，无法技能攻击", Toast.LENGTH_SHORT);
            hero.toast.toastShow("体力值不够，无法技能攻击");
            hero.setState(hero.getLastState());
        }
        else {  //进入技能攻击状态
            hero.setMP(hero.getMP() - 20); //技能攻击体力值-20
            hero.setState(hero.getSkillAttackState());
            hero.t_state.setText("技能攻击状态");
            hero.doingGif.setImageResource(R.drawable.skill_attack);
            //hero.midToast("发出技能攻击，体力值-20", Toast.LENGTH_SHORT);
            hero.toast.toastShow("发出技能攻击，体力值-20");
        }
    }
}
