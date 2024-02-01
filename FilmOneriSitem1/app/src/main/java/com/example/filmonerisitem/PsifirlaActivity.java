package com.example.filmonerisitem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PsifirlaActivity extends AppCompatActivity {
    private TextView psifirlaBaslik, psifirlaMesaj, psifirlaSozlesme;
    private EditText psifirlaEmail;
    private Button psifirlaOturumAc, psifirlaButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psifirla);

        // XML dosyasındaki bileşenleri Java'da kullanılabilir hale getirme
        psifirlaBaslik = findViewById(R.id.psifirlabaslik);
        psifirlaMesaj = findViewById(R.id.psifirlamesaj);
        psifirlaEmail = findViewById(R.id.psifirlaemail);
        psifirlaOturumAc = findViewById(R.id.psifirlamaoturumacbutton);
        psifirlaButton = findViewById(R.id.psifirlamabutton);

        // Firebase Authentication nesnesini başlatma
        auth = FirebaseAuth.getInstance();

        // Şifre sıfırlama butonuna tıklandığında yapılacak işlemler
        psifirlaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // E-posta adresini EditText bileşeninden alıp boş olup olmadığını kontrol etme
                String psifirlaEmailText = psifirlaEmail.getText().toString().trim();
                if (TextUtils.isEmpty(psifirlaEmailText)) {
                    psifirlaEmail.setError("Lütfen e-mail adresinizi yazınız.");
                } else {
                    // Firebase Authentication üzerinden şifre sıfırlama e-postası gönderme
                    auth.sendPasswordResetEmail(psifirlaEmailText)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Şifre sıfırlama e-postası başarıyla gönderildiğinde kullanıcıya bilgi verme
                                    Toast.makeText(PsifirlaActivity.this, "E-mail adresinize sıfırlama bağlantısı gönderildi, Lütfen kontrol ediniz.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Şifre sıfırlama işlemi başarısız olduğunda kullanıcıya bilgi verme
                                    Toast.makeText(PsifirlaActivity.this, "Sıfırlama işlemi başarısız.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    // Oturum Aç butonuna tıklandığında yapılacak işlemler
    public void oturumAc(View view) {
        // Giriş Ekranına yönlendirme
        Intent intent = new Intent(PsifirlaActivity.this, GirisActivity.class);
        startActivity(intent);
        finish(); // Aktiviteyi kapatma
    }
}