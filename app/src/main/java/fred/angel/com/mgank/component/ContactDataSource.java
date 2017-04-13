package fred.angel.com.mgank.component;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * @author chenqiang
 */
public class ContactDataSource implements LoaderManager.LoaderCallbacks<Cursor> {



    @Override
    public Loader onCreateLoader(int id, Bundle args) {


        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(Loader loader) {

    }



}
