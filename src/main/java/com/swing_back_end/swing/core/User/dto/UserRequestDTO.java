package com.swing_back_end.swing.core.User.dto;

import java.time.Instant;

import com.swing_back_end.swing.enums.DocumentTypes;

import lombok.Getter;

@Getter
public class UserRequestDTO extends UserDTO {

  private String password;

  public UserRequestDTO(String firstName, String middleName, String lastName, String secondLastName,
      String document, DocumentTypes documentType, String email, int phone, Instant birthdate,
      String password) {
    super(firstName, middleName, lastName, secondLastName, document, documentType, email, phone, birthdate);
    this.password = password;
  }
}