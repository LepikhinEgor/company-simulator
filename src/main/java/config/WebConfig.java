package config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.template.TemplateException;

@Configuration
@EnableWebMvc
public class WebConfig {
	@Bean
	public ViewResolver getViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		
		viewResolver.setOrder(1);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".ftl");
		viewResolver.setContentType("text/html; charset=windows-1251");
		
		return viewResolver;
	}
	@Bean
	public FreeMarkerConfigurer getFreeMarkerConfigurer() throws IOException, TemplateException {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/pages");
		
		return configurer;
	}
	
	@Bean
	WebMvcConfigurer webConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
			}
		};
	}
	
}
