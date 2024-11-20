package com.systex.tutorial.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    private String email;

    private String name;

    private Integer gender;

}
