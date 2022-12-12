package com.esprit.examen.entities;

import lombok.Data;

@Data
public class ProduitDTO {

	Long idProduit;
	String codeProduit;
	String libelleProduit;
	float prix;
}
