package com.renovSolution.renov;

import com.renovSolution.renov.model.*;
import com.renovSolution.renov.repo.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DataLoader {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final ClientRepo clientRepo;
    private final IndividualRepo individualRepo;
    private final MaterialRepo materialRepo;
    private final LaborRepo laborRepo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepo userRepo, AddressRepo addressRepo, ClientRepo clientRepo,
                      IndividualRepo individualRepo, MaterialRepo materialRepo,
                      LaborRepo laborRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.clientRepo = clientRepo;
        this.individualRepo = individualRepo;
        this.materialRepo = materialRepo;
        this.laborRepo = laborRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void seed() {
        if (userRepo.count() > 0) return;

        // Addresses — saved first so they have IDs; single transaction keeps them managed
        Address addr1 = addressRepo.save(new Address("7595",  "06", "Des Ormeaux", "Anjou", "QC", "Canada", "H1K 2X8"));
        Address addr2 = addressRepo.save(new Address("12000", "04", "Morfin",      "Laval", "QC", "Canada", "H1H 2X2"));

        Client client = new Client(
                "client001",
                passwordEncoder.encode("client001"),
                LocalDate.of(2021, 10, 10),
                "Client",
                "Alain",
                "Abdel",
                "abdel.alain@bar.com",
                "514 234 1231"
        );

        ProjectBid bid1 = new ProjectBid(LocalDate.of(2021, 10, 4),  LocalDate.of(2021, 10, 8),  LocalDate.of(2021, 10, 18), LocalDate.of(2021, 10, 22), "Open", "Painting");
        ProjectBid bid2 = new ProjectBid(LocalDate.of(2021, 10, 11), LocalDate.of(2021, 10, 15), LocalDate.of(2021, 10, 25), LocalDate.of(2021, 10, 29), "Open", "Framing");
        ProjectBid bid3 = new ProjectBid(LocalDate.of(2021, 10, 6),  LocalDate.of(2021, 10, 13), LocalDate.of(2021, 10, 20), LocalDate.of(2021, 10, 27), "Open", "Painting");

        // @MapsId requires a non-null embedded ID instance; values are filled by Hibernate on flush
        client.addAddress(new UserAddress(new UserAddressId(), client, addr1, "residence"));
        client.addAddress(new UserAddress(new UserAddressId(), client, addr2, "work"));
        client.addProjectBid(bid1);
        client.addProjectBid(bid2);
        client.addProjectBid(bid3);
        clientRepo.save(client);

        Material mat1 = materialRepo.save(new Material("Alpha Paint", 45));
        Material mat2 = materialRepo.save(new Material("Beta Paint",  33));
        Labor labor1  = laborRepo.save(new Labor("Painter", 3, 35));
        Labor labor2  = laborRepo.save(new Labor("Plumber", 2, 40));

        Individual contractor1 = new Individual(
                "contracteur001",
                passwordEncoder.encode("contracteur001"),
                LocalDate.of(2021, 10, 12),
                "Individual",
                0,
                5,
                "Painting",
                "514 432 3001",
                "henry.smart@bar.com",
                "Smart",
                "Henry",
                "P123450001"
        );

        ServiceOffer offer1 = new ServiceOffer(LocalDate.of(2021, 10, 4), null, "Open", 15000);
        ServiceOffer offer2 = new ServiceOffer(LocalDate.of(2021, 10, 4), null, "Open", 500);

        offer1.addMaterialToServiceOffer(new BidMaterial(new BidMaterialId(), offer1, mat1, 5, 46));
        offer1.addMaterialToServiceOffer(new BidMaterial(new BidMaterialId(), offer1, mat2, 3, 33));
        offer2.addMaterialToServiceOffer(new BidMaterial(new BidMaterialId(), offer2, mat1, 7, 77));

        offer1.addLaborToServiceOffer(new BidLabor(new BidLaborId(), offer1, labor1, 10, 33));
        offer1.addLaborToServiceOffer(new BidLabor(new BidLaborId(), offer1, labor2, 8, 42));

        contractor1.addServiceOffer(offer1);
        contractor1.addServiceOffer(offer2);
        bid1.addServiceOffer(offer1);
        bid1.addServiceOffer(offer2);

        individualRepo.save(contractor1);
    }
}
