package com.aws.ccamilo.com.app.useapiserveless.dto.response;

import com.aws.ccamilo.com.app.useapiserveless.commons.annotations.FieldMapping;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTOResponse {
    @FieldMapping("id")
    private String idUser;
    private String name;
    private String email;
}
