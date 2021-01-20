package ext.st.pmgt;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SessionAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class, SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com","ext"})
@EnableJpaRepositories(basePackages = {"com","ext"})
@ConfigurationPropertiesScan(basePackages = {"com","ext"})
@EntityScan(basePackages = {"com","ext"})
@EnableTransactionManagement
@EnableJpaAuditing
@EnableCaching
public class STPmgtApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(STPmgtApplication.class, args);
        System.out.println("Tundra后台管理系统启动成功");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(STPmgtApplication.class);
    }


}
