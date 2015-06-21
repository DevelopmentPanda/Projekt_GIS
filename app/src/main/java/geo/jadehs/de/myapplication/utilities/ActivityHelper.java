package geo.jadehs.de.myapplication.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import geo.jadehs.de.myapplication.R;

/**
 * Created by Maik on 13.05.2015.
 */
public class ActivityHelper {
    private static final String TAG = "ActivityHelper";

    static public void showAlert(Context ctx, final String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Application Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Dismiss",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        // Do nothing
                    }
                });
        alertDialog.show();
    }

    static public String getDataBase(Context ctx, String filename) throws FileNotFoundException {



        File db = null;





        db = new File(getPath(ctx, false), filename);

        try {
            db.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Checking: " + db.toString());

        if (db.exists()) {
            System.out.println("File db in Speicher geschrieben");

            return db.toString();
        }

        // Check external storage second
        db = new File(getPath(ctx, true), filename);
        Log.d(TAG, "Checking: " + db.toString());
        if (db.exists()) {
            return db.toString();
        }

        // Database not found
        throw new FileNotFoundException(ctx.getString(R.string.error_locate_failed));
    }

    static public File getPath(Context ctx, boolean externalStorage) {
        if (externalStorage) {
            return ctx.getExternalFilesDir(null);
        } else {
            return ctx.getFilesDir();
        }
    }

   static public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }
}
