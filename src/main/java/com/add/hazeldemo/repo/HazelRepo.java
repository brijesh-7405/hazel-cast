package com.add.hazeldemo.repo;

import com.add.hazeldemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HazelRepo extends JpaRepository<User,Long> {
}
