package com.tomato830.note_fjm;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tomato830.note_fjm.ContributionChart.GridViewAdapter;
import com.tomato830.note_fjm.ContributionChart.TaskCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class checkIN extends Fragment {

    //create a gridview
    public GridView contibutionView;

    //Create a gridviewAdapter object
    public GridViewAdapter gridAdapter;

    //Taskcalculator object
    TaskCalculator t;

    Button checkInButton;
    TextView textView,showContribution;

    RadioGroup radioGroup;
    RadioButton jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec,thisMonthButton;

    HorizontalScrollView horizontalScrollView;

    ArrayList<Integer> thisMonthVal;
    ArrayList<Integer> janVal ;
    ArrayList<Integer> febVal ;
    ArrayList<Integer> marVal ;
    ArrayList<Integer> aprVal ;
    ArrayList<Integer> mayVal ;
    ArrayList<Integer> junVal ;
    ArrayList<Integer> julVal ;
    ArrayList<Integer> augVal ;
    ArrayList<Integer> sepVal ;
    ArrayList<Integer> octVal ;
    ArrayList<Integer> novVal ;
    ArrayList<Integer> decVal ;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    //签到成功,更新数据
                    int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    thisMonthVal.set(day-1,thisMonthVal.get(day)+1);
                    Log.v("签到之后thismonth",thisMonthVal.toString());
                    Log.v("签到之后dec",decVal.toString());
                    stroeDate();
                    //更新ui
                    t.calculate(janVal, febVal, marVal, aprVal, mayVal, junVal, julVal, augVal, sepVal, octVal, novVal, decVal, 24, 2019);
                    thisMonthButton.callOnClick();
            }
        }
    };

    public checkIN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        //将scrollView滑到指定位置
        final int month=Calendar.getInstance().get(Calendar.MONTH)+1;
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.smoothScrollTo(month*radioGroup.getWidth()/12,0);
                //点击一下这个按钮,刷新grid
                thisMonthButton.callOnClick();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取手机日期并显示
        textView = getActivity().findViewById(R.id.check_in_today_date);
        final Calendar calendar = Calendar.getInstance();
        String mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);        //获取日期的月
        final String mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));      //获取日期的日
        textView.setText(mMonth+"月"+mDay+"日");

        contibutionView = (GridView) getActivity().findViewById(R.id.gridView1);

        //存储贡献数据
        janVal = new ArrayList<Integer>();
        febVal = new ArrayList<Integer>();
        marVal = new ArrayList<Integer>();
        aprVal = new ArrayList<Integer>();
        mayVal = new ArrayList<Integer>();
        junVal = new ArrayList<Integer>();
        julVal = new ArrayList<Integer>();
        augVal = new ArrayList<Integer>();
        sepVal = new ArrayList<Integer>();
        octVal = new ArrayList<Integer>();
        novVal = new ArrayList<Integer>();
        decVal = new ArrayList<Integer>();

        //判断是否第一次打开
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("contributionValue"+String.valueOf(calendar.get(Calendar.YEAR)), Context.MODE_PRIVATE);
        String isFirst=sharedPreferences.getString("isFirst","true");

        //第一次打开则初始化
        if (isFirst=="true"){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("isFirst","false");//是否第一次启动
            editor.putString("year",String.valueOf(calendar.get(Calendar.YEAR)));//本年的年份
            editor.apply();
            int year=calendar.get(Calendar.YEAR);

            //全部贡献值初始化为0
            for (int i=0;i<31;++i) {
                janVal.add(0);
                marVal.add(0);
                mayVal.add(0);
                julVal.add(0);
                augVal.add(0);
                octVal.add(0);
                decVal.add(0);
            }
            for (int i=0;i<28;++i ) febVal.add(0);
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0){//闰年二月多一天
                febVal.add(0);
            }
            for (int i=0;i<30;++i){
                aprVal.add(0);
                junVal.add(0);
                sepVal.add(0);
                novVal.add(0);
            }
            //将贡献值存储好
            stroeDate();
        }
        else {//否则读取贡献值
            String val=sharedPreferences.getString("jan","[]");
            janVal=String2list(val);
            val=sharedPreferences.getString("feb","[]");
            febVal=String2list(val);
            val=sharedPreferences.getString("mar","[]");
            marVal=String2list(val);
            val=sharedPreferences.getString("apr","[]");
            aprVal=String2list(val);
            val=sharedPreferences.getString("may","[]");
            mayVal=String2list(val);
            val=sharedPreferences.getString("jun","[]");
            junVal=String2list(val);
            val=sharedPreferences.getString("jul","[]");
            julVal=String2list(val);
            val=sharedPreferences.getString("aug","[]");
            augVal=String2list(val);
            val=sharedPreferences.getString("sep","[]");
            sepVal=String2list(val);
            val=sharedPreferences.getString("oct","[]");
            octVal=String2list(val);
            val=sharedPreferences.getString("nov","[]");
            novVal=String2list(val);
            val=sharedPreferences.getString("dec","[]");
            decVal=String2list(val);
        }

        //显示获得的贡献点


        //修改今天数据并存储


        //Create a task calculator object with the monthly flag as false for
        //year based contribution chart or true for month based contribution chart.

        //Pass the context, activity, monthly flag

        t = new TaskCalculator(getContext(), this,
                false);


        //Use this method to calculate and show the contributions for the whole year.

        t.calculate(janVal, febVal, marVal, aprVal, mayVal, junVal, julVal, augVal, sepVal, octVal, novVal, decVal, 24, 2019);


        //初始化radioButton和
        jan=(RadioButton) getActivity().findViewById(R.id.jan);
        feb=(RadioButton) getActivity().findViewById(R.id.feb);
        mar=(RadioButton) getActivity().findViewById(R.id.mar);
        apr=(RadioButton) getActivity().findViewById(R.id.apr);
        may=(RadioButton) getActivity().findViewById(R.id.may);
        jun=(RadioButton) getActivity().findViewById(R.id.jun);
        jul=(RadioButton) getActivity().findViewById(R.id.jul);
        aug=(RadioButton) getActivity().findViewById(R.id.aug);
        sep=(RadioButton) getActivity().findViewById(R.id.sep);
        oct=(RadioButton) getActivity().findViewById(R.id.oct);
        nov=(RadioButton) getActivity().findViewById(R.id.nov);
        dec=(RadioButton) getActivity().findViewById(R.id.dec);
        showContribution=(TextView) getActivity().findViewById(R.id.showContribution);

        //点击本月的数据
        thisMonthVal=new ArrayList<>();
        int month=calendar.get(Calendar.MONTH)+1;
        switch (calendar.get(Calendar.MONTH)){
            case 0:
                jan.toggle();
                thisMonthVal=janVal;
                thisMonthButton=jan;
                break;
            case 1:
                feb.toggle();
                thisMonthVal=febVal;
                thisMonthButton=feb;
                break;
            case 2:
                mar.toggle();
                thisMonthVal=marVal;
                thisMonthButton=mar;
                break;
            case 3:
                apr.toggle();
                thisMonthVal=aprVal;
                thisMonthButton=apr;
                break;
            case 4:
                may.toggle();
                thisMonthVal=mayVal;
                thisMonthButton=may;
                break;
            case 5:
                jun.toggle();
                thisMonthVal=junVal;
                thisMonthButton=jun;
                break;
            case 6:
                jul.toggle();
                thisMonthVal=julVal;
                thisMonthButton=jul;
                break;
            case 7:
                aug.toggle();
                thisMonthVal=augVal;
                thisMonthButton=aug;
                break;
            case 8:
                sep.toggle();
                thisMonthVal=sepVal;
                thisMonthButton=sep;
                break;
            case 9:
                oct.toggle();
                thisMonthVal=octVal;
                thisMonthButton=oct;
                break;
            case 10:
                nov.toggle();
                thisMonthVal=novVal;
                thisMonthButton=nov;
                break;
            case 11:
                dec.toggle();
                thisMonthVal=decVal;
                thisMonthButton=dec;
                break;
        }

        //计算本月贡献点
        Integer contri=0;
        for (Integer i:thisMonthVal){
            contri=contri+i;
        }
        showContribution.setText(String.valueOf(calendar.get(Calendar.MONTH)+1)+"月已获得"+String.valueOf(contri)+"个贡献点");

        //初始化HorizontalScrollView和radioGroup
        horizontalScrollView=(HorizontalScrollView) getActivity().findViewById(R.id.horizontalScrollView1);
        radioGroup=(RadioGroup) getActivity().findViewById(R.id.radioGroup1);


        //签到按钮
        checkInButton=(Button) getActivity().findViewById(R.id.checkInButton);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInButton.setText("已签到");
                checkInButton.setClickable(false);
                Toast.makeText(getContext(),"签到成功!",Toast.LENGTH_SHORT).show();

                //签到成功,更新ui
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        });
        //判断今天是否已签到

    }

    //This method is to set/update the gridview adapter

    public void monthlystat(int flag, ArrayList<Integer> a) {

        if (flag == 0) {//判断是否第一次创建

            //Define the gridViewAdapter
            gridAdapter = new GridViewAdapter(getContext(), R.layout.griditem, a);

            //Setting the adapter to the gridview
            contibutionView.setAdapter(gridAdapter);

        } else {

            gridAdapter.updatedata(a);
            gridAdapter.notifyDataSetChanged();

        }

    }

    ArrayList<Integer> String2list(String val){
        val=val.substring(1,val.length()-1);
        ArrayList<String> res=new ArrayList<>(Arrays.asList(val.split(",")));
        ArrayList<Integer> ret=new ArrayList<>();
        for (String s:res){
            ret.add(Integer.decode(s.trim()));//s.trim()去除空格
        }
        return ret;
    }

    void stroeDate(){
        //贡献值写入sharedPreference
        SharedPreferences sp=getActivity().getSharedPreferences("contributionValue"+String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("jan",janVal.toString());
        editor.putString("feb",febVal.toString());
        editor.putString("mar",marVal.toString());
        editor.putString("apr",aprVal.toString());
        editor.putString("may",mayVal.toString());
        editor.putString("jun",junVal.toString());
        editor.putString("jul",julVal.toString());
        editor.putString("aug",augVal.toString());
        editor.putString("sep",sepVal.toString());
        editor.putString("oct",octVal.toString());
        editor.putString("nov",novVal.toString());
        editor.putString("dec",decVal.toString());
        editor.apply();
    }
}
