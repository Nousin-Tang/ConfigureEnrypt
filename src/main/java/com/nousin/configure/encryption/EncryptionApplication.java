package com.nousin.configure.encryption;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@Controller
@Slf4j
public class EncryptionApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(EncryptionApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext appCtx;

    @Autowired
    private StringEncryptor codeSheepEncryptorBean;

    @GetMapping("/{id}")
    public User getUserInfo(@PathVariable("id") Integer userId) {
        Optional<User> optional = userRepository.findById(userId);
        return optional.orElseGet(User::new);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Environment environment = appCtx.getBean(Environment.class);
        encryptRun(environment);
        decryptRun(environment);

    }

    private void encryptRun(Environment environment) {
        // 首先获取配置文件里的原始明文信息
        String mysqlOriginPswd = environment.getProperty("spring.datasource.password");

        // 加密
        String mysqlEncryptedPswd = encrypt(mysqlOriginPswd);
        // 打印加密前后的结果对比
        log.error("MySQL原始明文密码为：" + mysqlOriginPswd);
        log.error("====================================");
        log.error("MySQL原始明文密码加密后的结果为：" + mysqlEncryptedPswd); // j03z6tFoQuEwJDLRpaFFZM/DCkl+6zNWskALNVRaLFbr8YJ2ZGzLg2smgeCfa0SV
    }

    private void decryptRun(Environment environment) {
        /// 首先获取配置文件里的配置项
        String mysqlOriginPswd = environment.getProperty("spring.datasource.password");

        // 打印解密后的结果
        log.error("MySQL原始明文密码为：" + mysqlOriginPswd);
    }

    private String encrypt(String originPassword) {
        return codeSheepEncryptorBean.encrypt(originPassword);
    }

    private String decrypt(String encryptedPassword) {
        return codeSheepEncryptorBean.decrypt(encryptedPassword);
    }
}
