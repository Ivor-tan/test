package com.example.myTest.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.test.my.R;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemContactActivity extends Activity {

    @BindView(R.id.contact_show)
    TextView contact_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_contact);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.add_phone_number, R.id.delete_phone_number, R.id.get_phone_number})
    void OnCilck(View v) {
        switch (v.getId()) {
            case R.id.add_phone_number:
                addContactPhoneNumber("我自己啊", new String[]{"15527017729", "123123123", "321321321"});
                break;

            case R.id.delete_phone_number:
                try {
                    testDelete();
                    Toast.makeText(SystemContactActivity.this, "delete.................ok", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.get_phone_number:

                List<String> list = queryNumber("我自己啊", SystemContactActivity.this);


                contact_show.setText(list.toString());

                break;
        }

    }


    /*
     * 添加系统联系人
     * */
    private void addContactPhoneNumber(String name, String[] number) {
        Toast.makeText(this, "success?", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //创建一个空的ContentValues
            ContentValues values = new ContentValues();
            //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
            Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContactUri);
            //往data表插入姓名数据
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//内容类型
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);//设置联系人名字
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);//向联系人URI添加联系人名字
            //往data表插入电话数据
            for (int i = 0; i < number.length; i++) {
                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number[i]);
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);//插入手机号码
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
            }
        }
    }

    /**
     * 删除系统联系人，部分机型无法删除
     */
    private void testDelete() throws Exception {
        String name = "我自己啊";
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, "display_name=?", new String[]{name}, null);
        while (cursor.moveToNext()) {

            Log.d("test", "testDelete: " + resolver.acquireContentProviderClient(uri));

            int id = cursor.getInt(0);
//            根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});


        }
    }


    /**
     * @param name 联系人姓名
     * @描述 查询该联系人下的所有号码
     */
    private List<String> queryNumber(String name, Context context) {
        List<String> numbers = new ArrayList<>();
        Cursor cursor = null;
        Cursor phoneCursor = null;
        try {
            //使用ContentResolver查找联系人数据
            cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            //遍历查询结果，找到所需号码
            while (cursor.moveToNext()) {
                //获取联系人ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //获取联系人的名字
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                if (name.equalsIgnoreCase(contactName)) {
                    // 查看联系人有多少个号码，如果没有号码，返回0
                    int phoneCount = cursor
                            .getInt(cursor
                                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if (phoneCount > 0) {
                        // 获得联系人的电话号码列表
                        phoneCursor = context.getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + "=" + contactId, null, null);
                        if (phoneCursor.moveToFirst()) {
                            do {
                                //遍历所有的联系人下面所有的电话号码
                                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                numbers.add(phoneNumber);
                            } while (phoneCursor.moveToNext());
                        }
                    }
                    //使用ContentResolver查找联系人的电话号码
                    //                Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    //                if (phone.moveToNext()) {
                    //                    String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //                    numbers.add(phoneNumber);
                    //                }
                }
            }
            cursor.close();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();

            }
            if (phoneCursor != null) {
                phoneCursor.close();
            }
        }
        return numbers;
    }
}
