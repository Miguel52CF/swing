package com.swing_back_end.swing.core.User.mapper;

import org.springframework.stereotype.Component;

import com.swing_back_end.swing.core.User.ApiUser;
import com.swing_back_end.swing.core.User.dto.UserResponseDTO;

@Component
public class UserMapper {

  public UserResponseDTO response(ApiUser user) {
    return new UserResponseDTO(
        user.getId(),
        user.getFirstName(),
        user.getMiddleName(),
        user.getLastName(),
        user.getSecondLastName(),
        user.getDocument(),
        user.getDocumentType(),
        user.getEmail(),
        user.getPhone(),
        user.getBirthdate());
  }

}
