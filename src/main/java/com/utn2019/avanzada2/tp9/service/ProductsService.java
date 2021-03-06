package com.utn2019.avanzada2.tp9.service;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn2019.avanzada2.tp9.domain.Product;
import com.utn2019.avanzada2.tp9.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws Exception {
        log.info("loading products...");
        productsRepository.deleteAll();
        // @formatter:off
        final List<Product> products = objectMapper.readValue(this.getClass().getResourceAsStream("/product-data.json"), new TypeReference<>() {});
        // @formatter:on
        productsRepository.saveAll(products);
    }

    public List<Product> getAll(Integer page, Integer size, String direction, String orderBy) {
        return ofNullable(page)
                .map(p -> getPaginated(p, size, direction, orderBy))
                .orElseGet(this::getAll);
    }

    public Long total() {
        return productsRepository.count();
    }

    private List<Product> getPaginated(Integer page, Integer size, String direction, String orderBy) {
        final PageRequest request = PageRequest.of(
                page,
                ofNullable(size).orElse(10),
                fromOptionalString(direction).orElse(ASC),
                ofNullable(orderBy).orElse("productId")
        );
        return iterableToList(productsRepository.findAll(request));
    }

    private List<Product> getAll() {
        return iterableToList(productsRepository.findAll());
    }

    private static List<Product> iterableToList(Iterable<Product> products) {
        return stream(products.spliterator(), false).collect(toList());
    }
}
