package com.swing_back_end.swing.core.User.controllers.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swing_back_end.swing.core.User.ApiUser;
import com.swing_back_end.swing.core.User.UserService;
import com.swing_back_end.swing.core.User.dto.UserDTO;
import com.swing_back_end.swing.core.User.dto.UserRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<Object> findAll(@RequestParam(required = false) Long id) {
    List<?> users = userService.findAllById(id);

    if (users.isEmpty()) {
      Map<String, String> respuesta = new HashMap<>();
      respuesta.put("message", "users not found.");
      return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable Long id) {
    try {
      UserDTO user = userService.findById(id);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (ObjectNotFoundException e) {
      Map<String, String> respuesta = new HashMap<>();
      respuesta.put("message", e.getMessage());
      return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/new")
  public ResponseEntity<Object> save(@Valid @RequestBody ApiUser user, BindingResult result) {
    if (result.hasErrors()) {
      Map<String, String> errores = new HashMap<>();
      result.getFieldErrors().forEach(error -> {
        errores.put(error.getField(), error.getDefaultMessage());
      });
      return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
    user = userService.save(user);
    UserDTO userDTO = userService.findById(user.getId());

    return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
  }

  // @PostMapping("/new/saveAll")
  // public ResponseEntity<Object> saveAll(@RequestBody List<@Valid ApiUser>
  // users, BindingResult result) {
  // return null;
  // }

  @PutMapping("/{id}/update")
  public ResponseEntity<Object> update(
      @PathVariable Long id,
      @Valid @RequestBody UserRequestDTO user,
      BindingResult result) {

    if (result.hasErrors()) {
      Map<String, String> errores = new HashMap<>();
      result.getFieldErrors().forEach(error -> {
        errores.put(error.getField(), error.getDefaultMessage());
      });
      return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
    try {
      UserDTO empleadoDTO = userService.update(id, user);
      return new ResponseEntity<>(empleadoDTO, HttpStatus.OK);

    } catch (ObjectNotFoundException e) {
      return new ResponseEntity<>("Empleado encontrado", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    try {
      String message = userService.delete(id);
      Map<String, String> respuesta = new HashMap<>();
      respuesta.put("message", message);
      return new ResponseEntity<>(respuesta, HttpStatus.OK);
    } catch (ObjectNotFoundException e) {
      Map<String, String> respuesta = new HashMap<>();
      respuesta.put("message", e.getMessage());
      return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }
  }

}
