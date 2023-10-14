package br.com.ruben.todolist.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


public class DateUtils {

    public static boolean dataTemQuerSerMaiorQueAtual(LocalDateTime dataPassada){
        var dataAtual = LocalDateTime.now();

        return dataAtual.isAfter(dataPassada);
    }

    public static ResponseEntity validarDatas(LocalDateTime dataInicio, LocalDateTime dataFinal){

        if (dataTemQuerSerMaiorQueAtual(dataInicio) || dataTemQuerSerMaiorQueAtual(dataFinal)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio/termino deve ser maior que a Atual");
        }

        if (dataInicio.isAfter(dataFinal)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser menor que a Final");
        }

        return null;
    }
}
