package com.ensimag.ridetrack.configuration;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "file:${app.home}/conf/config.properties", ignoreResourceNotFound = true)
public class DataSourceConfiguration {

	private final Environment env;

	public DataSourceConfiguration(@Autowired Environment env) {
		this.env = env;
	}

	@Bean
	@Profile("!test")
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
			.username(env.getProperty("db.username"))
			.password(env.getProperty("db.password"))
			.url(env.getProperty("db.url"))
			.build();
	}

	@Bean
	@Profile("test")
	public DataSource getTestDataSource() {
			return DataSourceBuilder.create()
				.driverClassName("org.h2.Driver")
				.url("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
				.username("sa")
				.password("sa")
				.build();
	}

}
