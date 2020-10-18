package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorial_v1.R;

import Model.UserAccount;
import Retrofit.IMyService;
import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import Retrofit.*;
public class Active_Account extends AppCompatActivity {
    EditText EmailEdtText, activeText;
    Button activeBtn;
    UserAccount userAccount;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    IMyService iMyService;
    String activeCode="";
    String mail="";
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active__account);

        activeText=findViewById(R.id.activeText);
        activeBtn=findViewById(R.id.activebtn);
        userAccount= (UserAccount) getIntent().getSerializableExtra("userAcc");
        alertDialog= new SpotsDialog.Builder().setContext(this).build();
        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);

        mail=userAccount.getMail();
        Toast.makeText(Active_Account.this, "Mã kích hoạt đã được gửi đến mail của bạn", Toast.LENGTH_LONG).show();
        activeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckValidInput())
                    Active();
            }
        });
    }

    private void Active() {

        alertDialog.show();
        iMyService.activeAccount(userAccount.getMail(),activeCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        alertDialog.dismiss();

                                    }
                                }, 500);

                        //JSONObject jObjError = new JSONObject(e.)

                        Toast.makeText(Active_Account.this, "Mã kích hoạt không đúng", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        alertDialog.dismiss();

                                    }
                                }, 500);
                        Toast.makeText(Active_Account.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(Active_Account.this, LoginActivity.class);
                        intent.putExtra("userAcc",userAccount);
                        startActivity(intent);
                        //  CustomIntent.customType(ActiveAccountActivity.this,"bottom-to-up");
                    }
                });
    }

    private boolean CheckValidInput(){
        boolean valid=false;
        activeCode=activeText.getText().toString();
        if(activeCode.isEmpty()) {valid=false;
            Toast.makeText(this, "Nhập mã kích hoạt", Toast.LENGTH_SHORT).show();}
        else valid=true;
        return valid;
    }


}