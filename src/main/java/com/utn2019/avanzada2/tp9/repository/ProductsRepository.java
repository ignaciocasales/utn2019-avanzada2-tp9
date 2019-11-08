package com.utn2019.avanzada2.tp9.repository;

import com.utn2019.avanzada2.tp9.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductsRepository extends PagingAndSortingRepository<Product, Integer> {
}
