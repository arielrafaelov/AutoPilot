package com.etech7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomAgents {

    private String userPrompt;
    private String mainPrompt;
    private String reasonPrompt;
    private String criticPrompt;
    private String decisionPrompt;

}
