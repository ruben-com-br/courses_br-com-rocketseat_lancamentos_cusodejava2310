package br.com.ruben.todolist.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptUtils {

    public static boolean verificaSenha(String senhaSite, String senhaBanco){

        var result = BCrypt.verifyer().verify(senhaSite.toCharArray(), senhaBanco);

        return result.verified;
    }

}
