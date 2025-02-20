package com.lime.edu.limeedu.config;

import com.lime.edu.limeedu.repository.LimeEduMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackageClasses = LimeEduMapper.class, sqlSessionFactoryRef = "limeSqlSessionFactory")
public class LimeEduDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.lime-edu.datasource.hikari")
    public DataSource limeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public org.apache.ibatis.session.Configuration limeSqlSessionConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }
    @Bean
    public SqlSessionFactory limeSqlSessionFactory(DataSource limeDataSource, org.apache.ibatis.session.Configuration limeSqlSessionConfiguration) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperResources = resolver.getResources("classpath:mappers/lime-edu/**/*.xml");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(limeDataSource);
        sqlSessionFactoryBean.setConfiguration(limeSqlSessionConfiguration);
        sqlSessionFactoryBean.setMapperLocations(mapperResources);

        return sqlSessionFactoryBean.getObject();
    }
    @Bean
    public SqlSessionTemplate limeSqlSessionTemplate(SqlSessionFactory limeSqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(limeSqlSessionFactory);
        return sqlSessionTemplate;
    }
}
