package com.example.filmonerisitem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UyeActivity extends AppCompatActivity {

    private EditText editAdSoyad, editEmail, editSifre;
    private String txtAdSoyad, txtEmail, txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String, Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye);

        // XML dosyasındaki EditText bileşenlerini Java'da kullanılabilir hale getirme
        editEmail = findViewById(R.id.uyeemail);
        editSifre = findViewById(R.id.uyeparola);
        editAdSoyad = findViewById(R.id.uyeadsoyad);

        // Firebase Authentication nesnesini başlatma
        mAuth = FirebaseAuth.getInstance();

        // Firebase veritabanı referansını başlatma
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    // Kayıt Ol butonuna tıklandığında çağrılan fonksiyon
    public void kayitOl(View v) {
        // EditText bileşenlerinden kullanıcının girdiği ad, soyad, e-posta ve şifreyi alma
        txtAdSoyad = editAdSoyad.getText().toString();
        txtEmail = editEmail.getText().toString();
        txtSifre = editSifre.getText().toString();

        // Ad ve Soyad alanlarının boş olup olmadığını kontrol etme
        if (TextUtils.isEmpty(txtAdSoyad)) {
            editAdSoyad.setError("Ad ve soyad boş bırakılamaz");
            return;
        }

        // E-posta alanının boş olup olmadığını kontrol etme
        if (TextUtils.isEmpty(txtEmail)) {
            editEmail.setError("E-mail adresinizi yazınız");
            return;
        } else if (!isEmailValid(txtEmail)) { // E-postanın geçerli bir formatta olup olmadığını kontrol etme
            editEmail.setError("Geçerli bir e-posta adresi giriniz");
            return;
        }

        // Şifre alanının boş olup olmadığını ve minimum uzunluğu kontrol etme
        if (TextUtils.isEmpty(txtSifre)) {
            editSifre.setError("Şifrenizi giriniz");
            return;
        } else if (txtSifre.length() < 6) { // Şifrenin minimum 6 karakterden oluşup oluşmadığını kontrol etme
            editSifre.setError("Şifre en az 6 karakterden oluşmalıdır");
            return;
        }

        // Firebase Authentication ile yeni kullanıcı oluşturma
        mAuth.createUserWithEmailAndPassword(txtEmail, txtSifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Yeni kullanıcı oluşturulduğunda yapılacak işlemler

                            // Oluşturulan kullanıcının FirebaseUser nesnesini alma
                            mUser = mAuth.getCurrentUser();

                            // Yeni kullanıcının bilgilerini HashMap kullanarak oluşturma
                            mData = new HashMap<>();
                            mData.put("kullaniciAdi", txtAdSoyad);
                            mData.put("kullaniciEmail", txtEmail);
                            mData.put("kullaniciSifre", txtSifre);
                            mData.put("kullaniciId", mUser.getUid());

                            // Veritabanına kullanıcının bilgilerini ekleme
                            mReference.child("Kullanıcılar").child(mUser.getUid())
                                    .setValue(mData)
                                    .addOnCompleteListener(UyeActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                                Toast.makeText(UyeActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(UyeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            // Kayıt işlemi başarılı olduğunda Giriş Ekranına geçiş
                            Intent intent = new Intent(UyeActivity.this, GirisActivity.class);
                            startActivity(intent);
                            finish(); // Kayıt ekranını kapatma
                        } else {
                            // Kayıt işlemi başarısız olduğunda kullanıcıya bilgi verme
                            Toast.makeText(UyeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // E-posta adresinin geçerli bir formatta olup olmadığını kontrol eden fonksiyon
    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}