package br.com.aluguel.core.segurity;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import br.com.aluguel.exceptions.NoSessionAvariable;
import br.com.aluguel.exceptions.SessionExpiredException;
import br.com.aluguel.model.bean.UserBean;

public class UserLogadoUtil {
    
    private final HttpSession session;
    private final UserBean userBean;

    public UserLogadoUtil(HttpSession session, UserBean userBean){
        super();
        this.session = session;
        this.userBean = userBean;
    }

    public boolean userIsAutenticated(){

        boolean userIsAutenticated = false;

        UserBean userBeanSession = (UserBean) this.session.getAttribute(this.userBean.getChave());

        if(userBeanSession != null){

            if(new JwtTokenUtil().validateToken(userBeanSession.getToken(), userBeanSession)){
                userIsAutenticated = true;
            }
        
        };

        return userIsAutenticated;
    }

    public void isAutenticated() throws SessionExpiredException, NoSessionAvariable {

        UserBean userBeanSession = (UserBean) this.session.getAttribute(this.userBean.getChave());

        if(userBeanSession != null){

            if(new JwtTokenUtil().validateToken(userBeanSession.getToken(), userBeanSession) == false){

                throw new SessionExpiredException();

            }
        
        }else{

            throw new NoSessionAvariable();

        };

    }

    public void isAutenticated(UUID id) throws SessionExpiredException, NoSessionAvariable {

        UserBean userBeanSession = (UserBean) this.session.getAttribute(id.toString());

        if(userBeanSession != null){

            if(new JwtTokenUtil().validateToken(userBeanSession.getToken(), userBeanSession) == false){

                throw new SessionExpiredException();

            }
        
        }else{

            throw new NoSessionAvariable();

        };

    }

    public static boolean userIsAutenticated(HttpSession session, UserBean userBean){
        
        boolean userIsAutenticated = false;

        UserBean userBeanSession = (UserBean) session.getAttribute(userBean.getChave());

        if(userBeanSession != null){

            if(new JwtTokenUtil().validateToken(userBeanSession.getToken(), userBeanSession)){
               
                userIsAutenticated = true;
            
            }

        };

        return userIsAutenticated;
        
    }

    public static void isAutenticated(HttpSession session, UserBean userBean) throws SessionExpiredException, NoSessionAvariable {

        UserBean userBeanSession = (UserBean) session.getAttribute(userBean.getId().toString());

        if(userBeanSession != null){

            if(new JwtTokenUtil().validateToken(userBeanSession.getToken(), userBeanSession) == false){

                throw new SessionExpiredException();

            }
        
        }else{

            throw new NoSessionAvariable();

        };
        
    }

    public static void isAutenticated(HttpSession session, UUID id) throws SessionExpiredException, NoSessionAvariable {

        UserBean userBeanSession = (UserBean) session.getAttribute(id.toString());

        if(userBeanSession != null){

            if(new JwtTokenUtil().validateToken(userBeanSession.getToken(), userBeanSession) == false){

                throw new SessionExpiredException();

            }
        
        }else{

            throw new NoSessionAvariable();

        };
        
    }

}
