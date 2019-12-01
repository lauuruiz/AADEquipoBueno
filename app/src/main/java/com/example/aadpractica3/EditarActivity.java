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
import com.example.aadpractica3.model.data.Equipo;

public class EditarActivity extends AppCompatActivity {

    private String nombre, ciudad, estadio, escudo;
    private int aforo;

    private ImageView imEquipo;
    private EditText etNombre, etCiudad, etEstadio, etAforo;
    private Button btImagen, btGuardar;

    private Equipo equipo;

    public static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Bundle equipoEditar = getIntent().getExtras();
        equipo = null;
        if(equipoEditar!=null){
            equipo = equipoEditar.getParcelable("equipo");
            initDatos();
            initComponents();
            initEvents();
        }
    }

    private void initDatos(){
        nombre = equipo.getNombre();
        ciudad = equipo.getCiudad();
        estadio = equipo.getEstadio();
        aforo = equipo.getAforo();
        escudo = equipo.getEscudo();
    }

    private void initComponents(){
        imEquipo = findViewById(R.id.imEquipoEditar);

        etNombre = findViewById(R.id.etNombre);
        etCiudad = findViewById(R.id.etCiudad);
        etEstadio = findViewById(R.id.etEstadio);
        etAforo = findViewById(R.id.etAforo);

        btImagen = findViewById(R.id.btImagenA);
        btGuardar = findViewById(R.id.btGuardar);
    }

    private void initEvents(){
        Glide.with(this).load(escudo).into(imEquipo);

        etNombre.setText(nombre);
        etCiudad.setText(ciudad);
        etEstadio.setText(estadio);
        etAforo.setText(""+aforo);

        btImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = equipo.getId();
                equipo.setId(id);
                equipo.setNombre(etNombre.getText().toString());
                equipo.setCiudad(etCiudad.getText().toString());
                equipo.setEstadio(etEstadio.getText().toString());
                equipo.setAforo(Integer.parseInt(etAforo.getText().toString()));
                equipo.setEscudo(escudo);

                EquipoActivity.viewModel.editEquipo(equipo);

                Intent volver = new Intent(v.getContext(), EquipoActivity.class);
                startActivity(volver);
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

            Glide.with(this).load(imagenUri).into(imEquipo);

            escudo = imagenUri.toString();
        }
    }
}
