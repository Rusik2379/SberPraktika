package Sber.Sber.services;

import Sber.Sber.models.User;
import Sber.Sber.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public List<User> listAllUser(){
//        return userRepository.findAll();
//    }

    public List<UserRepository.UserProjection> listUserByCompany(String companyname) {
        return userRepository.findByCompanyname(companyname);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }
}
