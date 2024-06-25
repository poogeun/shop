package com.shop.config;

import com.shop.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
                           String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public OAuthAttributes(){
    }
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes){
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes){

        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                (String) attributes.get("picture"));
    }

    public User toEntity(){
        return new User(name, email, picture);
    }
}
