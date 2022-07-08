package br.com.quarkus.project.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.quarkus.project.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post>{

}
