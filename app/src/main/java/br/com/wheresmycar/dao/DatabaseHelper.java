package br.com.wheresmycar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.wheresmycar.dto.UsuarioDTO;

/**
 * Created by Bruno.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABELA = "Wmclogin";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WheresMyCarDB";
    private static final int DICTIONARY_TABLE_NAME = 1;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String ddl = "CREATE TABLE IF NOT EXISTS " + TABELA + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " login TEXT NOT NULL, senha TEXT NOT NULL," +
                " ult_atul TIMESTAMP NOT NULL DEFAULT current_timestamp);";

        String UPDATE_TIME_TRIGGER = "CREATE TRIGGER IF NOT EXISTS update_time_trigger" +
                " AFTER UPDATE ON " + TABELA + " FOR EACH ROW" +
                " BEGIN UPDATE " + TABELA +
                " SET ult_atul = current_timestamp" +
                " WHERE id = old.id; END";

        db.execSQL(ddl);
        db.execSQL(UPDATE_TIME_TRIGGER);
        Log.i("SQL", "Banco Criado.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA + ";";
        db.execSQL(sql);
        onCreate(db);

    }



    public void createTable() {
        db = getWritableDatabase();
        onCreate(db);

        ContentValues values = new ContentValues();

        values.put("login", "12345678901");
        values.put("senha", "123");
        db.insert(TABELA, null, values);
        db.close();

    }

    public List<UsuarioDTO> getUsuario(String usuario) {
        db = getWritableDatabase();

        String[] args = {String.valueOf(usuario)};

        String sql = " SELECT * FROM " + TABELA + ";";
        Cursor c = getReadableDatabase().rawQuery(sql,null);



        List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
        while (c.moveToNext())
        {
            UsuarioDTO usuarioVO = new UsuarioDTO();
            usuarioVO.setId(c.getLong(c.getColumnIndex("id")));
            usuarioVO.setLogin(c.getString(c.getColumnIndex("login")));
            usuarioVO.setSenha(c.getString(c.getColumnIndex("senha")));
            usuarioVO.setUltimaAtualizacao(c.getString(c.getColumnIndex("ult_atul")));

            usuarios.add(usuarioVO);
        }
        return usuarios;
    }

}
