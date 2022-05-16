package com.chj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

@ConfigurationProperties(prefix = "cat")
@Validated//数据校验
public class Cat {

    private String name;
    private Integer age;
    @Email(message = "邮箱格式错误")
    private String email;
}
