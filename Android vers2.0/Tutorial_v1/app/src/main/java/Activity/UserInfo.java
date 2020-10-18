package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorial_v1.R;

import org.json.JSONException;
import org.json.JSONObject;

import Model.UserAccount;
import Retrofit.IMyService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UserInfo extends AppCompatActivity {
    Button updateBtn;
    EditText etHoTen, etSdt, etDiachi, etMota, etGioitinh;
    UserAccount userAccount = new UserAccount();
    String hoten, sdt, diachi, mota, gioitinh;
    IMyService iMyService;
    boolean flag = false;
    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        etHoTen = findViewById(R.id.userinfoName);
        etSdt = findViewById(R.id.userinfoPhone);
        etDiachi = findViewById(R.id.userinfoAddress);
        etMota = findViewById(R.id.userinfoDescript);
        etGioitinh = findViewById(R.id.userinfoGender);
        updateBtn = findViewById(R.id.ProfileUpdate);

        userAccount = (UserAccount) getIntent().getSerializableExtra("userAcc");
        sharedPreferences = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        hoten = sharedPreferences.getString("name", "");
        sdt = sharedPreferences.getString("phone", "");
        diachi = sharedPreferences.getString("address", "");
        gioitinh = sharedPreferences.getString("gender", "");

        etHoTen.setText(hoten);
        etSdt.setText(sdt);
        etDiachi.setText(diachi);
        etGioitinh.setText(gioitinh);
        etMota.setText(mota);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidInput())
                    updateProfile();
            }
        });
    }

    private boolean checkValidInput() {
        boolean valid = false;
        hoten = etHoTen.getText().toString();
        sdt = etSdt.getText().toString();
        diachi = etDiachi.getText().toString();
        mota = etMota.getText().toString();
        gioitinh = etGioitinh.getText().toString();
        if (hoten.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || gioitinh.isEmpty() || mota.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            valid = false;
        } else valid = true;
        return valid;
    }

    private void updateProfile() {
        updateBtn.setClickable(false);
        updateBtn.setEnabled(false);

        alertDialog.show();
        iMyService.changeProfile(hoten, sdt, diachi, mota, gioitinh, userAccount.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> response) {


                        if (response.isSuccessful()) {


                            if (response.body().toString().contains("success")) {
                                String responeString = response.body().toString();
                                try {
                                    JSONObject jo = new JSONObject(responeString);
                                    flag = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                flag = false;
                            }
                        } else {
                            int a = response.code();
                            Toast.makeText(UserInfo.this, "code: " + a, Toast.LENGTH_LONG).show();
                            flag = false;
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
                        Toast.makeText(UserInfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(UserInfo.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            final Intent data = new Intent();

                            data.putExtra("usernewAcc", userAccount);

                            setResult(Activity.RESULT_OK, data);

                            finish();
                        } else
                            Toast.makeText(UserInfo.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        updateBtn.setEnabled(true);
                        updateBtn.setClickable(true);
                    }
                });
    }
}