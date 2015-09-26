package br.com.wheresmycar.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.wheresmycar.dto.BlocosDTO;
import wheresmycar.com.br.wheresmycar.R;

public class BlocoActivity extends Activity implements View.OnClickListener {

    private Button btBloco1;
    private Button btBloco2;
    private Button btBloco3;
    private Button btBloco4;
    private Button btBloco5;
    private Button btBloco6;
    private Button btBloco7;
    private Button btBloco8;
    private Button btBloco9;
    private Button btBloco10;
    ProgressDialog dialog;
    List<BlocosDTO> blocosList;
    public int idAndar;

    Intent pageBloco1;
    Intent pageBloco2;
    Intent pageBloco3;
    Intent pageBloco4;
    Intent pageBloco5;
    Intent pageBloco6;
    Intent pageBloco7;
    Intent pageBloco8;
    Intent pageBloco9;
    Intent pageBloco10;

    //Timer Refresh
    private Timer autoUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloco);

        dialog = ProgressDialog.show(BlocoActivity.this, "", "Buscando blocos disponíveis...", true);

        btBloco1 = (Button) findViewById(R.id.btBloco1);
        btBloco1.setOnClickListener(this);
        btBloco2 = (Button) findViewById(R.id.btBloco2);
        btBloco2.setOnClickListener(this);
        btBloco3 = (Button) findViewById(R.id.btBloco3);
        btBloco3.setOnClickListener(this);
        btBloco4 = (Button) findViewById(R.id.btBloco4);
        btBloco4.setOnClickListener(this);
        btBloco5 = (Button) findViewById(R.id.btBloco5);
        btBloco5.setOnClickListener(this);
        btBloco6 = (Button) findViewById(R.id.btBloco6);
        btBloco6.setOnClickListener(this);
        btBloco7 = (Button) findViewById(R.id.btBloco7);
        btBloco7.setOnClickListener(this);
        btBloco8 = (Button) findViewById(R.id.btBloco8);
        btBloco8.setOnClickListener(this);
        btBloco9 = (Button) findViewById(R.id.btBloco9);
        btBloco9.setOnClickListener(this);
        btBloco10 = (Button) findViewById(R.id.btBloco10);
        btBloco10.setOnClickListener(this);

        Intent intent = getIntent();
        idAndar = intent.getIntExtra("idAndar", 0);
        ListarBlocosJson();
    }


    private void ListarBlocosJson() {
        new Task().execute();
    }


    private class Task extends AsyncTask<Void, Void, List<BlocosDTO>> {
        private String text;

        @Override
        protected List<BlocosDTO> doInBackground(Void... voids) {

            String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/ListarBlocos?Id_Andar=" + idAndar;

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = httpclient.execute(request);
                InputStream content = response.getEntity().getContent();
                Reader reader = new InputStreamReader(content);
                Gson gson = new Gson();

                blocosList = Arrays.asList(gson.fromJson(reader, BlocosDTO[].class));

                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(BlocoActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_LONG);
                toast.show();
                Log.e("Error", (String) getText(R.string.msg_erro_servico));

            }
            return blocosList;
        }

        protected void onPostExecute(List<BlocosDTO> vagasList) {
            Log.d("Listando Blocos", "onPostExecute");
            int varControleBloco = 0;
            for (BlocosDTO blocos : blocosList) {
                varControleBloco++;

                switch (varControleBloco) {

                    case 1:
                        btBloco1.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco1 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco1.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 2:
                        btBloco2.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco2 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco2.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 3:
                        btBloco3.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco3 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco3.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 4:
                        btBloco4.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco4 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco4.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 5:
                        btBloco5.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco5 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco5.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 6:
                        btBloco6.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco6 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco6.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 7:
                        btBloco7.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco7 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco7.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 8:
                        btBloco8.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco8 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco8.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 9:
                        btBloco9.setText(" "+ blocos.getNome() + "  Vagas "+ blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco9 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco9.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                    case 10:
                        btBloco10.setText(" " + blocos.getNome() + "  Vagas " + blocos.getQtdVagas() + "  Livre " + blocos.getQtdLivre()+ " ");
                        pageBloco10 = new Intent(BlocoActivity.this,VagasActivity.class);
                        pageBloco10.putExtra("idBloco", Integer.valueOf(blocos.getId()));
                        break;
                }
            }
            dialog.dismiss();
        }

    }

    @Override
    public void onClick(View v){

        if(v == btBloco1){
            startActivity(pageBloco1);
        }else if(v == btBloco2){
            startActivity(pageBloco2);
        }else if(v == btBloco3){
            startActivity(pageBloco3);
        }else if(v == btBloco4){
            startActivity(pageBloco4);
        }else if(v == btBloco5){
            startActivity(pageBloco5);
        }else if(v == btBloco6){
            startActivity(pageBloco6);
        }else if(v == btBloco7){
            startActivity(pageBloco7);
        }else if(v == btBloco8){
            startActivity(pageBloco8);
        }else if(v == btBloco9){
            startActivity(pageBloco9);
        }else if(v == btBloco10){
            startActivity(pageBloco10);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast refresh = Toast.makeText(BlocoActivity.this, "Refresh!", Toast.LENGTH_SHORT);
                        refresh.show();
                        ListarBlocosJson();
                    }
                });
            }
        }, 0, 40000); // 40 secs
    }

}
