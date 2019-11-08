package com.utn2019.avanzada2.tp9.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.utn2019.avanzada2.tp9.domain.Product;
import com.utn2019.avanzada2.tp9.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping
    /*@ApiImplicitParam(name = "Authorization", required = true, paramType = "header")*/
    public List<Product> getAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "direction", required = false) String direction,
            @RequestParam(name = "orderBy", required = false) String orderBy
    ) {
        if (page == null || size == null) {
            return productsService.getAll();
        }
        return productsService.getPaginated(page, size, direction, orderBy);
    }
}
