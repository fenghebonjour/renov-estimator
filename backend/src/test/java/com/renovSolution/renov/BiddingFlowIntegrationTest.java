package com.renovSolution.renov;

import com.renovSolution.renov.model.*;
import com.renovSolution.renov.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BiddingFlowIntegrationTest extends AbstractIntegrationTest {

    @Autowired ClientService clientService;
    @Autowired IndividualService individualService;
    @Autowired ContractorService contractorService;
    @Autowired ProjectBidService projectBidService;

    private Client newClient(String username) {
        return new Client(username, "pass", LocalDate.now(), "CLIENT",
                "Doe", "John", username + "@example.com", "514-000-0000");
    }

    private ProjectBid newBid(String type) {
        return new ProjectBid(
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                LocalDate.now().plusDays(60),
                LocalDate.now().plusDays(90),
                "OPEN",
                type
        );
    }

    private Individual newIndividual(String username) {
        return new Individual(username, "pass", LocalDate.now(), "CONTRACTOR",
                4, 5, "plumbing", "514-111-2222", username + "@contractor.com",
                "Smith", "Jane", "RBQ-12345");
    }

    @Test
    void clientCanAddProjectBid() {
        Client client = clientService.addClient(newClient("bidflow_client1"));
        Client updated = clientService.addProjectBidToClient(newBid("bathroom"), client.getId());

        assertThat(updated.getProjectBids()).hasSize(1);
        assertThat(updated.getProjectBids().get(0).getType()).isEqualTo("bathroom");
        assertThat(updated.getProjectBids().get(0).getStatus()).isEqualTo("OPEN");
        assertThat(updated.getProjectBids().get(0).getId()).isNotNull();
    }

    @Test
    void contractorCanAddServiceOfferToBid() {
        Client client = clientService.addClient(newClient("bidflow_client2"));
        Client withBid = clientService.addProjectBidToClient(newBid("kitchen"), client.getId());
        Long bidId = withBid.getProjectBids().get(0).getId();

        Individual individual = individualService.addIndividual(newIndividual("bidflow_contractor2"));

        ServiceOffer offerInput = new ServiceOffer(
                LocalDate.now(), LocalDate.now().plusDays(14), "PENDING", 1500.0);
        Contractor updated = contractorService.addServiceOfferToContractor(
                offerInput, individual.getId(), bidId);

        assertThat(updated.getServiceOffers()).hasSize(1);
        ServiceOffer saved = updated.getServiceOffers().get(0);
        assertThat(saved.getAmount()).isEqualTo(1500.0);
        assertThat(saved.getStatus()).isEqualTo("PENDING");
        assertThat(saved.getProjectBid()).isNotNull();
        assertThat(saved.getProjectBid().getId()).isEqualTo(bidId);
    }

    @Test
    void findProjectBidsByClientId_isScoped() {
        Client c1 = clientService.addClient(newClient("bidflow_client3a"));
        Client c2 = clientService.addClient(newClient("bidflow_client3b"));

        clientService.addProjectBidToClient(newBid("roofing"), c1.getId());
        clientService.addProjectBidToClient(newBid("flooring"), c1.getId());
        clientService.addProjectBidToClient(newBid("painting"), c2.getId());

        List<ProjectBid> c1Bids = projectBidService.findProjectBidsByClientId(c1.getId());
        List<ProjectBid> c2Bids = projectBidService.findProjectBidsByClientId(c2.getId());

        assertThat(c1Bids).hasSize(2);
        assertThat(c1Bids).extracting(ProjectBid::getType).containsExactlyInAnyOrder("roofing", "flooring");
        assertThat(c2Bids).hasSize(1);
        assertThat(c2Bids.get(0).getType()).isEqualTo("painting");
    }

    @Test
    void serviceOfferIsLinkedToProjectBid() {
        Client client = clientService.addClient(newClient("bidflow_client4"));
        Client withBid = clientService.addProjectBidToClient(newBid("garage"), client.getId());
        Long bidId = withBid.getProjectBids().get(0).getId();

        Individual individual = individualService.addIndividual(newIndividual("bidflow_contractor4"));
        ServiceOffer offerInput = new ServiceOffer(
                LocalDate.now(), LocalDate.now().plusDays(7), "PENDING", 800.0);
        contractorService.addServiceOfferToContractor(offerInput, individual.getId(), bidId);

        ProjectBid reloaded = projectBidService.findProjectBidById(bidId);
        assertThat(reloaded.getServiceOffers()).hasSize(1);
        assertThat(reloaded.getServiceOffers().get(0).getContractor().getId())
                .isEqualTo(individual.getId());
        assertThat(reloaded.getServiceOffers().get(0).getAmount()).isEqualTo(800.0);
    }
}
