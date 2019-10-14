package energy.viridis.exercise.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountCredentials implements Serializable {

    private String username;
    private String password;
}
