package Retrofit;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import  retrofit2.Call;
import  retrofit2.http.Body;
import retrofit2.http.PUT;


public interface IMyService {
    @POST("login")
    @FormUrlEncoded
    Observable<Response<String>> loginUser(@Field("email") String email, @Field("password") String actToken);

    //register
    @POST("register")
    @FormUrlEncoded
    Observable<Response<String>> registerUser (@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("password") String password,
                                               @Field("phone") String phone,
                                               @Field("address") String address,
                                               @Field("description") String description,
                                               @Field("gender") String gender);

    //Active Account
    @POST("active-account")
    @FormUrlEncoded
    Observable<String>  activeAccount(@Field("email") String email,
                                      @Field("activeToken") String actToken);

    //UserInfo Activity
    @PUT("change-profile")
    @FormUrlEncoded
    Observable<Response<String>>  changeProfile(@Field("name") String oldPass,
                                                @Field("phone") String phone,
                                                @Field("address") String address,
                                                @Field("description") String description,
                                                @Field("gender") String gender,
                                                @Header("auth-token") String authToken);

}
