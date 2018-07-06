package com.wyq.firehelper.developKit.EventBus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wyq.firehelper.R;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developkit_activity_eventbus_layout);
    }


    private String defectStr = "由于是Event,在发布Event的时候就要做好准备可能并没有人接受这个Event, Subscribe的时候也要做好准备可能永远不会收到Event。Event无论顺序还是时间上都某种程度上不太可控。如果你将数据寄托在Event上然后就直接在Android其他生命周期方法中直接使用这个数据或成员变量。那么很有可能你会得到NPE。\n" +
            "EventBus看似将你的程序解耦，但是又有些过了。我们常常使用EventBus传数据，这已经是Dependency级别的数据而不是一个可以被解耦出来的模块。这样就造成了过多EventBus的代码会造成代码结构混乱，难以测试和追踪，违背了解耦的初衷。这时如果有意或无意的造成了Nested Event。那情况会更糟。\n";
}
