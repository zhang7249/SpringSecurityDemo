package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_test")
public class User implements java.io.Serializable{
 @Id
 @Column
 private Long id;
 @Column
 private String login;
 @Column
 private String password;
 @Column
 private String role;


}
