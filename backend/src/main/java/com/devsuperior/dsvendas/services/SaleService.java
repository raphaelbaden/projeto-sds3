package com.devsuperior.dsvendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsvendas.dto.SaleDTO;
import com.devsuperior.dsvendas.dto.SaleSuccessDTO;
import com.devsuperior.dsvendas.dto.SaleSumDTO;
import com.devsuperior.dsvendas.entities.Sale;
import com.devsuperior.dsvendas.repositories.SaleRepository;
import com.devsuperior.dsvendas.repositories.SellerRepository;

@Service
public class SaleService {

	@Autowired //indica que a instância vai ser injeta automaticamente pelo framework, não precisando fazer: - new SellerRepository
	private SaleRepository repository;
	
	@Autowired 
	private SellerRepository sellerRepository;
	
	@Transactional(readOnly = true) //indica que será apenas de leitura, para não locar a tabela do banco
	public Page<SaleDTO> findAll(Pageable pageable) {
		sellerRepository.findAll(); //A JPA vai armazenar os vendedores em cache, assim quando consultar as vendas, o JPA vai pegar os vendedores da memória e não da base
		Page<Sale> result = repository.findAll(pageable);
		
		return result.map(x -> new SaleDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<SaleSumDTO> amountGroupBySeller() {
		return repository.amountGroupBySeller();
	}
	
	@Transactional(readOnly = true)
	public List<SaleSuccessDTO> successGroupBySeller() {
		return repository.successGroupBySeller();
	}
}
