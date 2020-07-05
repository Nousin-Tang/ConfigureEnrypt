package com.nousin.configure.encryption;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author Nousin
 * @since 2020/7/5
 */
@Configuration
public class CodeSheepEncryptorConfig {

    @Value("${jasypt.encryptor.password}")
    private String password;

    /**
     * 加密密钥不要写在配置文件中
     *  方式一：直接作为程序启动时的命令行参数来带入
     *      java -jar yourproject.jar --jasypt.encryptor.password=CodeSheep
     *  方式二：直接作为程序启动时的应用环境变量来带入
     *      java -Djasypt.encryptor.password=CodeSheep -jar yourproject.jar
     * @return
     */
    @Bean( name = "codeSheepEncryptorBean" )
    public StringEncryptor codeSheepStringEncryptor() {

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword("CodeSheep");
        config.setPassword(password);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;
    }
}
