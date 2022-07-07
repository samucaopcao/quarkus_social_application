package br.com.quarkus.project.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.quarkus.project.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
