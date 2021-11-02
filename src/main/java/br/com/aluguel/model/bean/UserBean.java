package br.com.aluguel.model.bean;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class UserBean {
    
    @Id
    @Column(name =  "user_id", unique = true, nullable = false, columnDefinition = "VARCHAR2(36)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID        id;

    @Column(name = "user_username", unique = true, nullable = false, columnDefinition = "VARCHAR2(30)")
    private String      username;

    @Column(name = "user_password", nullable = false, columnDefinition = "VARCHAR2(255)")
    private String      password;

    @Column(name = "user_dataCadastro", nullable = false, columnDefinition = "DATE")
    private LocalDate   dataCadastro;

    private String token;
    
    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }

    public String getChave(){
        return this.id+"-"+this.username; 
    }
}
