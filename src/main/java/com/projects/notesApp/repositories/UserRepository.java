package com.projects.notesApp.repositories;

import com.projects.notesApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM User WHERE user_name = ?1 AND password = ?2", nativeQuery = true)
    public List<User> getUser(String userName, String password);

//    @Query("FROM User u WHERE u.userName = ?1 AND u.password = ?2") // HQL
//    List<User> getUser(String userName, String password);
}
