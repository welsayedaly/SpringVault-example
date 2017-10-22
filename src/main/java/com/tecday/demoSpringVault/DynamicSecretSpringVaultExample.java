package com.tecday.demoSpringVault;

import com.tecday.demoSpringVault.data.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

@Slf4j
public class DynamicSecretSpringVaultExample {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DynamicVaultConfiguration.class);
        context.start();

        VaultTemplate vaultTemplate = context.getBean(VaultTemplate.class);
        VaultResponseSupport<Secrets> readPostgres = vaultTemplate.read("postgresql/creds/readonly", Secrets.class);

        DynamicVaultConfiguration dynamicVaultConfiguration = context.getBean(DynamicVaultConfiguration.class);
        dynamicVaultConfiguration.setUsername(readPostgres.getData().getUsername());
        dynamicVaultConfiguration.setPassword(readPostgres.getData().getPassword());

        CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
        Customer customer = createNewCustomer("Walid", "El Sayed Aly", "walid.elsayedaly@gmail,com");
        customerRepository.save(customer);

    }

    private static Customer createNewCustomer(String firstName, String lastName, String email) {

        Customer result = new Customer();
        result.setFirstName(firstName);
        result.setLastName(lastName);
        result.setEmail(email);
        return result;
    }

}
