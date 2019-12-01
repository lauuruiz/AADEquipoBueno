package com.example.aadpractica3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aadpractica3.model.data.Equipo;

public class AnadirActivity extends AppCompatActivity {

    private String nombre, ciudad, estadio, escudo = Integer.toString(R.drawable.futbol);
    private int aforo;

    private EditText etNombre, etCiudad, etEstadio, etAforo, etEscudo;
    private ImageView imEquipo;
    private Button btImagen, btAnadir;

    public static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);

        initComponents();
        initEvents();
    }

    private void initComponents(){
        etNombre = findViewById(R.id.etNombreA);
        etCiudad = findViewById(R.id.etCiudadA);
        etEstadio = findViewById(R.id.etEstadioA);
        etAforo = findViewById(R.id.etAforoA);

        imEquipo = findViewById(R.id.imEquipoA);

        btImagen = findViewById(R.id.btImagenA);
        btAnadir = findViewById(R.id.btAnadir);
    }

    private void initEvents(){
        btImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

        btAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Equipo equipo = new Equipo();
                equipo.setNombre(etNombre.getText().toString());
                equipo.setCiudad(etCiudad.getText().toString());
                equipo.setEstadio(etEstadio.getText().toString());
                equipo.setAforo(Integer.parseInt(etAforo.getText().toString()));
                equipo.setEscudo(escudo);


                EquipoActivity.viewModel.addEquipo(equipo);

                //finish();
                Intent i = new Intent(v.getContext(), EquipoActivity.class);
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
                    .error(R.drawable.futbol)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imEquipo);
            escudo = imagenUri.toString();
        }
    }
}

