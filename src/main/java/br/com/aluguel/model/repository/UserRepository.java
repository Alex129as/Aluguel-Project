package br.com.aluguel.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import br.com.aluguel.model.bean.UserBean;

public interface UserRepository  extends JpaRepository<UserBean, UUID>{
    
    @Query(
            value = "UPDATE USERS SET USER_PASSWORD = :PASSWORD WHERE 1=1 AND USER_ID = :ID",
            nativeQuery = true 
    )
    void update(@Param("ID") UUID id, @Param("PASSWORD") String password);

    @Query(
            value = "SELECT * FROM USERS WHERE 1=1 AND UPPER(USER_USERNAME) = :USERNAME", 
            nativeQuery = true
    )
    public List<UserBean> getUserByUserName(@Param("USERNAME") String username);
}
