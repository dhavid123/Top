package com.damg.top;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.containerMain)
    CoordinatorLayout containerMain;

    private ArtistaAdapter adapter;
    public static final Artista sArtista =  new Artista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configAdapter();
        configRecyclerView();

        generateArtist();
    }

    private void generateArtist() {
        String[] nombres = {"David", "Andres", "Carlos", "Jairo"};
        String[] apellidos = {"Montes", "Garcia", "Gutierrez", "Agudelo"};
        long[] nacimientos = {596851200000L, 565228800000L, 533692800000L, 502156800000L};
        String[] lugares = {"Colombia", "USA", "Canada", "Mexico"};
        short[] estaturas = {180, 190, 200, 175};
        String[] notas = {"No tiene notas", "Tampoco Tiene Notas", "Menos que tiene notas", "Este si tiene notas"};
        String[] fotos = {"https://upload.wikimedia.org/wikipedia/commons/d/df/Harris%27s_Hawk_Parabuteo_unicinctus_%28Temminck_1824%29.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/c/c0/Cat_Briciola_with_pretty_and_different_colour_of_eyes.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/6/6b/Tiny_katydid_%282499011827%29.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/f/ff/Spikey_iguana_%2823480520093%29.jpg"};


        int nroRegistros = nombres.length;
        for (int i = 0; i < nroRegistros; i++) {

            Artista artista = new Artista(i + 1, nombres[i], apellidos[i], nacimientos[i],
                    lugares[i], estaturas[i], notas[i], i + 1, fotos[i]);
            adapter.addArtista(artista);
        }
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configAdapter() {
        adapter = new ArtistaAdapter(new ArrayList<Artista>(), this);
    }

    private void configRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Metodos implementados por la interface OnItemClickListener.
    @Override
    public void OnItemClick(Artista artista) {

    }

    @Override
    public void OnLongItemClick(Artista artista) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            //REVISAR ESTA PARTE!!!
            adapter.addArtista(sArtista);
        }
    }

    @OnClick(R.id.fab)
    public void addArtist() {

        //Lanzar la vista desde el boton y enviar datos con el putExtra.
        Intent intent = new Intent(MainActivity.this, AddArtistActivity.class);

        intent.putExtra(Artista.ORDEN, adapter.getItemCount() + 1);
        //startActivity(intent);
        startActivityForResult(intent,1);


    }
}
