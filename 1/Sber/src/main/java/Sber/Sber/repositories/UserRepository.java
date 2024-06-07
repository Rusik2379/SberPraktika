package Sber.Sber.repositories;

import Sber.Sber.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


    List<UserProjection> findByCompanyname(String companyname);

    interface UserProjection {
        Long getId();
        String getEmail();
        String getCompanyname();
        String getFirstname();
        String getLastname();
        String getMiddlename();
    }
}