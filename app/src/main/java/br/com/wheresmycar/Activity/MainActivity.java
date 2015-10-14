package br.com.wheresmycar.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;

import br.com.wheresmycar.util.Util;
import wheresmycar.com.br.wheresmycar.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgVwCaminhoVaga1=(ImageView)findViewById(R.id.imgVwCaminhoVaga1);
        ImageView imgVwCaminhoVaga2=(ImageView)findViewById(R.id.imgVwCaminhoVaga2);
        ImageView imgVwCaminhoVaga3=(ImageView)findViewById(R.id.imgVwCaminhoVaga3);

        Intent intent = getIntent();
        String bitmap1 =  intent.getStringExtra("BitmapImag1");
//        String bitmap2 =  intent.getStringExtra("BitmapImag2");
//        String bitmap3 =  intent.getStringExtra("BitmapImag3");
        Bitmap caminhoEstacionamentoImg1 = Util.base64ToBitmap(bitmap1);
        imgVwCaminhoVaga1.setImageBitmap(caminhoEstacionamentoImg1);
//        Bitmap caminhoEstacionamentoImg2 = Util.base64ToBitmap(bitmap2);
//        imgVwCaminhoVaga2.setImageBitmap(caminhoEstacionamentoImg2);
//        Bitmap caminhoEstacionamentoImg3 = Util.base64ToBitmap(bitmap3);
//        imgVwCaminhoVaga3.setImageBitmap(caminhoEstacionamentoImg3);





    }


}
