package fred.angel.com.mgank.component;

import android.content.AsyncQueryHandler;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.SparseArray;

/**
 * @author chenqiang
 */
public enum ContactManager {

    INSTANCE;



    public interface PhoneContact{
        Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] PHONENUMBER_PROJECTION = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.MIMETYPE,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
        };
    }


    public void load(AsyncQueryHandler handler){

        handler.startQuery(0,null,PhoneContact.CONTENT_URI,
                PhoneContact.PHONENUMBER_PROJECTION,null,null,null);
    }

    public static class ContactEnity{
        public String name;
        public Uri photo;
        public SparseArray<String> numbers;

        @Override
        public String toString() {
            return "ContactEnity{" +
                    "name='" + name + '\'' +
                    ", photo=" + photo +
                    ", numbers=" + numbers.toString() +
                    '}';
        }
    }

}
