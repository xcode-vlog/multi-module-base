package com.lime.edu.test1.config;

import com.lime.edu.test1.repository.Test1Mapper;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan( basePackageClasses = Test1Mapper.class, sqlSessionFactoryRef = "sqlSessionFactoryTest1")
public class DataSourceTest1Config {


    @Bean("dataSourceTest1")
    @ConfigurationProperties( prefix = "spring.test1.datasource.hikari")
    public DataSource dataSourceTest1() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public org.apache.ibatis.session.Configuration configurationTest1() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }
    @Bean
    public SqlSessionFactory sqlSessionFactoryTest1(org.apache.ibatis.session.Configuration configurationTest1, DataSource dataSourceTest1) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperResources = resolver.getResources("classpath:mappers/test1/**/*.xml");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceTest1);
        sqlSessionFactoryBean.setMapperLocations(mapperResources);
        sqlSessionFactoryBean.setConfiguration(configurationTest1);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateTest1(SqlSessionFactory sqlSessionFactoryTest1) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryTest1);
        return sqlSessionTemplate;
    }
}
