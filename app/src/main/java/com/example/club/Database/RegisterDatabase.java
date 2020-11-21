package com.example.club.Database;

import com.example.club.Objects.Club;
import com.example.club.Objects.User;

public interface RegisterDatabase {
    public User queryRegistered(String username);
    public boolean insertUser(User user);
    public boolean insertClub(Club club);
}
