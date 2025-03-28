package com.swing_back_end.swing.core.User.dto;

import java.time.Instant;

import com.swing_back_end.swing.enums.DocumentTypes;

import lombok.Getter;

@Getter
public class UserResponseDTO extends UserDTO {
  private Long id;

  public UserResponseDTO(Long id, String firstName, String middleName, String lastName, String secondLastName,
      String document, DocumentTypes documentType, String email, long phone, Instant birthdate) {
    super(firstName, middleName, lastName, secondLastName, document, documentType, email, phone, birthdate);
    this.id = id;
  }
}