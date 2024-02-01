package com.example.filmonerisitem;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GirisActivity extends AppCompatActivity {
    private EditText editEmail, editSifre;
    private String txtEmail, txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        // XML dosyasındaki EditText bileşenlerini Java'da kullanılabilir hale getirme
        editEmail = findViewById(R.id.girisemail);
        editSifre = findViewById(R.id.girisparola);

        // Firebase Authentication nesnesini başlatma
        mAuth = FirebaseAuth.getInstance();
    }

    // Giriş butonuna tıklandığında çağrılan fonksiyon
    public void oturumAc(View view) {
        // EditText bileşenlerinden kullanıcının girdiği e-posta ve şifreyi alma
        txtEmail = editEmail.getText().toString();
        txtSifre = editSifre.getText().toString();

        // E-posta alanının boş olup olmadığını kontrol etme
        if (TextUtils.isEmpty(txtEmail)) {
            editEmail.setError("E-mail adresinizi yazınız");
            return;
        } else if (!isEmailValid(txtEmail)) { // E-postanın geçerli bir formatta olup olmadığını kontrol etme
            editEmail.setError("Geçerli bir e-posta adresi giriniz");
            return;
        }

        // Şifre alanının boş olup olmadığını kontrol etme
        if (TextUtils.isEmpty(txtSifre)) {
            editSifre.setError("Şifrenizi giriniz");
            return;
        }

        // Firebase Authentication ile giriş yapma
        mAuth.signInWithEmailAndPassword(txtEmail, txtSifre)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Giriş başarılı olduğunda yapılacak işlemler

                        // Giriş yapan kullanıcının FirebaseUser nesnesini alma
                        mUser = mAuth.getCurrentUser();

                        // Kullanıcının veritabanındaki bilgilerini içeren referansı alma
                        mReference = FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mUser.getUid());

                        // Veritabanındaki bilgileri dinlemek için ValueEventListener eklemek
                        mReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // Veritabanındaki her bir veri değiştiğinde yapılacak işlemler

                                // Örneğin, her bir veriyi ekrana yazdırma
                                for (DataSnapshot snp : snapshot.getChildren()) {
                                    System.out.println(snp.getKey() + " = " + snp.getValue());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Veritabanına erişirken hata olması durumunda yapılacak işlemler
                                Toast.makeText(GirisActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Giriş başarılı olduğunda Ana Ekran'a geçme
                        Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Giriş ekranını kapatma
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Giriş başarısız olduğunda yapılacak işlemler
                        Toast.makeText(GirisActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // E-posta adresinin geçerli bir formatta olup olmadığını kontrol eden fonksiyon
    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Yeni Üyelik butonuna tıklandığında çağrılan fonksiyon
    public void yeniUyelik(View view) {
        // Yeni Üyelik Ekranına geçiş
        Intent intent = new Intent(GirisActivity.this, UyeActivity.class);
        startActivity(intent);
    }

    // Parolamı Unuttum butonuna tıklandığında çağrılan fonksiyon
    public void parolamıUnuttum(View view) {
        // Şifre Sıfırlama Ekranına geçiş
        Intent intent = new Intent(GirisActivity.this, PsifirlaActivity.class);
        startActivity(intent);
    }
}