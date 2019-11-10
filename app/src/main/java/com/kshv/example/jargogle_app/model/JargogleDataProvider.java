package com.kshv.example.jargogle_app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kshv.example.jargogle_app.database.JargogleDbHelper;
import com.kshv.example.jargogle_app.database.JargogleDbScheme.JargogleTable;
import com.kshv.example.jargogle_app.ui.main.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import static com.kshv.example.jargogle_app.database.JargogleDbScheme.JargogleGradient;
import static com.kshv.example.jargogle_app.database.JargogleDbScheme.JargogleUsrProfiles;

public class JargogleDataProvider {
    private static JargogleDataProvider provider;
    private SQLiteDatabase db;

    public static JargogleDataProvider getInstance(Context context) {
        return provider == null ? new JargogleDataProvider(context) : provider;
    }

    private JargogleDataProvider(Context context) {
        db = new JargogleDbHelper(context).getWritableDatabase();
        provider = this;
    }

    public List<Jargogle> getJargogleList() {
        List<Jargogle> jargogleList = new ArrayList<>();
        Cursor cursor = db.query(JargogleTable.NAME,
                null, JargogleTable.JargogleCols.OWNER + " = '" + LoginFragment.currentUsr + "'", null,
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Jargogle jargogle = new Jargogle(cursor.getString(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.UUID)));

                jargogle.setTitle(cursor.getString(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.TITLE)));

                jargogle.setData(cursor.getString(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.DATA)));

                jargogle.setEncoded(cursor.getInt(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.ENCODED)));

                jargogle.setChain_len(cursor.getString(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.CHAIN_LEN)));

                jargogle.setChain_seed(cursor.getString(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.CHAIN_SEED)));

                jargogle.setPasswd(cursor.getString(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.PASSWD)));

                jargogle.setOwner(cursor.getString(
                        cursor.getColumnIndex(JargogleTable.JargogleCols.OWNER)));

                jargogleList.add(jargogle);
                cursor.moveToNext();
            }

            cursor.close();
        }

        return jargogleList;
    }

    public void addJargogle(Jargogle jargogle) {
        ContentValues contentValues = getJargogleAsContentValues(jargogle);
        db.insert(JargogleTable.NAME, null, contentValues);
    }

    public static ContentValues getJargogleAsContentValues(Jargogle jargogle) {
        ContentValues values = new ContentValues();
        values.put(JargogleTable.JargogleCols.UUID, jargogle.getUUID());
        values.put(JargogleTable.JargogleCols.TITLE, jargogle.getTitle());
        values.put(JargogleTable.JargogleCols.DATA, jargogle.getData());
        values.put(JargogleTable.JargogleCols.ENCODED, jargogle.getEncoded());
        values.put(JargogleTable.JargogleCols.CHAIN_LEN, jargogle.getChain_len());
        values.put(JargogleTable.JargogleCols.CHAIN_SEED, jargogle.getChain_seed());
        values.put(JargogleTable.JargogleCols.PASSWD, jargogle.getPasswd());
        values.put(JargogleTable.JargogleCols.OWNER, jargogle.getOwner());
        return values;
    }

    private static ContentValues getJargogleGradientAsContentValues(String[] gradientParts, int r, int g, int b, String currentUsr) {
        ContentValues values = new ContentValues();
        values.put(JargogleGradient.JargogleCols.HEX1, gradientParts[0]);
        values.put(JargogleGradient.JargogleCols.HEX2, gradientParts[1]);
        values.put(JargogleGradient.JargogleCols.R_col, r);
        values.put(JargogleGradient.JargogleCols.G_col, g);
        values.put(JargogleGradient.JargogleCols.B_col, b);
        values.put(JargogleGradient.JargogleCols.OWNER, currentUsr);
        return values;
    }

    private static ContentValues getJargogleUsrProfileAsContentValues(String[] loginData) {
        ContentValues values = new ContentValues();
        values.put(JargogleUsrProfiles.JargogleCols.USER, loginData[0]);
        values.put(JargogleUsrProfiles.JargogleCols.PASSWD, loginData[1]);
        return values;
    }

    public Jargogle getJargogleByUUID(String jargogleUUID) {
        Jargogle jargogle = null;
        Cursor cursor = db.query(JargogleTable.NAME,
                null,
                JargogleTable.JargogleCols.UUID + " = ?",
                new String[]{jargogleUUID},
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            jargogle = new Jargogle(cursor.getString(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.UUID)));
            jargogle.setTitle(cursor.getString(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.TITLE)));
            jargogle.setData(cursor.getString(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.DATA)));
            jargogle.setEncoded(cursor.getInt(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.ENCODED)));
            jargogle.setChain_len(cursor.getString(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.CHAIN_LEN)));
            jargogle.setChain_seed(cursor.getString(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.CHAIN_SEED)));
            jargogle.setPasswd(cursor.getString(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.PASSWD)));
            jargogle.setOwner(cursor.getString(
                    cursor.getColumnIndex(JargogleTable.JargogleCols.OWNER)));
        }
        cursor.close();
        return jargogle;
    }

    public String[] getSavedJargogleGradient(Context context) {
        String[] gradient = new String[]{"#000000", "#000000"};

        Cursor cursor = db.query(JargogleGradient.NAME,
                null,
                JargogleGradient.JargogleCols.OWNER + " = '" + LoginFragment.currentUsr + "'",
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            gradient[0] = cursor.getString(cursor.getColumnIndex(JargogleGradient.JargogleCols.HEX1));
            gradient[1] = cursor.getString(cursor.getColumnIndex(JargogleGradient.JargogleCols.HEX2));
        } else {
            JargogleDataProvider.getInstance(context).insertNewGradient(gradient);
        }
        cursor.close();

        return gradient;
    }

    public void insertNewGradient(String[] gradient) {
        ContentValues contentValues = getJargogleGradientAsContentValues(
                new String[]{gradient[0], gradient[1]}, 0, 0, 0, LoginFragment.currentUsr
        );
        db.insert(JargogleGradient.NAME, null, contentValues);
    }

    public int[] getSeekBarsPositions() {
        int[] vals = new int[3];

        Cursor cursor = db.query(JargogleGradient.NAME,
                null,
                JargogleGradient.JargogleCols.OWNER + " = '" + LoginFragment.currentUsr + "'",
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            vals[0] = cursor.getInt(cursor.getColumnIndex(JargogleGradient.JargogleCols.R_col));
            vals[1] = cursor.getInt(cursor.getColumnIndex(JargogleGradient.JargogleCols.G_col));
            vals[2] = cursor.getInt(cursor.getColumnIndex(JargogleGradient.JargogleCols.B_col));
        }
        cursor.close();
        return vals;
    }

    public void updateJargogleRecord(Jargogle jargogle) {
        ContentValues contentValues = getJargogleAsContentValues(jargogle);
        db.update(JargogleTable.NAME,
                contentValues,
                JargogleTable.JargogleCols.UUID + " = ?",
                new String[]{jargogle.getUUID()});
    }

    public void updateJargogleGradient(String hex1, String hex2, int r, int g, int b, String userToCreate) {
        ContentValues contentValues = getJargogleGradientAsContentValues(new String[]{hex1, hex2}, r, g, b, userToCreate);
        db.update(JargogleGradient.NAME,
                contentValues,
                JargogleGradient.JargogleCols.OWNER + " = '" + userToCreate + "'",
                null);
    }

    public void deleteJargogleRecord(Jargogle jargogle) {
        db.delete(JargogleTable.NAME, JargogleTable.JargogleCols.UUID + " = ?",
                new String[]{jargogle.getUUID()});
    }

    public boolean verifyUser(String userToVerify, String passwdToVerify) {
        boolean verified = false;

        Cursor cursor = db.query(JargogleUsrProfiles.NAME,
                null,
                JargogleUsrProfiles.JargogleCols.USER + " = '" + userToVerify + "'",
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String passwd = cursor.getString(cursor.getColumnIndex(JargogleUsrProfiles.JargogleCols.PASSWD));
            if (passwdToVerify.equals(passwd)) {
                verified = true;
            }
        }
        cursor.close();
        return verified;
    }

    public void createNewUsr(String userToCreate, String passwdToAssign) {
        ContentValues contentValues = getJargogleUsrProfileAsContentValues(new String[]{userToCreate, passwdToAssign});
        db.insert(JargogleUsrProfiles.NAME, null, contentValues);
    }

    public boolean checkUserExists(String userToCreate) {
        Cursor cursor = db.query(JargogleUsrProfiles.NAME,
                null,
                JargogleUsrProfiles.JargogleCols.USER + " = '" + userToCreate + "'",
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }
}
