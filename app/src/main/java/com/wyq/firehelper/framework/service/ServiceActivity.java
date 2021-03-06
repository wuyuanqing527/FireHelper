package com.wyq.firehelper.framework.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.base.BaseCaseActivity;

import java.util.List;

import butterknife.BindView;

public class ServiceActivity extends BaseCaseActivity implements View.OnClickListener {

    @BindView(R.id.service_activity_main_bt1)
    public Button button1;

    @Override
    protected int attachLayoutRes() {
        return R.layout.framework_activity_service;
    }

    @Override
    public String getToolBarTitle() {
        return "Service";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("Service");
    }

    @Override
    public void initView() {
        button1.setText("start service");
        button1.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ServiceActivity.this, FireService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(ServiceActivity.this, FireService.class));
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, ServiceActivity.class));
    }

}
