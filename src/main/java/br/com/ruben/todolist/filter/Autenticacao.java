package br.com.ruben.todolist.filter;

import java.util.Base64;

public class Autenticacao {

    private String password;
    private String username;;


    public Autenticacao(String authorization){

        // Remove String inicial e espcao(trim)
        var authEncoded = authorization.substring("Basic".length()).trim();

        // Decodifica para byte[]
        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

        // transforma em String
        var authString = new String(authDecoded);

        // Separe username e password
        String[] credentials = authString.split(":");

        this.username = credentials[0];
        this.password = credentials[1];
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
