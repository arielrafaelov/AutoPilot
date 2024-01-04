package com.etech7.service;

import com.etech7.entity.Agents;
import com.etech7.repository.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentsService {

    private final AgentsRepository agentsRepository;

    @Autowired
    public AgentsService(AgentsRepository agentsRepository) {
        this.agentsRepository = agentsRepository;
    }

    public List<Agents> getAllAgents() {
        return agentsRepository.findAll();
    }

    public Agents addAgent(Agents agents) {
        return agentsRepository.save(agents);
    }

    public Agents getAgentByName(String agentName) {
        return agentsRepository.findByAgentName(agentName).orElse(null);
    }

    public Agents getAgentById(String id) {
        return agentsRepository.findById(id).orElse(null);
    }
}
