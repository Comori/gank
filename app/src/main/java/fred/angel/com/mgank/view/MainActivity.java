package fred.angel.com.mgank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.View;
import android.widget.RadioGroup;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.BaseActivity;
import fred.angel.com.mgank.component.DownloadService;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.UIHelper;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.model.enity.UpdateInfo;

public class MainActivity extends BaseActivity {

    HomeFragment homeFragment;
    FindFragment findFragment;
    MineFragment mineFragment;

    RadioGroup bottonRdg;

    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();

        setContentView(R.layout.activity_main);

        Utils.checkUpdate( new Utils.CheckUpdateCallback() {
            @Override
            public void onComplete(boolean needUpdate, final UpdateInfo updateInfo) {
                if(needUpdate){
                    String info = getResources().getString(R.string.update_info,updateInfo.getChangelog());

                    UIHelper.showCenterDialogWithCancleCallback(MainActivity.this, Html.fromHtml(info),
                            "更新", true, null, true, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, DownloadService.class);
                            intent.putExtra(Constant.IntentKey.DOWNLOAD_URL,updateInfo.getInstall_url());
                            startService(intent);
                        }
                    },null,true);

                }
            }
        });

    }

    @Override
    public void initViews() {
        bottonRdg = getView(R.id.bottom_rdg);

        check(R.id.home_rb);
    }

    @Override
    public void initListener() {
        bottonRdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                check(checkedId);
            }
        });
    }

    private void check(int id){
        FragmentTransaction transaction = fm.beginTransaction();
        hideAll(transaction);
        switch (id){
            case R.id.home_rb:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.frag_content,homeFragment);
                }else transaction.show(homeFragment);
                break;
            case R.id.find_rb:
                if(findFragment == null){
                    findFragment = new FindFragment();
                    transaction.add(R.id.frag_content,findFragment);
                }else transaction.show(findFragment);
                break;
            case R.id.mine_rb:
                if(mineFragment == null){
                    mineFragment = new MineFragment();
                    transaction.add(R.id.frag_content,mineFragment);
                }else transaction.show(mineFragment);
                break;
        }

        transaction.commit();
    }

    private void hideAll(FragmentTransaction transaction){
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(findFragment != null){
            transaction.hide(findFragment);
        }
        if(mineFragment != null){
            transaction.hide(mineFragment);
        }
    }
}
