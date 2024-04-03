package com.andjela.diplomski.dto.user;

import java.util.List;

public interface UserData {
    String getUserType();
    String getEmail();
    String getPassword();
    String getFirstName();
    String getLastName();
    String getPhoneNumber();
    List<String> getAuthorities();
}
