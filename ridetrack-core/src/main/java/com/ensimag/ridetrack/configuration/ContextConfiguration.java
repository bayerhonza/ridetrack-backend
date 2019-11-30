package com.ensimag.ridetrack.configuration;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("file:${app.home}/conf/config.properties")
public class ContextConfiguration {

	private final Environment env;

	public ContextConfiguration(@Autowired Environment env) {
		this.env = env;
	}

	@Bean
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
			.username(env.getProperty("db.username"))
			.password(env.getProperty("db.password"))
			.url(env.getProperty("db.url"))
			.build();
	}

}
