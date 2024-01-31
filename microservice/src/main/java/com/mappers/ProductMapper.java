package com.mappers;

import com.domain.Product;
import com.utilities.EntitiesColumns;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class ProductMapper implements RowMapper<Product> {
    public Product map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Product(rs.getInt(EntitiesColumns.Product.ID),
                rs.getString(EntitiesColumns.Product.NAME),
                rs.getFloat(EntitiesColumns.Product.PRICE),
                rs.getString(EntitiesColumns.Product.BRAND),
                rs.getString(EntitiesColumns.Product.BARCODE));
    }
}