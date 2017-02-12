package fred.angel.com.mgank.view;

import android.os.Bundle;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragemnt fragment = new HomeFragemnt();
        getSupportFragmentManager().beginTransaction().
                add(R.id.frag_content,fragment).commit();

    }
}
