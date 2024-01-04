package com.etech7.controller;

import com.etech7.entity.Agents;
import com.etech7.service.AgentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{mspCustomDomain}/agents")
public class AgentsController {

    private final AgentsService agentsService;

    @Autowired
    public AgentsController(AgentsService agentsService) {
        this.agentsService = agentsService;
    }

    @GetMapping("/getAgents")
    public List<Agents> getAgents(@PathVariable String mspCustomDomain) {
        return agentsService.getAllAgents();
    }

    @PostMapping("/addAgent")
    public Agents addAgent(@PathVariable String mspCustomDomain, @RequestBody Agents agents) {
        return agentsService.addAgent(agents);
    }

    @GetMapping("/getAgentByName")
    public Agents getAgentByName(@PathVariable String mspCustomDomain, @RequestParam(required = true) String agentName) {
        return agentsService.getAgentByName(agentName);
    }

    @GetMapping("/getAgentById")
    public Agents getAgentById(@PathVariable String mspCustomDomain, @RequestParam(required = true) String id) {
        return agentsService.getAgentById(id);
    }
}
