package com.esprit.examen.controllers;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.ProduitDTO;
import com.esprit.examen.services.IProduitService;
import io.swagger.annotations.Api;

@RestController
@CrossOrigin("*")
@Api(tags = "Gestion des produits")
@RequestMapping("/produit")
public class ProduitRestController {

	@Autowired
	IProduitService produitService;

	@Autowired
	private ModelMapper modelMapper;

	public ProduitRestController(IProduitService produitService) {
		super();
		this.produitService = produitService;
	}

	@GetMapping("/retrieve-all-produits")
	@ResponseBody
	public List<Produit> getProduits() {
		return produitService.retrieveAllProduits();
	}

	@GetMapping("/retrieve-produit/{produit-id}")
	@ResponseBody
	public Produit retrieveRayon(@PathVariable("produit-id") Long produitId) {
		return produitService.retrieveProduit(produitId);
	}

	@PostMapping("/add-produit")
	@ResponseBody
	public ResponseEntity<ProduitDTO> addProduit(@RequestBody ProduitDTO produitDto) {
		// convert DTO to entity
		Produit produitRequest = modelMapper.map(produitDto, Produit.class);
		Produit produit = produitService.addProduit(produitRequest);
		// convert entity to DTO
		ProduitDTO produitResponse = modelMapper.map(produit, ProduitDTO.class);
		return new ResponseEntity<>(produitResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/remove-produit/{produit-id}")
	@ResponseBody
	public void removeProduit(@PathVariable("produit-id") Long produitId) {
		produitService.deleteProduit(produitId);
	}

	@PutMapping("/modify-produit")
	@ResponseBody
	public ResponseEntity<ProduitDTO> modifyproduit(@RequestBody ProduitDTO produitDto) {
		// convert DTO to entity
		Produit produitRequest = modelMapper.map(produitDto, Produit.class);
		Produit produit = produitService.addProduit(produitRequest);
		// convert entity to DTO
		ProduitDTO produitResponse = modelMapper.map(produit, ProduitDTO.class);
		return new ResponseEntity<>(produitResponse, HttpStatus.ACCEPTED);
	}
	
	@PutMapping(value = "/assignProduitToStock/{idProduit}/{idStock}")
	public void assignProduitToStock(@PathVariable("idProduit") Long idProduit, @PathVariable("idStock") Long idStock) {
		produitService.assignProduitToStock(idProduit, idStock);
	}
}
