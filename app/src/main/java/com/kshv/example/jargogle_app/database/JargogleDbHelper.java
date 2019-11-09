package com.kshv.example.jargogle_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kshv.example.jargogle_app.R;
import com.kshv.example.jargogle_app.database.JargogleDbScheme.JargogleTable;
import com.kshv.example.jargogle_app.model.Jargogle;
import com.kshv.example.jargogle_app.model.JargogleDataProvider;

import static com.kshv.example.jargogle_app.database.JargogleDbScheme.*;

public class JargogleDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "jargogle.db";
    private Context context;

    public JargogleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + JargogleTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                JargogleTable.JargogleCols.UUID + ", " +
                JargogleTable.JargogleCols.TITLE + ", " +
                JargogleTable.JargogleCols.ENCODED + ", " +
                JargogleTable.JargogleCols.CHAIN_LEN + ", " +
                JargogleTable.JargogleCols.CHAIN_SEED + ", " +
                JargogleTable.JargogleCols.PASSWD + ", " +
                JargogleTable.JargogleCols.DATA + ");"
        );

        db.execSQL("create table " + JargogleGradient.NAME + "(" +
                " _id integer primary key autoincrement, " +
                JargogleGradient.JargogleCols.ID + ", " +
                JargogleGradient.JargogleCols.HEX1 + ", " +
                JargogleGradient.JargogleCols.HEX2 + ", " +
                JargogleGradient.JargogleCols.R_col + ", " +
                JargogleGradient.JargogleCols.G_col + ", " +
                JargogleGradient.JargogleCols. B_col+ ");"
        );

        db.insert(JargogleGradient.NAME,null,JargogleDataProvider.getJargogleGradientAsContentValues(
                new String[]{"#000000","#000000"},0,0,0
        ));

        Jargogle defaultJargogle = new Jargogle ();
        defaultJargogle.setEncoded (Jargogle.DECODED);
        defaultJargogle.setTitle (context.getString (R.string.default_jargogle_title));
        defaultJargogle.setData (context.getString (R.string.default_jargogle_data));

        db.insert (JargogleTable.NAME,null,
                JargogleDataProvider.getJargogleAsContentValues (defaultJargogle));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
