package com.ensimag.ridetrack.services;


import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.repository.ClientRepository;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.AssertTrue;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing class for clientManager")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientManagerTest {

    @Mock
    SpaceManager spaceManager;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientManager clientManager;

    @Test
    @Order(0)
    @DisplayName("Create client if does not exist")
    public void testCreateClient(){

        Mockito.when(clientRepository.findByClientName(ArgumentMatchers.any())).thenReturn(Optional.empty());
        boolean testClient = clientManager.clientExists("Bond");
        Optional<Client> chercherClient = clientRepository.findByClientName("Bond");
        assertFalse(chercherClient.isPresent());

        Client newClient = Client.builder().
                clientName("Bond")
                .fullName("James Bond")
                .build();

        Assertions.assertNotEquals(testClient, newClient.getClientName());
    //    Client createClientTest = clientManager.createClient(newClient);

     //   MatcherAssert.assertThat(newClient.getClientName(),Client("Bond"));
    }

    @Test
    @Order(1)
    @DisplayName("Create client")
    public void testCreateClient_returnVoid() {
        Client newClient = Client.builder().
                clientName("Bond")
                .fullName("James Bond")
                .build();
        clientManager.createClient(newClient);
        Mockito.verify(clientRepository,Mockito.times(1)).save(newClient);
        Mockito.verify(spaceManager,Mockito.times(1)).createDefaultSpaceForClient(newClient);
    }

    /*
    @Test
    @DisplayName("Client existe")
    public boolean testClientExists(){
        Client newClient = Client.builder().
                clientName("Bond")
                .fullName("James Bond")
                .build();
        clientManager.createClient(newClient);
        boolean clientExist = clientManager.clientExists("Bond");
        assertTrue(clientManager.clientExists("Bond"));
        return clientExist;
    }*/

    @Test
    @DisplayName("DeleteClient")
    public void testDeletClient(){
        Client newClient = Client.builder().
                clientName("Bond")
                .fullName("James Bond")
                .build();
     //   Mockito.verify(clientManager,Mockito.times(1).save());
        clientManager.createClient(newClient);
        clientRepository.save(newClient);
        clientRepository.deleteClientByClientName("Bond");
        Mockito.verify(clientRepository, Mockito.times(1)).deleteClientByClientName("Bond");
        System.out.println("le client est supprimé");
        clientRepository.findByClientName("Bond");
        //  Assertions.assertEquals();
    }

}
