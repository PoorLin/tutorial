package com.systex.tutorial.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;

    private String email;

    private String name;

    private Date birthday;

}
