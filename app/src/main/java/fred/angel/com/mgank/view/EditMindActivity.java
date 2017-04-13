package fred.angel.com.mgank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.BaseToolbarActivity;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.widget.AspectRatioImageView;
import fred.angel.com.mgank.component.widget.IToolbar;
import fred.angel.com.mgank.model.enity.Mind;

/**
 * @author chenqiang
 */
public class EditMindActivity extends BaseToolbarActivity {

    Mind mind;

    TextView dateTv,cityInfoTv,categoryTv,authorTv;
    AspectRatioImageView img;
    EditText contentEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_edit_mind);
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        mind = (Mind) intent.getSerializableExtra(Constant.IntentKey.MIND);
    }

    @Override
    protected void onInitToolBar(IToolbar iToolbar) {
        super.onInitToolBar(iToolbar);
    }

    @Override
    public void initViews() {
        super.initViews();
        dateTv = getView(R.id.date_tv);
        cityInfoTv = getView(R.id.city_info_tv);
        categoryTv = getView(R.id.category_tv);
        authorTv = getView(R.id.author_tv);
        img = getView(R.id.img);
        contentEdt = getView(R.id.content_edt);

        dateTv.setText(Utils.format2MindTime(mind.getMaketime()));

    }


}
