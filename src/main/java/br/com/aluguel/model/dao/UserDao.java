package br.com.aluguel.model.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.aluguel.constants.SortUser;
import br.com.aluguel.core.segurity.JwtTokenUtil;
import br.com.aluguel.exceptions.NoDataFoundException;
import br.com.aluguel.model.bean.UserBean;
import br.com.aluguel.model.repository.UserRepository;

public class UserDao {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserDao(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserBean save(UserBean userBean) throws DataIntegrityViolationException{
        
        try {
            
            this.userRepository.save(userBean);
        
        }catch(DataIntegrityViolationException e){

            throw new DataIntegrityViolationException("Duplicate for Unique Collums.");

        }

        return userBean;
    }
    
    public void saveAll(List<UserBean> userBeanList){
        this.userRepository.saveAll(userBeanList);
    }
 
    public UserBean findOne(UUID id) throws NoDataFoundException{

        Optional<UserBean> userBean = this.userRepository.findById(id);

        if(userBean.isEmpty()){
            throw new NoDataFoundException();
        }
        
        return userBean.get();

    }
   
    public List<UserBean> findAll() throws NoDataFoundException{

        List<UserBean> userBeanList = this.userRepository.findAll();

        if(userBeanList.isEmpty()){
            throw new NoDataFoundException();
        }

        return userBeanList;

    }

    public List<UserBean> findAll(SortUser sortUser) throws NoDataFoundException{

        sortUser = (sortUser == null) ? SortUser.DESCNOME : sortUser;

        List<UserBean> userBeanList = this.userRepository.findAll();

        if(userBeanList.isEmpty()){
            throw new NoDataFoundException();
        }

        if(sortUser.equals(SortUser.DESCID) || sortUser.equals(SortUser.ASCID)){

            return sortListId(userBeanList, sortUser);
        
        }else if (sortUser.equals(SortUser.DESCNOME) || sortUser.equals(SortUser.ASCNOME)){

            return sortListUserName(userBeanList, sortUser);

        }else if (sortUser.equals(SortUser.DESCNOME) || sortUser.equals(SortUser.ASCDATACADASTRO)){

            return sortListDataCadastro(userBeanList, sortUser);

        }

        return userBeanList;

    }

    public List<UserBean> sortListId(List<UserBean> userBeanList, SortUser sortUser){

        sortUser = (sortUser == null) ? SortUser.DESCID : sortUser;

        userBeanList = userBeanList.parallelStream()
                                   .sorted((user1, user2) -> user1.getId().compareTo(user2.getId()))
                                   .collect(Collectors.toList());
        
        if (sortUser.equals(SortUser.ASCID)){
            Collections.reverse(userBeanList);
        }

        return userBeanList;
    }

    public List<UserBean> sortListUserName(List<UserBean> userBeanList, SortUser sortUser){

        sortUser = (sortUser == null) ? SortUser.DESCNOME : sortUser;

        userBeanList = userBeanList.parallelStream()
                                   .sorted((user1, user2) -> user1.getUsername().compareTo(user2.getUsername()))
                                   .collect(Collectors.toList());
        
        if(sortUser.equals(SortUser.ASCNOME)){
            Collections.reverse(userBeanList);
        }

        return userBeanList;

    }
    
    public List<UserBean> sortListDataCadastro(List<UserBean> userBeanList, SortUser sortUser){

        sortUser = (sortUser == null) ? SortUser.DESCNOME : sortUser;

         userBeanList = userBeanList.parallelStream()
                                    .sorted((user1, user2) -> user1.getDataCadastro().compareTo(user2.getDataCadastro()))
                                    .collect(Collectors.toList());

        if (sortUser.equals(SortUser.ASCDATACADASTRO)){
            Collections.reverse(userBeanList);
        }
        
        return userBeanList;

    }

    public UserBean getById(UUID id) throws NoDataFoundException{

        UserBean userBean = null;
        userBean = this.userRepository.getById(id);

        if(userBean == null){
            throw new NoDataFoundException("Nenhum Usuário Encontrado com Id: "+id+"!");
        }

        return userBean;

    }

    public void update(UserBean userBean) throws NoDataFoundException{
        this.userRepository.update(userBean.getId(), passwordEncoder.encode(userBean.getPassword()));
    }

    public void delete(UserBean userBean){
        this.userRepository.delete(userBean);
    }

    public void deleteAll(){
        this.userRepository.deleteAll();
    }

    public UserBean validaLogin(UserBean userBean) throws NoDataFoundException{

        UserBean userHasEncontred = null;

        userHasEncontred = this.userRepository.getUserByUserName(userBean.getUsername().toUpperCase())
                                .stream()
                                .findFirst()
                                .orElseThrow(() -> new NoDataFoundException());

        if(userHasEncontred != null && passwordEncoder.matches(userBean.getPassword(), userHasEncontred.getPassword())){
            userHasEncontred.setToken(new JwtTokenUtil().generateToken(userHasEncontred));    
            return userHasEncontred;
        }

        return userHasEncontred;
    }; 
}
