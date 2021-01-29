package uz.dev.caveatemptor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dev.caveatemptor.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
