package tripqm.evn.java.system.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"tripqm.evn.java.system.repository"})
@Profile("dev")
public class PostgresConfigDB {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.postgres-datasource")
    public DataSource postgreDataSource() {
        return DataSourceBuilder.create().build();
    }
}
