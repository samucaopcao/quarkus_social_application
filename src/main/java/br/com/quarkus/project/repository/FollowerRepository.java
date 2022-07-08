package br.com.quarkus.project.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import br.com.quarkus.project.model.Follower;
import br.com.quarkus.project.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

	// Método responsável por ver se usuário já esta seguindo outro
	// para que não exista duas inserções no BD com mesmo valor
	public boolean follows(User follower, User user) {
		Map<String, Object> params = new HashMap<>();
		params.put("follower", follower);
		params.put("user", user);
		PanacheQuery<Follower> query = find("follower =:follower and user =:user", params);
		Optional<Follower> result = query.firstResultOptional();
		return result.isPresent();
	}
}
