package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@SpringBootApplication
public class WebTransformsApplication extends WebMvcConfigurerAdapter {

	@Value("${application.version}")
	private String version;

	@Value("${application.devmode}")
	private boolean devmode;

	@Bean
	public ResourceUrlEncodingFilter filter() {
		return new ResourceUrlEncodingFilter();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!this.devmode) {
			registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
					.resourceChain(true)
					.addResolver(new VersionResourceResolver()
							.addFixedVersionStrategy(this.version, "/**/*.js")
							.addContentVersionStrategy("/**"))
					.addTransformer(new AppCacheManifestTransformer());
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	public static void main(String[] args) {
		SpringApplication.run(WebTransformsApplication.class, args);
	}

}
