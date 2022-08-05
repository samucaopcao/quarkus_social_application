package br.com.quarkus.project;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
		info = @Info(
				title = "API Quarkus Social API", 
				version = "1.0", 
				contact = 
				@Contact(
						name = "Samuel M. dos Santos", 
						url = "https://www.linkedin.com/in/samuel-macedo-dos-santos-77751118a/", 
						email = "samuelmacedo2002@yahoo.com.br"), 
				license = 
				@License(
						name = "Apache 2.0", 
						url = "https://www.apache.org/licenses/LICENSE-2.0.html"))

)
public class QuarkusSocialApplication extends Application {

}
