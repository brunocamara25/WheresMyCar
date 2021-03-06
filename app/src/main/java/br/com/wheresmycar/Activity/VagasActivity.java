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

import br.com.wheresmycar.dto.SituacaoVagaDTO;
import br.com.wheresmycar.dto.VagasDTO;
import wheresmycar.com.br.wheresmycar.R;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class VagasActivity extends Activity implements View.OnClickListener{

    private Button btVaga1;
    private Button btVaga2;
    private Button btVaga3;
    private Button btVaga4;
    private Button btVaga5;
    private Button btVaga6;
    private Button btVaga7;
    private Button btVaga8;
    private Button btVaga9;
    private Button btVaga10;
    ProgressDialog dialog;
    List<VagasDTO> vagasList;
    List<SituacaoVagaDTO> sitVagasList;
    //Valor recebido da activity anterior, para char no servico
    public static int idBloco;
    boolean situacaoVaga;

    //Variáveos que guardam valor dos ids das vagas, para ser passado na chamada servico
    public int idVagaBt1;
    public int idVagaBt2;
    public int idVagaBt3;
    public int idVagaBt4;
    public int idVagaBt5;
    public int idVagaBt6;
    public int idVagaBt7;
    public int idVagaBt8;
    public int idVagaBt9;
    public int idVagaBt10;

    int idPlacaCarro;

    //Timer Refresh
    private Timer autoUpdate;

    public int vagaParaReserva;
    public int carroParaReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vagas);

        dialog = ProgressDialog.show(VagasActivity.this, "", "Buscando vagas disponíveis...", true);
        btVaga1 = (Button) findViewById(R.id.btVaga1);
        btVaga1.setOnClickListener(this);
        btVaga2 = (Button) findViewById(R.id.btVaga2);
        btVaga2.setOnClickListener(this);
        btVaga3 = (Button) findViewById(R.id.btVaga3);
        btVaga3.setOnClickListener(this);
        btVaga4 = (Button) findViewById(R.id.btVaga4);
        btVaga4.setOnClickListener(this);
        btVaga5 = (Button) findViewById(R.id.btVaga5);
        btVaga5.setOnClickListener(this);
        btVaga6 = (Button) findViewById(R.id.btVaga6);
        btVaga6.setOnClickListener(this);
        btVaga7 = (Button) findViewById(R.id.btVaga7);
        btVaga7.setOnClickListener(this);
        btVaga8 = (Button) findViewById(R.id.btVaga8);
        btVaga8.setOnClickListener(this);
        btVaga9 = (Button) findViewById(R.id.btVaga9);
        btVaga9.setOnClickListener(this);
        btVaga10 = (Button) findViewById(R.id.btVaga10);
        btVaga10.setOnClickListener(this);

        Intent intent = getIntent();
        idBloco = intent.getIntExtra("idBloco", 0);
        idPlacaCarro = Integer.valueOf(intent.getStringExtra("idPlacaCarro"));
        ListarVagasJson();

    }



    private void ListarVagasJson() {
        new ListarVagas().execute();
    }



    private class ListarVagas extends AsyncTask<Void, Void, List<VagasDTO>> {
        private String text;

        @Override
        protected List<VagasDTO> doInBackground(Void... voids) {

             String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/ListarVagas?Id_Bloco="+idBloco;

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = httpclient.execute(request);
                InputStream content = response.getEntity().getContent();
                Reader reader = new InputStreamReader(content);
                Gson gson = new Gson();
                vagasList = Arrays.asList(gson.fromJson(reader, VagasDTO[].class));
                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(VagasActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_SHORT);
                toast.show();
                Log.e("Error", (String) getText(R.string.msg_erro_servico));

            }
            return vagasList;
        }

        protected void onPostExecute(List<VagasDTO> vagasList) {

            Log.d("Listando Vagas", "onPostExecute");
            int varControleVagas = 0;
            for (VagasDTO vagas : vagasList) {
                varControleVagas++;

                switch (varControleVagas) {

                    case 1:
                        btVaga1.setText("  Vaga  " + vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga1.setBackgroundColor(GREEN);
                            }else{
                                btVaga1.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga1.setBackgroundColor(RED);
                        }
                        idVagaBt1 = Integer.valueOf(vagas.getId());
                        break;
                    case 2:
                        btVaga2.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga2.setBackgroundColor(GREEN);
                            }else{
                                btVaga2.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga2.setBackgroundColor(RED);
                        }
                        idVagaBt2 = Integer.valueOf(vagas.getId());
                        break;
                    case 3:
                        btVaga3.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga3.setBackgroundColor(GREEN);
                            }else{
                                btVaga3.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga3.setBackgroundColor(RED);
                        }
                        idVagaBt3 = Integer.valueOf(vagas.getId());
                        break;
                    case 4:
                        btVaga4.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga4.setBackgroundColor(GREEN);
                            }else{
                                btVaga4.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga4.setBackgroundColor(RED);
                        }
                        idVagaBt4 = Integer.valueOf(vagas.getId());
                        break;
                    case 5:
                        btVaga5.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga5.setBackgroundColor(GREEN);
                            }else{
                                btVaga5.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga5.setBackgroundColor(RED);
                        }
                        idVagaBt5 = Integer.valueOf(vagas.getId());
                        break;
                    case 6:
                        btVaga6.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga6.setBackgroundColor(GREEN);
                            }else{
                                btVaga6.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga6.setBackgroundColor(RED);
                        }
                        idVagaBt6 = Integer.valueOf(vagas.getId());
                        break;
                    case 7:
                        btVaga7.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga7.setBackgroundColor(GREEN);
                            }else{
                                btVaga7.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga7.setBackgroundColor(RED);
                        }
                        idVagaBt7 = Integer.valueOf(vagas.getId());
                        break;
                    case 8:
                        btVaga8.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga8.setBackgroundColor(GREEN);
                            }else{
                                btVaga8.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga8.setBackgroundColor(RED);
                        }
                        idVagaBt8 = Integer.valueOf(vagas.getId());
                        break;
                    case 9:
                        btVaga9.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga9.setBackgroundColor(GREEN);
                            }else{
                                btVaga9.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga9.setBackgroundColor(RED);
                        }
                        idVagaBt9 = Integer.valueOf(vagas.getId());
                        break;
                    case 10:
                        btVaga10.setText("  Vaga  "+ vagas.getNome() + "   ");
                        if(vagas.getDisponivel().equalsIgnoreCase("true")) {
                            if (!vagas.getDeficiente().equalsIgnoreCase("true")){
                                btVaga10.setBackgroundColor(GREEN);
                            }else{
                                btVaga10.setBackgroundColor(BLUE);
                            }
                        }else {
                            btVaga10.setBackgroundColor(RED);
                        }
                        idVagaBt10 = Integer.valueOf(vagas.getId());
                        break;
                }
            }
            dialog.dismiss();
        }

    }


    @Override
    public void onClick(View v) {

        if(v == btVaga1){
            vagaParaReserva = idVagaBt1;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga2){
            vagaParaReserva = idVagaBt2;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga3){
            vagaParaReserva = idVagaBt3;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga4){
            vagaParaReserva = idVagaBt4;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga5){
            vagaParaReserva = idVagaBt5;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga6){
            vagaParaReserva = idVagaBt6;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga7){
            vagaParaReserva = idVagaBt7;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga8){
            vagaParaReserva = idVagaBt8;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga9){
            vagaParaReserva = idVagaBt9;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
        }else if(v == btVaga10){
            vagaParaReserva = idVagaBt10;
            carroParaReserva = idPlacaCarro;
            new VerificarReservaVaga().execute();
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
//                        Toast refresh = Toast.makeText(VagasActivity.this, "Refresh!", Toast.LENGTH_SHORT);
//                        refresh.show();
                        ListarVagasJson();
                    }
                });
            }
        }, 0, 40000); // 40 secs
    }



    private class ReservarVaga extends AsyncTask<Void, Void, List<VagasDTO>> {
        private String text;

        @Override
        protected List<VagasDTO> doInBackground(Void... voids) {

            String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/ReservarVaga?Id_Vaga="+ vagaParaReserva +"&Id_Carro="+ carroParaReserva;

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = httpclient.execute(request);
                InputStream content = response.getEntity().getContent();
                Reader reader = new InputStreamReader(content);
//                Gson gson = new Gson();
//                vagasList = Arrays.asList(gson.fromJson(reader, VagasDTO[].class));
                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(VagasActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_SHORT);
                toast.show();
                Log.e("Error", (String) getText(R.string.msg_erro_servico));

            }
            return vagasList;
        }

        protected void onPostExecute(List<VagasDTO> vagasList) {

            Log.d("Listando Vagas", "onPostExecute");
            int varControleVagas = 0;
            for (VagasDTO vagas : vagasList) {
                varControleVagas++;
            }
            ListarVagasJson();
            dialog.dismiss();
        }

    }


    private class CancelarVaga extends AsyncTask<Void, Void, List<VagasDTO>> {
        private String text;

        @Override
        protected List<VagasDTO> doInBackground(Void... voids) {

            String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/CancelarReserva?Id_Vaga="+ vagaParaReserva +"&Id_Carro="+ carroParaReserva;

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = httpclient.execute(request);
                InputStream content = response.getEntity().getContent();
                Reader reader = new InputStreamReader(content);
//                Gson gson = new Gson();
//                vagasList = Arrays.asList(gson.fromJson(reader, VagasDTO[].class));
                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(VagasActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_SHORT);
                toast.show();
                Log.e("Error", (String) getText(R.string.msg_erro_servico));

            }
            return vagasList;
        }

        protected void onPostExecute(List<VagasDTO> vagasList) {

            ListarVagasJson();
            dialog.dismiss();
        }

    }

    private class VerificarReservaVaga extends AsyncTask<Void, Void, List<SituacaoVagaDTO>> {
        private String text;

        @Override
        protected List<SituacaoVagaDTO> doInBackground(Void... voids) {

            String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/ConsultaSituacaoVaga?Id_Vaga="+ vagaParaReserva +"&Id_Carro="+ carroParaReserva;

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = httpclient.execute(request);
                InputStream content = response.getEntity().getContent();
                Reader reader = new InputStreamReader(content);
                Gson gson = new Gson();
                sitVagasList = Arrays.asList(gson.fromJson(reader, SituacaoVagaDTO[].class));
                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(VagasActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_SHORT);
                toast.show();
                Log.e("Error", (String) getText(R.string.msg_erro_servico));

            }
            return sitVagasList;
        }

        protected void onPostExecute(List<SituacaoVagaDTO> sitVagasList) {

            Log.d("Verificar Sit. Vaga", "onPostExecute");
            int varControleVagas = 0;
            for (SituacaoVagaDTO vaga : sitVagasList) {
                varControleVagas++;
                situacaoVaga = Boolean.valueOf(vaga.getVagaAindaReservada());

            }
            if(!situacaoVaga){
                new ReservarVaga().execute();
            }else{
                new CancelarVaga().execute();
            }
            dialog.dismiss();
        }

    }

}
