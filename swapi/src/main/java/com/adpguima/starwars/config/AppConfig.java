package com.adpguima.starwars.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig {

	@Autowired
	private Environment env;

	/**
	 * MySQL Datasource connection config
	 * 
	 * @return Datasource
	 * @throws NamingException for Error
	 */
	@Bean(destroyMethod = "")
	public DataSource dataSource() throws NamingException {

		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername(env.getProperty("mysql.user"));
		dataSource.setPassword(env.getProperty("mysql.password"));
		dataSource.setUrl(env.getProperty("mysql.url"));
		dataSource.setDriverClassName(env.getProperty("mysql.driver"));
		return dataSource;
	}

	/**
	 * DB Connection and MySQL configuration
	 * 
	 * @param dataSource to create factoryBean
	 * @return factorybean configuration
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
		factoryBean.setDataSource(dataSource);

		final Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.jdbc.batch_size", "50");
		properties.setProperty("hibernate.proc.param_null_passing", "true");
		factoryBean.setJpaProperties(properties);

		factoryBean.setPackagesToScan("com.adpguima.starwars");

		return factoryBean;
	}

	/**
	 * Manager for database transactions
	 * 
	 * @param emf EntityManagerFactory
	 * @return transaction manager
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}