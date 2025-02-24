package com.lime.base.security.config;

import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;


@ConfigurationProperties(prefix = "lime.permit")
public class PermitRequestRegistry {

    @Setter
    public Set<String> permitRequests;

    private boolean isEmpty() {
        return this.permitRequests.isEmpty();
    }
    public PermitRequestRegistry() {
        permitRequests = new HashSet<>();
    }
    public PermitRequestRegistry(Collection<String> permitRequests) {
        this.permitRequests = new HashSet<>(permitRequests);
    }
    public PermitRequestRegistry(String... permitRequests) {
        this();
        this.permitRequests.addAll(Arrays.asList(permitRequests));
    }


    public void addPermitRequest(Collection<String> permitRequests) {
        this.permitRequests.addAll(permitRequests);
    }
    public void addPermitRequest(String... permitRequest) {
        permitRequests.addAll(Arrays.asList(permitRequest));
    }

    public String[] getPermitRequests() {
        return Optional.ofNullable(permitRequests).orElse(Collections.emptySet()).toArray(new String[0]);
    }


}
