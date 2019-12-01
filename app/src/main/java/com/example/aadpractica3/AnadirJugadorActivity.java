package com.example.aadpractica3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aadpractica3.model.data.Equipo;
import com.example.aadpractica3.model.data.Jugador;

public class AnadirJugadorActivity extends AppCompatActivity {

    private String nombre, apellidos, foto = Integer.toString(R.drawable.futbolista);
    private int idequipo;

    private EditText etNombre, etApellidos;
    private ImageView imJugador;
    private Button btImagen, btCambios;

    public static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_jugador);

        idequipo = getIntent().getIntExtra("id", 1);
        Log.v("idequipo2", ""+idequipo);

        initComponents();
        initEvents();
    }

    private void initComponents(){
        etNombre = findViewById(R.id.etNombreO);
        etApellidos = findViewById(R.id.etApellidosO);

        imJugador = findViewById(R.id.imJugadorO);

        btImagen = findViewById(R.id.btImagenO);
        btCambios = findViewById(R.id.btCambiosO);
    }

    private void initEvents(){
        btImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

        btCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Jugador jugador = new Jugador();
                jugador.setNombre(etNombre.getText().toString());
                jugador.setApellidos(etApellidos.getText().toString());
                jugador.setFoto(foto);
                jugador.setidequipo(idequipo);

                PlantillaActivity.viewModel.addJugador(jugador);

                Toast.makeText(AnadirJugadorActivity.this, "Insertado en equipo con id: "+idequipo, Toast.LENGTH_SHORT).show();
                //finish();
                Intent i = new Intent(v.getContext(), PlantillaActivity.class);
                i.putExtra("id", idequipo);
                startActivity(i);
            }
        });
    }

    private void seleccionarImagen(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PHOTO_SELECTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_SELECTED && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imagenUri = data.getData();

            Glide.with(this)
                    .asBitmap()
                    .load(imagenUri)
                    .error(R.drawable.futbolista)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imJugador);
            foto = imagenUri.toString();
        }
    }
}

