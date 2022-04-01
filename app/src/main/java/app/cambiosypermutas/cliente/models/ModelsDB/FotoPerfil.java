package app.cambiosypermutas.cliente.models.ModelsDB;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

import de.hdodenhof.circleimageview.CircleImageView;

public class FotoPerfil extends SugarRecord {

    @Unique
    @Column(name="foto")
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
