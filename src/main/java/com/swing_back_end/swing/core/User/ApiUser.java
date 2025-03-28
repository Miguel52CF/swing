package com.swing_back_end.swing.core.User;

import java.time.Instant;

import com.swing_back_end.swing.base.BaseModel;
import com.swing_back_end.swing.enums.DocumentTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class ApiUser extends BaseModel {

  @NotEmpty
  @NotNull
  private String firstName;

  private String middleName;

  @NotEmpty
  @NotNull
  private String lastName;

  private String secondLastName;

  @NotNull
  private String document;

  @NotNull
  private DocumentTypes documentType;

  @NotEmpty
  @NotNull
  private String email;

  @NotEmpty
  @NotNull
  private String password;

  private int phone;

  @NotEmpty
  @NotNull
  private Instant birthdate;

}
