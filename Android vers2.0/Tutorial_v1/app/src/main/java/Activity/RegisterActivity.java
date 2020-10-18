package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorial_v1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import Model.UserAccount;
import Retrofit.IMyService;
import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.Response;
import Retrofit.*;

public class RegisterActivity extends AppCompatActivity {
    EditText taikhoanText,sdtText,motaText, hotenText, matkhauText, xacnhanText,diachiText,gioitinhText;
    TextView logText;
    Button RegButton;
    String hoten = "";
    String mail = "";
    String sdt = "";
    String matkhau = "";
    String xacnhan= "";
    String diachi="";
    String mota="";
    String gioitinh="";
    String token="";
    String failText="";
    public static final String URL = "http://149.28.24.98:9000/register";
    UserAccount userAccount;
    IMyService iMyService;
    Boolean flag=false;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        taikhoanText=findViewById(R.id.taikhoanText);
        sdtText=findViewById(R.id.sdtText);
        hotenText=findViewById(R.id.HoTenText);
        matkhauText=findViewById(R.id.matkhauText);
        xacnhanText=findViewById(R.id.xacnhanText);
        logText=findViewById(R.id.logText);
        RegButton=findViewById(R.id.regisBtn);
        gioitinhText=findViewById(R.id.gioitinhText);
        diachiText=findViewById(R.id.diachiText);
        motaText=findViewById(R.id.motaText);

        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);
        alertDialog= new SpotsDialog.Builder().setContext(this).build();

        logText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckValidInput()) {

                    register();
                }

            }
        });
    }

    //đăng kí
    private void register() {

        RegButton.setClickable(false);
        RegButton.setEnabled(false);

        alertDialog.show();
        iMyService.registerUser(hoten,mail,matkhau,sdt,diachi,mota,gioitinh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {

                        if (stringResponse.isSuccessful()) {

                            if (stringResponse.body().toString().contains("name")) {
                                String responseString = stringResponse.body().toString();

                                try {
                                    JSONObject jo = new JSONObject(responseString);
                                    userAccount = new UserAccount(hoten, "", sdt, "", mail, stringResponse.headers().get("auth-token"), gioitinh, mota, diachi, matkhau, jo.getString("_id"));
                                    flag = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(RegisterActivity.this, "Mail đã tồn tại", Toast.LENGTH_SHORT).show();
                                flag = false;
                            }
                        } else {
                            failText = "cc";
                            String responseString = null;
                            try {
                                responseString = stringResponse.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            try {
                                JSONObject parent = new JSONObject(responseString);
                                JSONArray jo = parent.getJSONArray("errors");
                                for (int i = 0; i < jo.length(); i++) {
                                    JSONObject jsonObject = jo.getJSONObject(i);
                                    failText = jsonObject.getString("msg");
                                }
                                flag = false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                failText = e.toString();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        alertDialog.dismiss();

                                    }
                                }, 500);
                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                        flag = false;

                    }

                    @Override
                    public void onComplete() {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        alertDialog.dismiss();

                                    }
                                }, 500);


                        if (flag == true) {
                            Intent intent = new Intent(RegisterActivity.this, Active_Account.class);
                            intent.putExtra("userAcc", userAccount);
                            startActivity(intent);

                        } else {
                            Toast.makeText(RegisterActivity.this, failText, Toast.LENGTH_SHORT).show();
                            RegButton.setClickable(true);
                            RegButton.setEnabled(true);
                        }

                    }
                });

    }

    //Check độ dài kí tự
    private boolean CheckValidInput() {
        boolean valid = true;
        hoten = hotenText.getText().toString();
        mail = taikhoanText.getText().toString().trim();
        sdt = sdtText.getText().toString().trim();
        matkhau = matkhauText.getText().toString().trim();
        xacnhan = xacnhanText.getText().toString().trim();
        diachi=diachiText.getText().toString();
        mota=motaText.getText().toString();
        gioitinh=gioitinhText.getText().toString();
        if(hoten.isEmpty()||hoten.length()<3 || hoten.length()>40)
        {
            hotenText.setError("Tên từ 3 đến 40 ký tự");
            valid = false;
        } else {
            hotenText.setError(null);
        }

        if(mail.isEmpty() || mail.length() < 3 || mail.length() >40 )
        {
            taikhoanText.setError("Từ 6 đến 40 ký tự");
            valid = false;
        } else {
            taikhoanText.setError(null);
        }
        if(sdt.isEmpty())
        {
            sdtText.setError("Nhập số điện thoại không hợp lệ ");
            valid = false;
        } else {
            sdtText.setError(null);
        }

        if(matkhau.isEmpty() || matkhau.length() <8 || matkhau.length()>16 )
        {
            matkhauText.setError("Mật khẩu có 8 đến 16 ký tự");
            valid = false;
        } else {
            matkhauText.setError(null);
        }

        if(xacnhan.isEmpty() || xacnhan.length() < 8 || xacnhan.length()>16 || !xacnhan.equals(matkhau) )
        {
            xacnhanText.setError("Mật khẩu không khớp");
            valid = false;
        } else{
           xacnhanText.setError(null);
        }

        return valid;


    }
}
