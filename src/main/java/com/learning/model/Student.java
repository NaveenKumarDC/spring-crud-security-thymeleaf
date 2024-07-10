package com.learning.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Student {

  private String firstName;
  private String lastName;
  private String country;
  private String favoriteLanguage;

}
