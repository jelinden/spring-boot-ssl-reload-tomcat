package com.example.springboot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SSLRenewSertificateStoreService {

    private TomcatUtil tomcatUtil;

    public SSLRenewSertificateStoreService(TomcatUtil tomcatUtil) {
        this.tomcatUtil = tomcatUtil;
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void reloadSertificateStore() {
        tomcatUtil.reloadSSLHostConfig();
    }
}
