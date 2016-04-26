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
            + COLUMN_WORKPLACE_ID + " INTEGER , "
            + COLUMN_WORKPLACE_NAME + " TEXT, "
            + COLUMN_FLOOR + " INTEGER, "
            + COLUMN_ROOM + " INTEGER, "
            + COLUMN_FLOOR_MAP + " TEXT, "
            + "PRIMARY KEY ( " + COLUMN_WORKPLACE_ID + ", " + COLUMN_FLOOR + ", " + COLUMN_ROOM + ") "
            +")";

    // Sätter upp Rumstabell
    private static final String ROOM_TABLE_CREATE = "CREATE TABLE " + ROOM_TABLE +" ("
            + COLUMN_ROOM + " INTEGER , "
            + COLUMN_FLOOR + " INTEGER, "
            + COLUMN_WORKPLACE_ID + " INTEGER , "
            + COLUMN_WORKPACKAGE + " TEXT, "
            + COLUMN_ROOM_MAP + " TEXT, "
            + "PRIMARY KEY ( " + COLUMN_WORKPLACE_ID + ", " + COLUMN_FLOOR + ", " + COLUMN_ROOM + ", " + COLUMN_WORKPACKAGE + ") "
            +")";


    // Sätter upp Arbetspaketstabell
    private static final String WORKPACKAGE_TABLE_CREATE = "CREATE TABLE " + WORKPACKAGE_TABLE +" ("
            + COLUMN_WORKPACKAGE + " TEXT PRIMARY KEY, "
            + COLUMN_MESSAGES + " TEXT, "
            + COLUMN_OTHER_MAPS + " TEXT, "
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


    public boolean insertWorkPlace(int ID, String name, int floor, int room, String floorMap){
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

    public boolean insertRoom(int room, int floor, int workplace_ID, String workpackage, String roomMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ROOM, room);
        contentValues.put(COLUMN_FLOOR, floor);
        contentValues.put(COLUMN_WORKPLACE_ID, workplace_ID);
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
                    a = cursor.getInt(0); //https://github.com/alfredjohansson/Git_Rep_Craft.git
                    if (a == ID) {
                        return true;
                    }
                }
                while (cursor.moveToNext());
            }
        }
        db.close();
        return false;
    }

    // Funktion för att plocka ut distinkta våningsplan utifrån ett arbetsplatts ID
    public int [] getFloors(int Workplace_ID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_FLOOR + " FROM " + WORKPLACE_TABLE +
                " WHERE " + COLUMN_WORKPLACE_ID + " = " + Workplace_ID;
        int [] floors = null;
        int place = 0;
        if(db != null){
            Cursor cursor = db.rawQuery(query,null);
            floors = new int[cursor.getCount()];

            if (cursor.moveToFirst()){

                do{
                    floors[place] = cursor.getInt(0);
                    place ++;
                }
                while(cursor.moveToNext());
            }
        }
        db.close();
        return floors;

    }
    // Funktion för att plocka ut distinkta rum utifrån ett arbetsplatts ID och våning
    public int [] getRoom(int Workplace_ID, int floor){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_ROOM + " FROM " + WORKPLACE_TABLE + " WHERE " +
                COLUMN_WORKPLACE_ID + " = " + Workplace_ID +" AND " + COLUMN_FLOOR + " = " + floor;
        int [] rooms = null;
        int place = 0;
        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            rooms = new int[cursor.getCount()];

            if(cursor.moveToFirst()){
                do{
                    rooms [place] = cursor.getInt(0);
                    place ++;
                }
                while(cursor.moveToNext());
            }
        }
        db.close();
        return rooms;
    }
    // Funktion som ger arbetsplattsnamnet
    public String getWorkplace(int Workplace_ID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT Workplace_name FROM Workplacetable WHERE workplace_id = " + Workplace_ID;
        String name = null;
        if (db != null){
            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do{
                    name = cursor.getString(0);
                }
                while(cursor.moveToNext());
            }
        }
        db.close();
        return name;

    }
    // Funktion som plockar ut alla arbetspaket för ett rum
    public String [] getWP(int room, int floor,int wpID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_WORKPACKAGE + " FROM " + ROOM_TABLE + " WHERE " +
                COLUMN_WORKPLACE_ID + " = " + wpID +" AND " + COLUMN_FLOOR + " = " + floor + " AND " +
                COLUMN_ROOM + " = " + room;
        String [] WP = null;
        int place = 0;
        if(db != null){
            Cursor cursor = db.rawQuery(query,null);
            WP = new String [cursor.getCount()];
            if(cursor.moveToFirst()){
                do{
                    WP[place] = cursor.getString(0);
                    place ++;
                }
                while(cursor.moveToNext());
            }
        }
        db.close();
        return WP;
    }
    // Funktion för att kontrolera statusen på ett arbetspaket.
    public boolean getStatus(String WP){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_STATUS + " FROM " + WORKPACKAGE_TABLE + " WHERE " + COLUMN_WORKPACKAGE +  " = '" + WP + "' ";
        int result = 0;
        if(db != null) {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                result = cursor.getInt(0);
            }
        }
        if(result == 1){
            return true;
        }else return false;
    }
    // Funktion för att plocka ut meddelanden
    public String getMessage(String WP){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_MESSAGES + " FROM " + WORKPACKAGE_TABLE + " WHERE " + COLUMN_WORKPACKAGE +  " = '" + WP + "' ";
        String message = "";
        if(db != null){
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                message = cursor.getString(0);
            }
        }
        db.close();
        return message;
    }

    public boolean sign(String WP, String signature){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + WORKPACKAGE_TABLE + " SET " + COLUMN_SIGNATURES + " ='" + signature + "' " + "WHERE " + COLUMN_WORKPACKAGE + " ='" + WP + "' ";

        try {
            db.execSQL(query);
            db.close();
            return true;
        }catch (Exception e){
            db.close();
            return false;
        }
    }
    public boolean changeStatus(String WP, String signature, boolean status){
        SQLiteDatabase db = this.getWritableDatabase();
        int a;
        if(status){
            a = 1;
        }
        else a = 0;
        String query = "UPDATE " + WORKPACKAGE_TABLE + " SET " + COLUMN_STATUS + " =" + a + " WHERE " + COLUMN_WORKPACKAGE + " ='" + WP + "' ";
        try {
            db.execSQL(query);
            db.close();
            sign(WP,signature);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public void insertData(){
        insertWorkPlace(1,"Arbetsplatts1",1,1,"Våningsritning11");
        insertWorkPlace(1,"Arbetsplatts1",1,2,"Våningsritning11");
        insertWorkPlace(1,"Arbetsplatts1",2,1,"Våningsritning12");
        insertWorkPlace(2,"Arbetsplatts2",1,1,"Våningsritning21");
        insertWorkPlace(2,"Arbetsplatts2",1,2,"Våningsritning21");

        insertRoom(1,1,1,"AP1","Rumsritning1");
        insertRoom(1,1,1,"AP2","Rumsritning1");
        insertRoom(2,1,1,"AP3","Rumsritning2");
        insertRoom(2,1,1,"AP4","Rumsritning2");

        insertWorkPackage("AP1","","Ritning",false,"kolla med vattenpass","","Hammare","reglar");
        insertWorkPackage("AP2","Ser bra ut","Ritning",true,"Dubbelkolla cc","Ove","Hammare","Gips");
        insertWorkPackage("AP3","","Ritning",false,"Kolla något","","Såg","Gips");
    }
}