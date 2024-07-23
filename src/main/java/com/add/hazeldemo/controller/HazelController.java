package com.add.hazeldemo.controller;

import com.add.hazeldemo.entity.User;
import com.add.hazeldemo.repo.HazelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CacheConfig(cacheNames = "user")
public class HazelController {

        @Autowired
        private HazelRepo userRepository;

        // GET all users
        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
            List<User> users = userRepository.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        // GET user by ID
        @GetMapping("/{id}")
        @Cacheable(key = "#id")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
            System.out.println("get Data");
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        // POST new user
        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user) {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        // PUT update existing user
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                User _user = userData.get();
                _user.setUsername(user.getUsername());
                _user.setEmail(user.getEmail());
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        // DELETE user by ID
        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
            try {
                userRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
