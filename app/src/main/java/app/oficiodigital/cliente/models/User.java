package app.oficiodigital.cliente.models;


import com.google.gson.annotations.SerializedName;

public class User {

    public static final int MALE = 1;
    public static final int FEMALE = 2;

    @SerializedName("client_id")
    private String id;
    private String name;
    private String surname1;
    private String surname2;
    private int gender;
    private String birthday;
    private String phone;
    private String email;
    private String password;
    @SerializedName("security_question")
    private String securityQuestion;
    @SerializedName("security_answer")
    private String securityAnswer;
    private int id_role;
    @SerializedName("active_status")
    private int active_status;
    private int pay_status;
    private int profile_status;

    public User() { }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public int getIdRole() {
        return id_role;
    }

    public void setIdRole(int id_role) {
        this.id_role = id_role;
    }

    public int getActiveStatus() {
        return active_status;
    }

    public void setActiveStatus(int active_status) {
        this.active_status = active_status;
    }

    public int getPayStatus() {
        return pay_status;
    }

    public void setPayStatus(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getProfileStatus() {
        return profile_status;
    }

    public void setProfileStatus(int profile_status) {
        this.profile_status = profile_status;
    }
}
