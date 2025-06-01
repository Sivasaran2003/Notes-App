package com.projects.notesApp.repositories;

import com.projects.notesApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT 1 FROM User WHERE email = ?1", nativeQuery = true)
    public User findByMail(String email);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM User WHERE email = ?1)")
    public boolean existsByMail(String mail);

//    @Query("FROM User u WHERE u.userName = ?1 AND u.password = ?2") // HQL
//    List<User> getUser(String userName, String password);
}
