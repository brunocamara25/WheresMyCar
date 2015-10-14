package br.com.wheresmycar.Activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.com.wheresmycar.dao.DatabaseHelper;
import br.com.wheresmycar.dto.CarrosDTO;
import br.com.wheresmycar.dto.UsuarioDTO;
import wheresmycar.com.br.wheresmycar.R;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edtLogin;
    private TextView txtVersion;
    private Button btnLogar;
    private static final String TAG = "ExampleActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    //Campo passado da tela para chamada servico
    String CPF;
    ProgressDialog dialog;
    List<CarrosDTO> carrosList = new ArrayList<CarrosDTO>();
    ListView listPlacas;
    Intent paramPlaca;


    private SQLiteDatabase db;

    private ProgressDialog mConnectionProgressDialog;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Versao do app
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        txtVersion.setText(R.string.versao);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        btnLogar.setOnClickListener(this);
        listPlacas = (ListView) findViewById(R.id.lvPlacas);

        //Criando Banco
        dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.createTable();

        //edtLogin.setOnKeyListener(this);

    }


    public void logar(View view){

        String login = edtLogin.getText().toString();

        List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
        listaUsuarios = dbHelper.getUsuario(login);

        Log.i("Sucesso", "Logado");

    }

    private void ListarCarrosJson() {  new Task().execute(); }
    private class Task extends AsyncTask<Void, Void, List<CarrosDTO>> {
        List<String> listaPlacas = new ArrayList<String>();
        @Override
        protected List<CarrosDTO> doInBackground(Void... voids) {

            String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/ListarCarros?CPF=" + CPF;

            try {
                // Setamos o cliente http e o nosso request, que será do tipo GET
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                // Setamos nossa URI
                request.setURI(new URI(url));
                // Executamos nossa transação HTTP
                HttpResponse response = httpclient.execute(request);
                // Pegamos o conteúdo como resposta e inserimos em um InputStream
                InputStream content = response.getEntity().getContent();
                // Instanciamos o nosso Reader com o InputStream
                Reader reader = new InputStreamReader(content);
                // Aqui vamos utilizar a Biblioteca Gson para transformar o Json recebido em Objeto JAVA
                /* Instanciamos o objeto Gson e em seguida utilizamos o método fromJson() passando como parâmetro o Reader instanciado e o tipo do Objeto que será retornado. */
                Gson gson = new Gson();

                carrosList = Arrays.asList(gson.fromJson(reader, CarrosDTO[].class));

                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                //Toast toast = Toast.makeText(LoginActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_LONG);
                //toast.show();
                Log.e("Error", (String) getText(R.string.msg_erro_servico));

            }

            return carrosList;
        }

        protected void onPostExecute(List<CarrosDTO> carrosList) {
            Log.d("Listando Placas", "onPostExecute");

             /*Lista Placas*/
            for (CarrosDTO placas : carrosList) {
                listaPlacas.add(placas.getPlaca());
            }


            final StableArrayAdapter adapter = new StableArrayAdapter(LoginActivity.this,android.R.layout.simple_list_item_single_choice, listaPlacas);
            listPlacas.setAdapter(adapter);

            listPlacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    final String placa = (String) parent.getItemAtPosition(position);
                    paramPlaca = new Intent(LoginActivity.this,PrincipalActivity.class);
                    paramPlaca.putExtra("placa", placa);
                    startActivity(paramPlaca);
                    finish();
                }
            });
            /*Lista Placas*/

        }


    }

    /*Lista Placas*/
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
    }
    /*Lista Placas*/

    @Override
    public void onClick(View v) {

        if (v == btnLogar){
            CPF = edtLogin.getText().toString();
            ListarCarrosJson();

        }

    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event){
//        digitosCpf++;
//        if(digitosCpf == 11 | digitosCpf > 11 ){
//            Toast toast = Toast.makeText(LoginActivity.this, "Enter!", Toast.LENGTH_LONG);
//            toast.show();
//            //fazer if 11 caracteres
//
//        }
//        return true;
//    }

}

