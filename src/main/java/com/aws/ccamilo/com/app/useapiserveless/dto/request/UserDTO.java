package com.aws.ccamilo.com.app.useapiserveless.dto.request;

import com.aws.ccamilo.com.app.useapiserveless.commons.annotations.FieldMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    @FieldMapping("id")
    private String idUser;
    private String name;
    private String email;
}
