package com.and1.tecday.demoSpringVault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import static org.springframework.util.Assert.isTrue;

@Slf4j
public class StaticSecretSpringVaultExample {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                VaultConfiguration.class);

        context.start();

        VaultTemplate vaultTemplate = context.getBean(VaultTemplate.class);

        Secrets secret = new Secrets();
        secret.username = "cp";
        secret.password = "swordfish";

        vaultTemplate.write("secret/myapp", secret);

        VaultResponseSupport<Secrets> response = vaultTemplate.read("secret/myapp", Secrets.class);
        log.info("Username is" + response.getData().getUsername());
        log.info("Password is" + response.getData().getPassword());

        vaultTemplate.delete("secret/myapp");

        response = vaultTemplate.read("secret/myapp", Secrets.class);

        isTrue(response == null, "after deleting response must be null");

    }


}
