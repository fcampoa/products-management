package com.repositories;

import com.domain.Product;
import com.utilities.EntitiesColumns;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
public interface IProductsRepository {
    @SqlQuery("call sp_get_all_products()")
    List<Product> getProducts();
    @SqlUpdate("call sp_insert_product(:product.name, :product.price, :product.brand, :product.barCode)")
    int addProduct(@BindBean("product") Product product);
    @SqlQuery("call sp_check_duplicated_product(:barCode)")
    int checkDuplicated(@Bind("barCode") String barCode);
}
