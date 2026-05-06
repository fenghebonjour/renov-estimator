package com.renovSolution.renov.controller;


import com.renovSolution.renov.model.*;

import com.renovSolution.renov.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/client")//(value="/client", consumes = "APPLICATION/JSON")
public class ClientController {
    private final ClientService clientService ;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients(){
        List<Client> clients =  clientService.findAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id){
        Client unClient =  clientService.findClientById(id);
        return new ResponseEntity<>(unClient, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client newClient = clientService.addClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);

    }




    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client){

        Client updatedClient = clientService.updateClient(client);
        return new ResponseEntity<>(updatedClient,HttpStatus.OK);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id){
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/addProjectBid/{id}")
    public ResponseEntity<Client> addProjectBidToClient(@RequestBody ProjectBid projectBid,
                                                        @PathVariable("id") Long id) {
        Client updatedClient = clientService.addProjectBidToClient(projectBid, id);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }


}
