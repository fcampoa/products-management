package com.dao;

import com.repositories.IProductsRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class DaoConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource driverManagerDataSource() {
        return new DriverManagerDataSource();
    }
    @Bean
    public Jdbi jdbi(DataSource dataSource, List<RowMapper<?>> rowMappers) {
        Jdbi jdbi = Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin());
        rowMappers.forEach(jdbi::registerRowMapper);
        return jdbi;
    }
    @Bean
    IProductsRepository productsRepository(Jdbi jdbi) {
        return jdbi.onDemand(IProductsRepository.class);
    }
}