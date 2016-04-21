package com.example.alfred.craftsman_part;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nettan on 2016-04-19.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERTION = 1;
    private static final String DATABASE_NAME ="HV_HELPER.db";

    // Arbetsplattstabell
    private static final String WORKPLACE_TABLE = "Workplacetable";
    private static final String COLUMN_WORKPLACE_ID = "Workplace_ID";
    private static final String COLUMN_WORKPLACE_NAME = "Workplace_name";
    private static final String COLUMN_FLOOR = "Floor";
    private static final String COLUMN_ROOM = "Room";
    private static final String COLUMN_FLOOR_MAP = "Floor_map";

    // Rumstabell
    private static final String ROOM_TABLE = "Roomtable";
    //private static final String COLUMN_ROOMS = "Rooms";
    private static final String COLUMN_WORKPACKAGE = "Workpackage";
    private static final String COLUMN_ROOM_MAP = "Room_map";

    // Arbetspaket
    private static final String WORKPACKAGE_TABLE = "Workpackagetable";
    //private static final String COLUMN_WORKPACKAGES = "Workpackages";
    private static final String COLUMN_MESSAGES = "Messages";
    private static final String COLUMN_OTHER_MAPS = "Other_maps";
    private static final String COLUMN_STATUS = "Status";
    private static final String COLUMN_CONTROLS = "Controls";
    private static final String COLUMN_SIGNATURES = "Signatures";
    private static final String COLUMN_TOOLS = "Tools";
    private static final String COLUMN_MATERIAL = "Material";


    SQLiteDatabase db;


    // Sätter upp Arbetsplattstabell
    private static final String WORKPLACE_TABLE_CREATE = "CREATE TABLE " + WORKPLACE_TABLE +" ("
            + COLUMN_WORKPLACE_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_WORKPLACE_NAME + " TEXT, "
            + COLUMN_FLOOR + " INTEGER, "
            + COLUMN_ROOM + " INTEGER, "
            + COLUMN_FLOOR_MAP + " TEXT "
            +")";

    // Sätter upp Rumstabell
    private static final String ROOM_TABLE_CREATE = "CREATE TABLE " + ROOM_TABLE +" ("
            + COLUMN_ROOM + " INTEGER PRIMARY KEY, "
            + COLUMN_WORKPACKAGE + " TEXT, "
            + COLUMN_ROOM_MAP + " TEXT "
            +")";


    // Sätter upp Arbetspaketstabell
    private static final String WORKPACKAGE_TABLE_CREATE = "CREATE TABLE " + WORKPACKAGE_TABLE +" ("
            + COLUMN_WORKPACKAGE + " INTEGER PRIMARY KEY, "
            + COLUMN_MESSAGES + " TEXT, "
            + COLUMN_OTHER_MAPS + " INTEGER, "
            + COLUMN_STATUS + " BOOLEAN, "
            + COLUMN_CONTROLS + " TEXT, "
            + COLUMN_SIGNATURES + " TEXT, "
            + COLUMN_TOOLS + " TEXT, "
            + COLUMN_MATERIAL + " TEXT "
            +")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERTION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(WORKPLACE_TABLE_CREATE);
        db.execSQL(WORKPACKAGE_TABLE_CREATE);
        db.execSQL(ROOM_TABLE_CREATE);


        this.db = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ WORKPLACE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ ROOM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ WORKPACKAGE_TABLE);
        onCreate(db);


    }


    public boolean insertWorkPlace(int ID, String name, String floor, int room, String floorMap){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_WORKPLACE_ID,ID);
        contentValues.put(COLUMN_WORKPLACE_NAME,name);
        contentValues.put(COLUMN_FLOOR,floor);
        contentValues.put(COLUMN_ROOM,room);
        contentValues.put(COLUMN_FLOOR_MAP,floorMap);

        long result = db.insert(WORKPLACE_TABLE,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else return true;
    }

    public boolean insertRoom(int room, String workpackage, String roomMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ROOM, room);
        contentValues.put(COLUMN_WORKPACKAGE, workpackage);
        contentValues.put(COLUMN_ROOM_MAP, roomMap);


        long result = db.insert(ROOM_TABLE, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else return true;
    }

    public boolean insertWorkPackage (String workpackage,String messages,String otherMaps, boolean status,String controls,String signatures,
                                      String tools,String materials){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORKPACKAGE,workpackage);
        contentValues.put(COLUMN_MESSAGES, messages);
        contentValues.put(COLUMN_OTHER_MAPS, otherMaps);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_CONTROLS, controls);
        contentValues.put(COLUMN_SIGNATURES, signatures);
        contentValues.put(COLUMN_TOOLS, tools);
        contentValues.put(COLUMN_MATERIAL, materials);

        long result = db.insert(WORKPACKAGE_TABLE, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else return true;
    }

    public boolean checkWpId(int ID){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Workplace_ID FROM Workplacetable";
        if(db != null) {
            Cursor cursor = db.rawQuery(query,null);
            int a;

            if (cursor.moveToFirst()) {
                do {
                    a = cursor.getInt(0);
                    if (a == ID) {
                        return true;
                    }
                }
                while (cursor.moveToNext());
            }
        }
        return false;
    }
}


