package com.aws.ccamilo.com.app.useapiserveless.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTOResponse {
    private Long idUser;
    private String name;
    private String email;
}
