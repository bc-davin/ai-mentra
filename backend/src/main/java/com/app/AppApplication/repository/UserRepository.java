package com.app.AppApplication.repository;
import com.app.AppApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
   boolean existsByUserMail(String userMail);
   User findByUserMail(String userMail);
}
