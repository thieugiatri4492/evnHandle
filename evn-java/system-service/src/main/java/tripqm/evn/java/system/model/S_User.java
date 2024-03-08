package tripqm.evn.java.system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class S_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("USER_NAME")
    @Column(name="USER_NAME")
    public String user_name;
    @JsonProperty("PASSWORD")
    @Column(name="PASSWORD")
    public String password;
    @JsonProperty("FULL_NAME")
    @Column(name="FULL_NAME")
    public String full_name;
    @JsonProperty("EMAIL")
    @Column(name="EMAIL")
    public String email;

    public S_User() {
    }

    public S_User(String user_name, String password, String full_name, String email) {
        this.user_name = user_name;
        this.password = password;
        this.full_name = full_name;
        this.email = email;
    }

    public String getuser_name() {
        return user_name;
    }

    public void setuser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getfull_name() {
        return full_name;
    }

    public void setfull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}
