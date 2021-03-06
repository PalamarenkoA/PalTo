package com.geekhub.palto.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.R;
import com.geekhub.palto.customviews.splash.MySplashScreenPagerAdapter;
import com.geekhub.palto.customviews.splash.SplahFrag;
import com.geekhub.palto.databinding.ActivityLogInBinding;
import com.geekhub.palto.object.User;
import com.geekhub.palto.object.UserForSearch;
import com.geekhub.palto.servise.MessageListenerOLD;
import com.geekhub.palto.viewmodel.LoginActivityViewModel;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;

public class LogInActivity extends AppCompatActivity implements SplahFrag.OnFragmentInteractionListener {

    private LoginActivityViewModel model;
    Firebase myFirebaseRef;
    SharedPreferences.Editor edit;
    Context context;
    ArrayList<String> ID;
    public  static boolean authorized = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/").child("members");
        ID = new ArrayList<>();
        context = this;
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        for(int i = 0;i<fingerprints.length;i++){
            Log.d("log",fingerprints[i]);
        }
        ActivityLogInBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in);
        MySplashScreenPagerAdapter adapter = new MySplashScreenPagerAdapter(getSupportFragmentManager());
        model = new LoginActivityViewModel(this, binding, adapter.getCount());
        binding.setModel(model);
        binding.pager.setAdapter(adapter);
    }
    public void addToFirebase(VKAccessToken res){
        edit = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString("VKAccessToken", res.accessToken).apply();
        edit.putString("VKUserID", res.userId).apply();
        authorized=true;
        Intent serviseIntent = new Intent(context, MessageListenerOLD.class);
        serviseIntent.putExtra("ID",res.userId);
        context.startService(serviseIntent);
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,"sex, bdate,city,country, photo_200,photo_max"));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList abc = (VKList) response.parsedModel;

                try {

                    User user = new User(abc.get(0).fields);
                    edit.putString("VKmaxIcon", String.valueOf(abc.get(0).fields.get("photo_max")));
                    edit.putString("VKUserICON",user.getPhoto_200()).apply();
                    edit.putString("VKUserNAME",user.getName()+" "+user.getLastName()).apply();
                    SharedPreferences srefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    Log.d("logo",srefs.getString("VKUserID","dddd"));
                    myFirebaseRef.child(abc.get(0).fields.getString("id")).setValue(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent = new Intent(getApplicationContext(), FirstSettingsActivity.class);
        startActivity(intent);
        finish();
    }
    public void dontaddToFirebase(VKAccessToken res){    edit = PreferenceManager.
            getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString("VKAccessToken", res.accessToken).apply();
        edit.putString("VKUserID", res.userId).apply();
        authorized=true;
        Intent serviseIntent = new Intent(context, MessageListenerOLD.class);
        serviseIntent.putExtra("ID",res.userId);
        context.startService(serviseIntent);
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,"sex, bdate,city,country, photo_200,photo_max"));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList abc = (VKList) response.parsedModel;

                try {

                    User user = new User(abc.get(0).fields);
                    edit.putString("VKmaxIcon", String.valueOf(abc.get(0).fields.get("photo_max")));
                    edit.putString("VKUserICON",user.getPhoto_200()).apply();
                    edit.putString("VKUserNAME", user.getName() + " " + user.getLastName()).apply();
                    SharedPreferences srefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent = new Intent(getApplicationContext(), FirstSettingsActivity.class);
        startActivity(intent);
        finish();}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(final VKAccessToken res) {
                // Пользователь успешно авторизовался
                myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                        if(iterator.hasNext()){
                            do{
                                DataSnapshot dataSnapshot1 = iterator.next();
                                UserForSearch userForSearch = dataSnapshot1.getValue(UserForSearch.class);
                                ID.add(userForSearch.getId());

                            }while (iterator.hasNext());
                            if(ID.contains(res.userId)){
                                dontaddToFirebase(res);
                            }else{
                                addToFirebase(res);
                            }
                        }else{
                        addToFirebase(res);}
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, п
                // ользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
