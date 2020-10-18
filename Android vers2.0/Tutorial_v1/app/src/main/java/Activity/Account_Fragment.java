package Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import Activity.UserInfo;
import Model.UserAccount;
import de.hdodenhof.circleimageview.CircleImageView;
public class Account_Fragment extends Fragment {
    TextView Name,Email;
    UserAccount userAccount;

    public Account_Fragment(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View rootView= inflater.inflate(R.layout.fragment_account, container, false);

        Name=rootView.findViewById (R.id.accountFrag_user_name);
        Name.setText("heeelooo");
        Email=rootView.findViewById(R.id.accountFrag_account);
        Email.setText(userAccount.getMail());
        TextView ThongTinCaNhan=rootView.findViewById(R.id.tvThongTinCaNhan);
        ThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), UserInfo.class);
                intent.putExtra("userAcc", userAccount);
                startActivityForResult(intent,1);
            }
        });
        return rootView;
    }
}

