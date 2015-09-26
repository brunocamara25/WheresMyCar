//package br.com.wheresmycar.Activity;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import br.com.wheresmycar.dto.VagasDTO;
//import wheresmycar.com.br.wheresmycar.R;
//
//import static android.graphics.Color.GREEN;
//import static android.graphics.Color.RED;
//
////private ProgressDialog progresso = new ProgressDialog(this);
//
//public class GetSetDataWeb extends AsyncTask<Void, Void, String> {
//
//    private String text;
//
//    @Override
//    protected String doInBackground(Void... voids) {
//
//        String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/ReservarVaga?Id_Vaga="+ idVaga +"&Id_Carro="+ IdCarro;
//
//        try {
//
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpGet request = new HttpGet();
//            request.setURI(new URI(url));
//            HttpResponse response = httpclient.execute(request);
//            InputStream content = response.getEntity().getContent();
//            Reader reader = new InputStreamReader(content);
//            Gson gson = new Gson();
//            vagasList = Arrays.asList(gson.fromJson(reader, VagasDTO[].class));
//            content.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
////            Toast toast = Toast.makeText(VagasActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_SHORT);
////            toast.show();
//           // Log.e("Error", (String) getText(R.string.msg_erro_servico));
//
//        }
//        return "";
//    }
//
//    protected void onPostExecute(List<VagasDTO> vagasList) {
//
//        Log.d("Listando Vagas", "onPostExecute");
//        int varControleVagas = 0;
//        for (VagasDTO vagas : vagasList) {
//            varControleVagas++;
//
//
//        }
//        dialog.dismiss();
//    }
//
//}