package com.extra.somthing.demo.spring.vault;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.SslConfiguration;

import java.io.File;

@Configuration
 public class VaultConfiguration extends AbstractVaultConfiguration{

    @Override
    public VaultEndpoint vaultEndpoint() {
        return new VaultEndpoint();
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public SslConfiguration sslConfiguration() {

        return SslConfiguration.forTrustStore(new FileSystemResource(new File(
                findWorkDir(new File(System.getProperty("user.dir"))), "keystore.jks")), "changeit");
    }
    public static File findWorkDir(File directory) {

        File searchLevel = directory;
        while (searchLevel.getParentFile() != null
                && searchLevel.getParentFile() != searchLevel) {

            File work = new File(searchLevel, "work");
            if (work.isDirectory() && work.exists()) {
                return work;
            }

            searchLevel = searchLevel.getParentFile();
        }

        throw new IllegalStateException(String.format(
                "Cannot find work directory in %s or any parent directories",
                directory.getAbsoluteFile()));
    }
}
