package at.uibk.los.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "at.uibk.los.controller", "at.uibk.los.model.storage",  "at.uibk.los.model", "at.uibk.los", "at.uibk.los.login"})
@EnableMongoRepositories({"at.uibk.los.model.storage", "at.uibk.los.login"})
public class TestAppConfig extends WebMvcConfigurerAdapter 
{
    @Autowired
    private Environment env;
    
    private Fongo fongo = new Fongo("mongo server");
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "los");
    }

    @Bean
    public Mongo mongo() throws Exception {
        return fongo.getMongo();
    }
    
    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
 }