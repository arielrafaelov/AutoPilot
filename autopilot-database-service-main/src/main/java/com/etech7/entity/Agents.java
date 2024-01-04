package com.etech7.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Agents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agents {

    @Id
    private String id;
    private String agentName;
    private String defaultPrompt;
}
