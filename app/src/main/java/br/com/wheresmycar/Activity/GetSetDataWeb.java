package br.com.wheresmycar.Activity;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

//private ProgressDialog progresso = new ProgressDialog(this);

public class GetSetDataWeb extends AsyncTask<Void, Void, String> {

    private String url, method, data;

    public GetSetDataWeb(String url, String method, String data){
        this.url = url;
        this.method = method;
        this.data = data;
    }


    @Override
    protected void onPreExecute() {

//        progresso.setMessage("Aguarde...");
//        progresso.show();
    }


    protected String doInBackground(Void... params) {
        String answer = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("method", method));
            valores.add(new BasicNameValuePair("json", data));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        }
        catch(NullPointerException e){ e.printStackTrace(); }
        catch(ClientProtocolException e){ e.printStackTrace(); }
        catch(IOException e){ e.printStackTrace(); }
        return answer;
    }


    protected void onPostExecute(String result) {
//        progresso.dismiss();
//        if(result.equals("3")){
//            Toast.makeText(MainActivity.this, "Código da igreja inesistente, por favor escreva um código válido.",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }else if (result.equals("2")){
//            Toast.makeText(MainActivity.this, "Senha incorreto, por favor escreva uma senha válida.",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }else if (result.equals("1"))
//        {
//            progresso.setMessage("E-mail incorreto, por favor escreva um email válido.");
//            Toast.makeText(MainActivity.this, "E-mail incorreto, por favor escreva um email válido.",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Toast.makeText(MainActivity.this, "Seja bem vindo", Toast.LENGTH_SHORT).show();
//        Intent it = new Intent(MainActivity.this, MC_Home.class);
//        startActivity(it);
    }

}