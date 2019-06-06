# 状态设计模式

## 一．应用场景与案例描述

​		我们经常在课余时间玩游戏以放松身心，缓解压力。在很多游戏中英雄根据不同的体力值，可以做出不同的动作来面对敌人。

​		英雄可以进行休息、防御、普通攻击、技能攻击。而消耗或恢复的体力值是不同的，例如：

1. 休息：恢复体力值至最大，体力值为最大时无需休息；

2. 防御：消耗10点体力值，没有体力值无法进行防御；
3.  普通攻击：消耗15点体力值，没有体力值无法进行普通攻击；
4.  技能攻击：消耗20点体力值，没有体力值无法进行技能攻击；



## 二．案例分析与解决问题

​		倘若我们将这些操作逻辑都写在同一个类——英雄类中作为英雄的方法的话，会出现以下问题：

1. 后期如果操作逻辑（如消耗的体力值变化）有所改变就需要修改整个英雄类；
2. 后期如果要增加操作（如增加魔法攻击）的话需要修改整个英雄类；
3. 只能堆砌非常多的if—else if—else，代码不好维护，可读性很差；



​		所以我决定使用状态模式以解决这个问题。状态模式在本情景下所体现出来的优点：

1. 使用一个类封装对象的一种状态（操作），很容易添加新的状态（操作）；
2. 在状态模式中，环境（Context）——英雄类（Hero）中不必出现大量的条件判断语句。环境（Context）实例所呈现的状态变得更加清晰、容易理解；
3. 使用状态模式可以让用户程序很方便地切换环境（Context）——英雄类（Hero）实例的状态（操作）；
4. 使用状态模式不会让环境（Context）——英雄类（Hero）的实例中出现内部状态不一致的情况；
5. 当状态对象没有实例变量时，环境（Context）——英雄类（Hero）的各个实例可以共享一个状态对象；

 

我将英雄的操作抽象为状态，四种操作对应着四种不同的具体状态： 

* RestState（休息状态）
* DefenseState（防御状态）
* NormalAttackState（普通攻击状态）
* SkillAttackState（技能攻击状态）



## 三．各个角色描述与UML图示

#### 角色描述：

1. 环境（Context）：Hero类
2.  抽象状态（State）：State抽象类
3. 具体状态（Concrete State）：RestState、DefenseState、NormalAttackState和SkillAttackState类



## 五．程序完整源代码

#### 1. 环境（Context）

本设计中，环境角色是Hero类，代码如下：

 **Hero.java**

```java
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


```



#### 2. 抽象状态（State）

对于本问题，抽象状态（State）是State抽象类，代码如下：

**State.java**

```java
package com.yuebanquan.statepattern;

public abstract class State {
    public abstract void toDo();
}
```



### 3. 具体状态（Concrete State）

对于本问题，共有4个具体状态角色，分别是RestState、DefenseState、NormalAttackState和SkillAttackState类，代码如下：

**RestState.java**

```java
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
```



**DefenseState.java**

```java
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
```



**NormalAttackState.java**

```java
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
```



**SkillAttackState.java**

```java
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
```



### 4. 其他类

扩充一个ToastShow类来解决Android自带的Toast显示延迟问题，代码如下：

**Toast.java**

```java
package com.yuebanquan.statepattern;

import android.content.Context;
import android.widget.Toast;

public class ToastShow {
    private Context context;  //在此窗口提示信息

    private Toast toast = null;  //用于判断是否已有Toast执行
    public ToastShow(Context context) {
        this.context = context;
    }
    public void toastShow(String text) {
        if(toast == null)
        {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);  //正常执行
        }
        else {
            toast.setText(text);  //用于覆盖前面未消失的提示信息
        }
        toast.show();
    }
}
```



### 5. Android的布局文件

布局文件位于app/src/main/res/layout，代码如下：

**activity_main.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".Hero">

    <!--占位-->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="100dp" />

    <!--英雄名-->
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="英雄名："
            android:textSize="30sp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="月半泉"
            android:textSize="30sp"/>
    </LinearLayout>

    <!--体力值-->
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="体力值："
            android:textSize="30sp"/>

        <TextView
            android:id="@+id/t_MP"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="100"
            android:textSize="30sp"/>
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="/100"
            android:textSize="30sp"/>
    </LinearLayout>

    <!--状态-->
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="状态："
            android:textSize="30sp"/>

        <TextView
            android:id="@+id/t_state"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="休息状态"
            android:textSize="30sp"/>
    </LinearLayout>

    <!--&lt;!&ndash;占位&ndash;&gt;-->
    <!--<TextView-->
        <!--android:layout_width="match_parent"-->
        <!--android:layout_height="100dp" />-->

    <pl.droidsonroids.gif.GifImageView
        android:id="@+id/doingGif"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_gravity="center"
        android:src="@drawable/rest"/>

    <Button
        android:id="@+id/b_rest"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="进行休息"
        android:textSize="30sp" />

    <Button
        android:id="@+id/b_defense"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="进行防御"
        android:textSize="30sp"/>

    <Button
        android:id="@+id/b_normalAttack"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="普通攻击"
        android:textSize="30sp"/>

    <Button
        android:id="@+id/b_skillAttack"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="技能攻击"
        android:textSize="30sp"/>

</LinearLayout>
```



### 6. Android程序声明文件

程序声明文件位于app/src/,代码如下：

**AndroidManifest.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.yuebanquan.statepattern">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".Hero">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```



### 7. Android的build.gradle

位置在app/，指定生成的apk名称以及引入的模块，代码如下：

**build.gradle**

```gradle
apply plugin: 'com.android.application'

android {
    compileSdkVersion 28
    defaultConfig {
        applicationId "com.yuebanquan.statepattern"
        minSdkVersion 15
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

android.applicationVariants.all {
    variant ->
        variant.outputs.all {
            // 此处指定生成的apk文件名
            outputFileName = "蔡佳泉大作业(状态模式).apk"
        }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.android.support:support-vector-drawable:28.0.0'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    //显示GifView模块
    implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.7'
}
```



