package com.wyq.firehelper.base;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.architecture.ArchitectureActivity;
import com.wyq.firehelper.article.ArticleMainActivity;
import com.wyq.firehelper.base.adapter.TvImgRecyclerViewAdapter;
import com.wyq.firehelper.device.DeviceActivity;
import com.wyq.firehelper.developKit.DevelopKitMainActivity;
import com.wyq.firehelper.encryption.EncryptActivity;
import com.wyq.firehelper.kotlin.mvpGitHub.view.GitHubMainActivity;
import com.wyq.firehelper.ui.UiMainActivity;
import com.wyq.firehelper.ui.layout.recyclerView.SimpleItemTouchHelperCallback;
import com.wyq.firehelper.utils.FireUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.list_activity_recycler_view)
    public RecyclerView baseRV;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    public static final int LINEAR_LAYOUT_TYPE = 0;
    public static final int GRID_LAYOUT_TYPE = 1;

    public int HOME_LAYOUT_TYPE = GRID_LAYOUT_TYPE;


    public TvImgRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvImgRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this,
                                ArticleMainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,
                                DeviceActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,
                                UiMainActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,
                                EncryptActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,
                                DevelopKitMainActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this,
                                ArchitectureActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this,
                                GitHubMainActivity.class));
                        break;
                    default:
                        break;

                }

            }
        };
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.list_activity_layout;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, getString(R.string.app_name), false);
    }

    public void initView() {
        AppCompatDelegate.setDefaultNightMode(NightThemeConfig.getInstance(this).getNightMode());

        initRecyclerView();
    }

    public void initRecyclerView() {

        TvImgRecyclerViewAdapter adapter = new TvImgRecyclerViewAdapter(this,getModuleList());
        baseRV.setLayoutManager(getLayoutManager(HOME_LAYOUT_TYPE));
        baseRV.setAdapter(adapter);
        adapter.setOnItemClickListener(onListItemClickListener());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(baseRV);
    }

    public RecyclerView.LayoutManager getLayoutManager(int layoutType) {
        if(layoutType == LINEAR_LAYOUT_TYPE){
            return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }else {
            return new GridLayoutManager(this,2);
        }
    }

    public List<FireModule> getModuleList() {
        String json = FireUtils.readAssets2String(this, "module.json");
        Logger.i(json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<FireModule> fireModules = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                FireModule module = FireModule.convertFromJson(jsonArray.getString(i));
                fireModules.add(module);
            }
            return fireModules;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * AppCompatDelegate.setDefaultNightMode(int mode);只作用于新生成的组件，对原先处于任务栈中的Activity不起作用。如果直接在Activity的onCreate()中调用这句代码，那当前的Activity中直接生效，不需要再调用recreate(),但我们通常是在监听器中调用这句代码，那就需要调用recreate()，否则对当前Activity无效(新生成的Activity还是生效的)。当然可以提前在onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)中保存好相关属性值,重建时使用。
     *
     * @param nightMode
     */
    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        NightThemeConfig.getInstance(this).setNightMode(nightMode);
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }
}
