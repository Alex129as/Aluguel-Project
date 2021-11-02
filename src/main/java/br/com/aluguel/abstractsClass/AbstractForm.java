package br.com.aluguel.abstractsClass;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractForm {
    
    private String      token;
    private String      name;
    private String      username;
    private String      password;
    private LocalDate   data_inicial;
    private LocalDate   data_final;
    private LocalDate   data;
    private LocalDate   data_nasc;
    private String      sobrenome;
    private String      cpf;
    private String      rg;
    private String      logradouro;
    private String      cep;
    private String      bairro;
    private String      telefone;
    private String      celuluar;
    private Integer     cidade;
    private Integer     unidadefederativa; 

}
