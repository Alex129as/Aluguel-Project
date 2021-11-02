package br.com.aluguel.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.aluguel.constants.SortUser;
import br.com.aluguel.core.segurity.UserLogadoUtil;
import br.com.aluguel.exceptions.NoDataFoundException;
import br.com.aluguel.model.bean.UserBean;
import br.com.aluguel.model.dao.UserDao;
import br.com.aluguel.model.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public UserBean createUser(@RequestBody UserBean userBean){
    
        PasswordEncoder passEncode = new BCryptPasswordEncoder();       
        String passHashed = passEncode.encode(userBean.getPassword());
        
        UserDao userDao = new UserDao(this.userRepository);
        userBean.setPassword(passHashed);
        userBean.setDataCadastro(LocalDate.now());
        UserBean userCreated = userDao.save(userBean);
        
        return userCreated;

    }

    @GetMapping("/get/all/users")
    public List<UserBean> getUsers(@RequestBody UserBean userBean, HttpSession session) throws Exception{

        UserLogadoUtil.isAutenticated(session, userBean);

        List<UserBean> userBeanList = new ArrayList<UserBean>();

        UserDao userDao = new UserDao(this.userRepository);

        try {
            
            userBeanList = userDao.findAll(SortUser.DESCNOME);
        
        }catch(Exception e){

            if (e instanceof NoDataFoundException){
                return new ArrayList<UserBean>();
            }

            e.printStackTrace();
        }

        return userBeanList;

    }
    
    @GetMapping("/get/{id}/user")
    public UserBean getUser(@PathVariable("id") UUID id){

        UserBean userBean = new UserBean();

        UserDao userDao = new UserDao(this.userRepository);
        
        try {
            
             userBean = userDao.getById(id);
        
        }catch(Exception e){

            if (e instanceof NoDataFoundException){
                return new UserBean();
            }

            e.printStackTrace();
        }

        return userBean;

    }

    @PostMapping("/auth/user")
    @ResponseBody
    public boolean logar(@RequestBody UserBean userBean, HttpSession session) throws Exception{

        boolean isAutenticaded = false;

        try {
            
            UserBean userBeanAuth = new UserDao(this.userRepository).validaLogin(userBean);
            session.setAttribute(userBeanAuth.getId().toString(), userBeanAuth);

            isAutenticaded = true;

        } catch(NoDataFoundException e){

            isAutenticaded = false;

        }
        
        return isAutenticaded;
    }

}
