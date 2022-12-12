package com.esprit.examen.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.esprit.examen.entities.Produit;
import com.esprit.examen.repositories.ProduitRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProduitMockTest {

	@Mock // declaration d'un mock
	ProduitRepository pr;
	@InjectMocks
	ProduitServiceImpl psi;

	Produit p = Produit.builder().codeProduit("159").dateCreation(null).dateDerniereModification(null)
			.libelleProduit("arctic").prix(1200).build();

	@DisplayName("Retrieving products...!")
	@Test
	void GetProductTest() {

		List<Produit> lst = new ArrayList<>();
		lst.add(new Produit());

		Mockito.when(pr.findAll()).thenReturn(lst);
		List<Produit> exp = psi.retrieveAllProduits();
		assertEquals(exp, lst);
		log.info("get ps ==> " + exp.toString());

	}

	@DisplayName("Retrieve product by ID")
	@Test
	void GetbyID() {

		Mockito.when(pr.findById(Mockito.anyLong())).thenReturn(Optional.of(p));
		Produit prod = psi.retrieveProduit(3L);
		assertNotNull(prod);
		log.info("get ==> " + prod.toString());
		verify(pr).findById(Mockito.anyLong());

	}

	@DisplayName("Updating product...!")
	@Test
	void EditProductTest() {
		Produit pedit = new Produit();
		pedit.setIdProduit(3L);
		pedit.setLibelleProduit("edit");

		Produit new_pedit = new Produit();
		new_pedit.setLibelleProduit("new edit");

		Mockito.lenient().when(pr.findById(pedit.getIdProduit())).thenReturn(Optional.of(pedit));
		// assertEquals(pedit, psi.updateProduit(new_pedit) );
		pedit = psi.updateProduit(new_pedit);
		log.info("updated ==> " + pedit.toString());
		verify(pr).save(pedit);
	}

	@DisplayName("Adding products...!")
	@Test
	void AddProductTest() {

		Produit produit = new Produit();
		produit.setLibelleProduit("arctic");
		Mockito.lenient().when(pr.save(produit)).thenReturn(produit);
		Produit newp = psi.addProduit(produit);
		assertEquals(produit.getLibelleProduit(), newp.getLibelleProduit());
		verify(pr).save(produit);
		log.info("Added ==> " + produit.toString());
	}

	@DisplayName("Deleting product...!")
	@Test
	void DeleteTest() {
		Produit p = new Produit();
		p.setLibelleProduit("libelle");
		p.setIdProduit((long) 3);
		Long pid = p.getIdProduit();
		Mockito.lenient().when(pr.findById(pid)).thenReturn(Optional.of(p));

		psi.deleteProduit(pid);
		verify(pr).deleteById(pid);
		log.info("Deleted ==> " + pid.toString());
	}

}