package com.renovSolution.renov;

import com.renovSolution.renov.model.*;
import com.renovSolution.renov.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RenovApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenovApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepo userRepo, AddressRepo addressRepo, ClientRepo clientRepo,
										IndividualRepo individualRepo, ServiceOfferRepo serviceOfferRepo) {

		return args -> {

			// Create two addresses
			Address addr1 = new Address(
					"7595",
					"06",
					"Des ormeaux",
					"Anjou",
					"QC",
					"Canada",
					"H1K 2X8"
			);
			Address addr2 = new Address(
					"12000",
					"04",
					"Morfin",
					"Laval",
					"QC",
					"Canada",
					"H1H 2X2"
			);

			// Create a client
			Client client = new Client(
					"client",
					"client",
					LocalDate.now(),
					"client",
					"Mitterand",
					"Mark",
					"littirand.mark@itsolution.com",
					"514 514 2222"
			);

			// Create project bids
			ProjectBid bid1 = new ProjectBid(
					LocalDate.now(),
					LocalDate.now().plusMonths(2),
					LocalDate.now().plusMonths(4),
					LocalDate.now().plusMonths(5),
					"Open",
					"Painting"
			);

			ProjectBid bid2 = new ProjectBid(
					LocalDate.now(),
					LocalDate.now().plusMonths(3),
					LocalDate.now().plusMonths(4),
					LocalDate.now().plusMonths(6),
					"Open",
					"Plastering"
			);

			ProjectBid bid3 = new ProjectBid(
					LocalDate.now(),
					LocalDate.now().plusMonths(3),
					LocalDate.now().plusMonths(4),
					LocalDate.now().plusMonths(6),
					"Open",
					"Framing"
			);

			// Link addresses to client
			UserAddressId addrUserId1 = new UserAddressId(client.getId(), addr1.getId());
			UserAddress residenceAddress = new UserAddress(addrUserId1, client, addr1, "residence");

			UserAddressId addrUserId2 = new UserAddressId(client.getId(), addr2.getId());
			UserAddress workAddress = new UserAddress(addrUserId2, client, addr2, "work");

			client.addAddress(residenceAddress);
			client.addAddress(workAddress);
			client.addProjectBid(bid1);
			client.addProjectBid(bid2);
			client.addProjectBid(bid3);
			clientRepo.save(client);

			// Create an individual contractor
			Individual contractor1 = new Individual(
					"Alain",
					"Alain",
					LocalDate.now(),
					"Individual",
					0,
					0,
					"Painting",
					"555-555-5555",
					"alain@bell.net",
					"Flouflou",
					"Alain",
					"Cert2021AlainPainter"
			);

			// Create service offers
			ServiceOffer offer1 = new ServiceOffer(LocalDate.now(), LocalDate.now().plusMonths(2), "created", 500);
			ServiceOffer offer2 = new ServiceOffer(LocalDate.now(), LocalDate.now().plusMonths(1), "created", 100);

			// Create materials
			Material mat1 = new Material("Alpha Paint", 45);
			Material mat2 = new Material("Beta Paint", 33);

			// Create composite keys for material join table
			BidMaterialId bidMatId1 = new BidMaterialId(offer1.getId(), mat1.getId());
			BidMaterialId bidMatId2 = new BidMaterialId(offer1.getId(), mat2.getId());
			BidMaterialId bidMatId3 = new BidMaterialId(offer2.getId(), mat1.getId());

			// Create bid-material line items
			BidMaterial bidMat1 = new BidMaterial(bidMatId1, offer1, mat1, 5, 46);
			BidMaterial bidMat2 = new BidMaterial(bidMatId2, offer1, mat2, 3, 33);
			BidMaterial bidMat3 = new BidMaterial(bidMatId3, offer2, mat1, 7, 77);

			offer1.addMaterialToServiceOffer(bidMat1);
			offer1.addMaterialToServiceOffer(bidMat2);
			offer2.addMaterialToServiceOffer(bidMat3);

			// Create labor
			Labor labor1 = new Labor("Painter", 3, 35);
			Labor labor2 = new Labor("Plumber", 2, 40);

			// Create composite keys for labor join table
			BidLaborId bidLaborId1 = new BidLaborId(offer1.getId(), labor1.getId());
			BidLaborId bidLaborId2 = new BidLaborId(offer1.getId(), labor2.getId());

			// Create bid-labor line items
			BidLabor bidLabor1 = new BidLabor(bidLaborId1, offer1, labor1, 10, 33);
			BidLabor bidLabor2 = new BidLabor(bidLaborId2, offer1, labor2, 8, 42);

			offer1.addLaborToServiceOffer(bidLabor1);
			offer1.addLaborToServiceOffer(bidLabor2);

			// Link service offers to contractor and bid
			contractor1.addServiceOffer(offer1);
			contractor1.addServiceOffer(offer2);
			bid1.addServiceOffer(offer1);
			bid1.addServiceOffer(offer2);

			individualRepo.save(contractor1);

			// Print summary
			clientRepo.findById(1L).ifPresent(c -> {
				System.out.println("Project bids for client:");
				List<ProjectBid> bids = client.getProjectBids();
				bids.forEach(System.out::println);
			});

			List<ServiceOffer> offerList = serviceOfferRepo.findServiceOffersByContractorId(contractor1.getId());
			offerList.forEach(offer ->
					System.out.println("Service offer id=" + offer.getId()
							+ " amount=" + offer.getAmount()
							+ " contractor=" + offer.getContractor().getUsername())
			);
		};
	}
}
