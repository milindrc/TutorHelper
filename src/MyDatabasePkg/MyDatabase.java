package MyDatabasePkg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper{

	
	public static SQLiteDatabase  sqldb;
	
	private static final String dbname = "Student.sqlite";
	private static final int dbversion = 3;
	
	public MyDatabase(Context context) {
		super(context, dbname,null, dbversion);
		// TODO Auto-generated constructor stub
			
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	arg0.execSQL("create table batches('ID' integer primary key autoincrement ,'days' text,'time' text,'room' text ,'students' text,'class' text,'subject' text,'start_date' text );");
	}
	
	public void openDatabase()
	{
		sqldb = this.getWritableDatabase();
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}} 


