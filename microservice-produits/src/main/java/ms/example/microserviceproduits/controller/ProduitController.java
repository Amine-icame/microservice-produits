package ms.example.microserviceproduits.controller;

import ms.example.microserviceproduits.model.Produit;
import ms.example.microserviceproduits.repository.ProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private static final Logger log = LoggerFactory.getLogger(ProduitController.class);

    @Autowired
    private ProduitRepository produitRepository;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // --- Nouvel endpoint pour simuler un délai ---
    @GetMapping("/delay/{seconds}")
    public ResponseEntity<String> getProduitWithDelay(@PathVariable int seconds) throws InterruptedException {
        log.info("Requête GET /produits/delay/{} reçue par l'instance sur le port : {}", seconds, serverPort);
        if (seconds > 0) {
            TimeUnit.SECONDS.sleep(seconds); // Met le thread en pause
        }
        return new ResponseEntity<>("Opération réussie après " + seconds + " secondes par le port " + serverPort, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit savedProduit = produitRepository.save(produit);
        return new ResponseEntity<>(savedProduit, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produitDetails) {
        Optional<Produit> optionalProduit = produitRepository.findById(id);
        if (optionalProduit.isPresent()) {
            Produit existingProduit = optionalProduit.get();
            existingProduit.setNom(produitDetails.getNom());
            existingProduit.setPrix(produitDetails.getPrix());
            Produit updatedProduit = produitRepository.save(existingProduit);
            return new ResponseEntity<>(updatedProduit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduit(@PathVariable Long id) {
        try {
            produitRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}