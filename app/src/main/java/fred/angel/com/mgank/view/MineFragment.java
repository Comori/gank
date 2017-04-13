package fred.angel.com.mgank.view;

import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.MyPagerAdapter;
import fred.angel.com.mgank.component.BaseFragment;
import fred.angel.com.mgank.component.ContactManager;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.widget.VerticalViewPager;
import fred.angel.com.mgank.model.DateGankModel;
import fred.angel.com.mgank.model.INetCallback;

/**
 * @author chenqiang
 */
public class MineFragment extends BaseFragment {

    VerticalViewPager pager;
    TextView tv;
    TextView m_tv;
    DateGankModel model;

    List<String> dates;
    List<BaseFragment> fragments;
    ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = Utils.findView(view,R.id.verticalPager);
        tv = Utils.findView(view,R.id.tv);
        img = Utils.findView(view,R.id.img);
        m_tv = Utils.findView(view,R.id.m_tv);
        model = new DateGankModel();
    }

    @Override
    public void onLoad() {

        model.loadHistoryDate(new INetCallback<String>() {
            @Override
            public void onSuccess(int pageNum, String data) {
                try {
                    JSONArray array = new JSONArray(data);
                    dates = new ArrayList<>(array.length());
                    for(int i=0;i<array.length();i++){
                        dates.add(array.opt(i).toString());
                    }
                    showDateGanks();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int pageNum, String msg) {
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tv.setText(fragments.get(position).getPageTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        pager.setPageMargin(DisplayUtil.dip2px(16));

        if(ensurePermissions()){
            getAllContact();
        }

    }

    private void showDateGanks() {
        fragments = new ArrayList<>(dates.size());
        for (String date : dates){
            fragments.add(DayFragment.newInstance(date).setPageTitle(date).setViewPager(pager));
        }
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager(),fragments,true));
        pager.setOffscreenPageLimit(10);
    }

    @Override
    public String getPageTitle() {
        return "";
    }

    private boolean ensurePermissions() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            MPermissions.requestPermissions(this,REQUEST_CONTACT_CODE,PERMISSIONS);
            return false;
        }
        return true;
    }

    @PermissionGrant(REQUEST_CONTACT_CODE)
    public void requestSdcardSuccess() {
        getAllContact();
    }

    @PermissionDenied(REQUEST_CONTACT_CODE)
    public void requestSdcardFailed() {
        Toast.makeText(context, "DENY ACCESS CONTACT!", Toast.LENGTH_SHORT).show();
    }

    private final static String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS};
    private final static int REQUEST_CONTACT_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getAllContact(){
        final SparseIntArray contactIds = new SparseIntArray();

        final List<ContactManager.ContactEnity> list = new ArrayList<>();
        ContactManager.INSTANCE.load(new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                if(cursor != null && cursor.moveToFirst()){
                    ContactManager.ContactEnity enity = null;
                    int cIndex = 0;
                    do{
                        int _id = cursor.getInt(0);
                        String name = cursor.getString(4);
                        int type = cursor.getInt(2);
                        String number = cursor.getString(1);
                        String s = cursor.getString(5);
                        Uri photo = null;
                        if(!TextUtils.isEmpty(s)){
                            photo = Uri.parse(s);
                        }
                        int index = contactIds.indexOfValue(_id);
                        if(index < 0){
                            contactIds.put(cIndex,_id);
                            cIndex++;
                            enity = new ContactManager.ContactEnity();
                            enity.name = name;
                            SparseArray<String> numbers = new SparseArray<>();
                            numbers.put(type,number);
                            enity.photo = photo;
                            enity.numbers = numbers;
                            list.add(enity);
                        }else {
                            enity = list.get(index);
                            enity.numbers.put(type,number);
                        }

                    } while (cursor.moveToNext());
                    cursor.close();

                    img.setImageURI(list.get(0).photo);
                    m_tv.setText(list.toString());
                }
            }
        });
    }
}
