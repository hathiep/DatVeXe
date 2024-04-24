package com.rs.datvexe.service;

import com.rs.datvexe.model.Client;
import com.rs.datvexe.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    public void save (Client client){
        clientRepository.save(client);
    }
    public Client getById(Integer integer){
        return clientRepository.getById(integer);
    }
    public Client getByEmail(String email){
        return clientRepository.getByEmail(email);
    }
}
