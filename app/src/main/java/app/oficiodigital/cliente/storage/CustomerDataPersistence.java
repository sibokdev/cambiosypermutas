package app.oficiodigital.cliente.storage;

import android.content.Context;
import android.content.SharedPreferences;

import app.oficiodigital.cliente.models.Auth;
import app.oficiodigital.cliente.models.User;

/**
 * Created by Roberasd on 26/10/16.
 */

public class CustomerDataPersistence {

    private static final String PREF_NAME = "customer_data_persistence";
    private static final String USER_LOGGED_IN = "is_logged_in";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_LAST_NAME = "user_last_name";
    private static final String USER_LAST_NAME_2 = "user_last_name_2";
    private static final String USER_GENRE = "user_genre";
    private static final String USER_BIRTHDAY = "user_birthdat";
    private static final String USER_PHONE = "user_phone";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_RECOVERY_QUESTION = "user_recovery_question";
    private static final String USER_RECOVERY_ANSWER = "user_recovery_answer";

    private static final String SIGN_COMPLETED = "sign_completed";
    private static final String PAY_STATUS = "pay_status";
    private static final String TOKEN = "token";
    private static final String EXPIRES = "expires";

    private SharedPreferences mManager;
    private SharedPreferences.Editor mEditor;

    public CustomerDataPersistence(Context context) {
        mManager = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mManager.edit();
    }

    public void saveUserData(User user) {
        mEditor.putString(USER_ID, user.getId());
        mEditor.putString(USER_NAME, user.getName());
        mEditor.putString(USER_LAST_NAME, user.getSurname1());
        mEditor.putString(USER_LAST_NAME_2, user.getSurname2());
        mEditor.putInt(USER_GENRE, user.getGender());
        mEditor.putString(USER_BIRTHDAY, user.getBirthday());
        mEditor.putString(USER_PHONE, user.getPhone());
        mEditor.putString(USER_EMAIL, user.getEmail());
        mEditor.putString(USER_PASSWORD, user.getPassword());
        mEditor.putString(USER_RECOVERY_QUESTION, user.getSecurityQuestion());
        mEditor.putString(USER_RECOVERY_ANSWER, user.getSecurityAnswer());
        setPayStatus(user.getPayStatus());
        setIsSignCompleted(true);
        mEditor.commit();
    }

    public int getPayStatus() {
        return mManager.getInt(PAY_STATUS, 0);
    }

    public void setPayStatus(int pay) {
        mEditor.putInt(PAY_STATUS, pay);
        mEditor.commit();
    }

    public boolean isLoggedIn() {
        return mManager.getBoolean(USER_LOGGED_IN, false);
    }

    public boolean isSignCompleted() {
        return mManager.getBoolean(SIGN_COMPLETED, false);
    }

    public void setIsSignCompleted(boolean completed) {
        mEditor.putBoolean(SIGN_COMPLETED, completed);
        mEditor.commit();
    }

    public void logout() {
        mEditor.clear();
        mEditor.commit();
    }

    public User getUserData() {
        User user = new User();

        user.setId(getUserId());
        user.setName(getUserName());
        user.setSurname1(getUserLastName());
        user.setSurname2(getUserLastName2());
        user.setGender(getUserGenre());
        user.setBirthday(getUserBirthday());
        user.setPhone(getUserPhone());
        user.setEmail(getUserEmail());
        user.setPassword(getUserPassword());
        user.setSecurityQuestion(getUserRecoveryQuestion());
        user.setSecurityAnswer(getUserRecoveryAnswer());

        return user;
    }

    public void saveAuth(String userId, String email, Auth auth) {
        mEditor.putString(TOKEN, auth.getToken());
        mEditor.putString(EXPIRES, auth.getExpires());
        mEditor.putString(USER_ID, userId);
        mEditor.putString(USER_EMAIL, email);
        mEditor.putBoolean(USER_LOGGED_IN, true);

        mEditor.commit();
    }

    public String getToken() {
        return mManager.getString(TOKEN, "");
    }

    public String setExpires() {
        return mManager.getString(EXPIRES, "");
    }

    public String getUserId() {
        return mManager.getString(USER_ID, "");
    }

    public String getUserName() {
        return mManager.getString(USER_NAME, "");
    }

    public String getUserLastName() {
        return mManager.getString(USER_LAST_NAME, "");
    }

    public String getUserLastName2() {
        return mManager.getString(USER_LAST_NAME_2, "");
    }

    public int getUserGenre() {
        return mManager.getInt(USER_GENRE, 0);
    }

    public String getUserBirthday() {
        return mManager.getString(USER_BIRTHDAY, "");
    }

    public String getUserPhone() {
        return mManager.getString(USER_PHONE, "");
    }

    public String getUserEmail() {
        return mManager.getString(USER_EMAIL, "");
    }

    public String getUserPassword() {
        return mManager.getString(USER_PASSWORD, "");
    }

    public String getUserRecoveryQuestion() {
        return mManager.getString(USER_RECOVERY_QUESTION, "");
    }

    public String getUserRecoveryAnswer() {
        return mManager.getString(USER_RECOVERY_ANSWER, "");
    }
}
