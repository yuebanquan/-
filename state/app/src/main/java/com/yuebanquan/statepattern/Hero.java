package com.yuebanquan.statepattern;

        import android.content.Context;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import pl.droidsonroids.gif.GifImageView;

public class Hero extends AppCompatActivity implements View.OnClickListener {
    State state;        //当前状态
    State lastState;    //上一个状态
    private State restState;    //休息状态
    private State defenseState; //防御状态
    private State normalAttackState;    //普通攻击状态
    private State skillAttackState;     //技能攻击状态

    private int MP;     //英雄的体力值
    int MAXMP = 100;    //最大体力值
    int MINMP = 0;      //最小体力值

    //声明界面的组件
    TextView t_MP;
    TextView t_state;
    Button b_rest;
    Button b_defense;
    Button b_normalAttack;
    Button b_skillAttack;
    GifImageView doingGif;

    //声明一个Toast
    ToastShow toast = new ToastShow(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(MAXMP);

    }

    /*************
     * 初始化Hero
     *************/
    public void init(int MP) {
        this.MP = MP;

        //初始化各种状态
        restState = new RestState(this);
        defenseState = new DefenseState(this);
        normalAttackState = new NormalAttackState(this);
        skillAttackState = new SkillAttackState(this);

        //英雄默认为休息状态
        state = restState;
        lastState = restState;

        //绑定界面的组件
        t_MP = (TextView) findViewById(R.id.t_MP);
        t_state = (TextView) findViewById(R.id.t_state);
        b_rest = (Button) findViewById(R.id.b_rest);
        b_defense = (Button) findViewById(R.id.b_defense);
        b_normalAttack = (Button) findViewById(R.id.b_normalAttack);
        b_skillAttack = (Button) findViewById(R.id.b_skillAttack);
        doingGif = (GifImageView) findViewById(R.id.doingGif);

        //给几个按钮设置监听器
        b_rest.setOnClickListener(this);
        b_defense.setOnClickListener(this);
        b_normalAttack.setOnClickListener(this);
        b_skillAttack.setOnClickListener(this);
    }


    //监听器
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_rest:
                setLastState(getState());
                setState(getRestState());
                state.toDo();
                break;
            case R.id.b_defense:
                setLastState(getState());
                setState(getDefenseState());
                state.toDo();
                break;
            case R.id.b_normalAttack:
                setLastState(getState());
                setState(getNormalAttackState());
                state.toDo();
                break;
            case R.id.b_skillAttack:
                setLastState(getState());
                setState(getSkillAttackState());
                state.toDo();
                break;
        }
    }

    void midToast(String str, int showTime) {
        Toast toast = Toast.makeText(Hero.this, str, showTime);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.show();
    }


    //各种getter和setter
    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
        t_MP.setText(String.valueOf(getMP()));
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public State getLastState() {
        return lastState;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }

    public State getRestState() {
        return restState;
    }

    public State getDefenseState() {
        return defenseState;
    }

    public State getNormalAttackState() {
        return normalAttackState;
    }

    public State getSkillAttackState() {
        return skillAttackState;
    }
}
