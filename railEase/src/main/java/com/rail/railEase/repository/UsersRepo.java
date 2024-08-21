package com.rail.railEase.repository;

import com.rail.railEase.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {

//    @Query(value = "select u from Users u where u.email =:email ")
//    Optional<Users> login(@Param("email") String email);

    @Query(value = "select u from Users u where (u.email = :email OR u.phone = :phone) and u.password =:password")
    Optional<Users> registerCheck(@Param("email") String email,@Param("phone") String phone, @Param("password") String password);

    Optional<Users> findByEmail(String email);
}
