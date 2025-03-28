package com.swing_back_end.swing.core.User.dto;

import java.time.Instant;

import com.swing_back_end.swing.enums.DocumentTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
  private String firstName;
  private String middleName;
  private String lastName;
  private String secondLastName;
  private String document;
  private DocumentTypes documentType;
  private String email;
  private long phone;
  private Instant birthdate;
}