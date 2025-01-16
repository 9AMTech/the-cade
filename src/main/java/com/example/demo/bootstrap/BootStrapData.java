package com.example.demo.bootstrap;

import com.example.demo.domain.*;
import com.example.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;
    private final InhousePartRepository inhousePartRepository;
    private final PurchaseRepository purchaseRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository, InhousePartRepository inhousePartRepository, PurchaseRepository purchaseRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository = outsourcedPartRepository;
        this.inhousePartRepository = inhousePartRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void addSampleProducts() {
        Set<Part> parts = new HashSet<>();

        Iterable<InhousePart> inhouseIterables = inhousePartRepository.findAll();
        Iterable<OutsourcedPart> outsourcedIterables = outsourcedPartRepository.findAll();

        for (InhousePart part : inhouseIterables) {
            parts.add(part);
        }

        for (OutsourcedPart part : outsourcedIterables) {
            parts.add(part);
        }

//        System.out.println("Parts! " + parts);

        Product product1 = new Product("Groove Coaster Cabinet", 1000.00, 6);
        Product product2 = new Product("Marvel vs Capcom 3 Cabinet", 6000.00, 1);
        Product product3 = new Product("Street Fighter 6 Cabinet", 1250.00, 10);
        Product product4 = new Product("Tekken 8 Cabinet", 2000.00, 7);
        Product product5 = new Product("Pacman", 5000.00, 1);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
    }


    public LocalDate getRandomDate() {
        Random random = new Random();

        LocalDate startDate = LocalDate.now().minusDays(365);
        LocalDate endDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        return startDate.plusDays(random.nextInt((int) daysBetween + 1));
    }

    public void addPurchases() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);

        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            Product randomProduct = products.get(random.nextInt(products.size()));
            Purchase purchase = new Purchase(randomProduct);

            purchase.setSaleDate(Timestamp.valueOf(getRandomDate().atStartOfDay()));
            purchaseRepository.save(purchase);
        }
    }


    public void addMoreProducts() {
        List<Product> products = Arrays.asList(
                new Product("Street Fighter 6 Cabinet", 1250.0, 10),
                new Product("Tekken 8 Cabinet Ultra Edition", 2000.0, 5),
                new Product("Pacman", 5000.0, 0),
                new Product("Mortal Kombat XL Cabinet", 4000.0, 8),
                new Product("Donkey Kong Arcade Machine", 3500.0, 6),
                new Product("Galaga Classic Machine", 2800.0, 7),
                new Product("Space Invaders Classic", 3000.0, 9),
                new Product("Time Crisis 4 Deluxe", 4500.0, 4),
                new Product("Dance Dance Revolution Extreme", 7000.0, 3),
                new Product("The House of the Dead 2", 3800.0, 6),
                new Product("Asteroids Deluxe", 3100.0, 5),
                new Product("Centipede Classic", 3200.0, 4),
                new Product("Daytona USA Racing Machine", 8000.0, 2),
                new Product("Frogger Arcade Classic", 2500.0, 7),
                new Product("Soul Calibur II Cabinet", 2700.0, 8),
                new Product("Super Smash Bros Ultimate Cabinet", 5200.0, 10),
                new Product("Virtual On: Cyber Troopers", 4900.0, 4),
                new Product("Rampage World Tour", 4300.0, 5),
                new Product("Samurai Shodown NeoGeo Cabinet", 2900.0, 3),
                new Product("Golden Axe: The Revenge of Death Adder", 4100.0, 6),
                new Product("Crazy Taxi Deluxe", 4500.0, 5),
                new Product("Outrun 2 SE Deluxe", 7500.0, 2),
                new Product("Virtua Fighter 5 Cabinet", 5100.0, 8),
                new Product("Killer Instinct Arcade Machine", 4600.0, 4),
                new Product("Punch-Out!! Arcade Edition", 3700.0, 9),
                new Product("Star Wars Trilogy Arcade", 6200.0, 3),
                new Product("X-Men vs Street Fighter", 5300.0, 6)
        );
        productRepository.saveAll(products);


    }


    public void addParts() {
        InhousePart part1 = new InhousePart();
        part1.setName("Cabinet Frame");
        part1.setPrice(150.00);
        part1.setInv(20);
        part1.setBounds(0, 25);

        InhousePart part2 = new InhousePart();
        part2.setName("Keycap");
        part2.setPrice(3.50);
        part2.setInv(100);
        part2.setBounds(0, 100);

        InhousePart part3 = new InhousePart();
        part3.setName("Joystick");
        part3.setPrice(12.00);
        part3.setInv(15);
        part3.setBounds(0, 25);

        OutsourcedPart outsourcedPart1 = new OutsourcedPart();
        outsourcedPart1.setName("Button");
        outsourcedPart1.setCompanyName("Sanwa");
        outsourcedPart1.setPrice(2.00);
        outsourcedPart1.setInv(50);
        outsourcedPart1.setBounds(0, 100);

        OutsourcedPart outsourcedPart2 = new OutsourcedPart();
        outsourcedPart2.setName("Motherboard");
        outsourcedPart2.setCompanyName("Brooks");
        outsourcedPart2.setPrice(75.00);
        outsourcedPart2.setInv(17);
        outsourcedPart2.setBounds(0, 25);

        partRepository.save(part1);
        partRepository.save(part2);
        partRepository.save(part3);
        outsourcedPartRepository.save(outsourcedPart1);
        outsourcedPartRepository.save(outsourcedPart2);
    }


    @Override
    public void run(String... args) throws Exception {
        long productCount = productRepository.count();
        long partCount = partRepository.count();
        long outsourcedPartCount = outsourcedPartRepository.count();
        long inhousePartCount = inhousePartRepository.count();
        long purchaseCount = purchaseRepository.count();

        if (productCount == 0 && partCount == 0 && outsourcedPartCount == 0 && inhousePartCount == 0) {
            addParts();
            addSampleProducts();
            addMoreProducts();
        }

        if (purchaseCount == 0) {
            addPurchases();
        }
       /*
        OutsourcedPart o= new OutsourcedPart();
        o.setCompanyName("Western Governors University");
        o.setName("out test");
        o.setInv(5);
        o.setPrice(20.0);
        o.setId(100L);
        outsourcedPartRepository.save(o);
        OutsourcedPart thePart=null;
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            if(part.getName().equals("out test"))thePart=part;
        }

        System.out.println(thePart.getCompanyName());
        */
        List<OutsourcedPart> outsourcedParts = (List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for (OutsourcedPart part : outsourcedParts) {
//            System.out.println(part.getName() + " " + part.getCompanyName());
        }

        /*
        Product bicycle= new Product("bicycle",100.0,15);
        Product unicycle= new Product("unicycle",100.0,15);
        productRepository.save(bicycle);
        productRepository.save(unicycle);
        */

//        System.out.println("Started in Bootstrap");
//        System.out.println("Number of Products" + productRepository.count());
//        System.out.println(productRepository.findAll());
//        System.out.println("Number of Parts" + partRepository.count());
//        System.out.println(partRepository.findAll());

    }
}
