package com.extra.somthing.demo.spring.vault;


import com.extra.somthing.demo.spring.vault.data.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CustomerRepository extends CrudRepository<Customer, Long> {
}
