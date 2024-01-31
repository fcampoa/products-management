package com.repositories;

import com.domain.Product;
import com.mappers.ProductMapper;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RegisterBeanMapper(Product.class)
public class ProductsRepository implements IProductsRepository{
    private final RowMapper<Product> productMapper;
    private final Jdbi jdbi;
    @Autowired
    public ProductsRepository(Jdbi jdbi, ProductMapper productMapper) {
        this.jdbi = jdbi;
        this.productMapper = productMapper;
    }
    @Override
    public List<Product> getProducts() {
        return jdbi.withHandle(
                handle ->
                        handle.createQuery("call sp_get_all_products()")
                                .map(productMapper)
                                .list()
        );
    }
    @Override
    public int addProduct(Product product) {
        return jdbi.withHandle(
                handle ->
                        handle.createUpdate("call sp_insert_product(:name, :price, :brand, :barCode)")
                                .bind("name", product.getName())
                                .bind("price", product.getPrice())
                                .bind("brand", product.getBrand())
                                .bind("barCode", product.getBarCode())
                                .execute()
        );
    }

    @Override
    public int checkDuplicated(String barCode) {
        return jdbi.withHandle(handle ->
                handle.createQuery("call sp_check_duplicated_product(:barCode)")
                        .bind("barCode", barCode)
                        .mapTo(Integer.class)
                        .first()
        );
    }
}
