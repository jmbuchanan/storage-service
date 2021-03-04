package com.storage.site.config;

import com.storage.site.service.CustomerService;
import com.storage.site.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private CustomerService customerService;
    private JwtService jwtService;

    private enum Role {
        NONE, USER, ADMIN;
    }

    public JwtFilter(CustomerService customerService, JwtService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        log.info("");
        log.info(String.format("<<< REQUEST START: %s", uuid));
        log.info(request.getMethod() + " " + request.getRequestURI());
        request.setAttribute("uuid", uuid);
        boolean customerIsAuthorizedToAccessResource = checkIfCustomerIsAuthorizedToAccessResource(request);

        if (customerIsAuthorizedToAccessResource) {
            chain.doFilter(request, response);
        } else {
            log.info("Not authorized");
            log.info(String.format("REQUEST END: %s >>>", uuid));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean checkIfCustomerIsAuthorizedToAccessResource(HttpServletRequest request) {
        Role roleRequired = findRoleRequiredToAccessResource(request);
        boolean isAuthorized = checkIfCustomerHasRoleRequired(request, roleRequired);
        return isAuthorized;
    }

    private Role findRoleRequiredToAccessResource(HttpServletRequest request) {

        String uri = request.getRequestURI();

        List<String> resourcesWithNoRestrictions = populateResourcesWithNoRestrictions();
        if (resourcesWithNoRestrictions.contains(uri)) {
            return Role.NONE;
        }

        List<String> resourcesRestrictedToAdmins = populateResourcesRestrictedToAdmins();
        if (resourcesRestrictedToAdmins.contains(uri)) {
            return Role.ADMIN;
        }

        return Role.USER;
    }

    private List<String> populateResourcesWithNoRestrictions() {

        List<String> whiteListedResources = new ArrayList<>();

        whiteListedResources.add("/customers/addCustomer");
        whiteListedResources.add("/login");
        whiteListedResources.add("/transactions/stripe");

        return whiteListedResources;
    }

    private List<String> populateResourcesRestrictedToAdmins() {

        List<String> adminRoleRequired = new ArrayList<>();

        adminRoleRequired.add("/customers/getAllCustomers");
        adminRoleRequired.add("/customers/getAllCustomers/export");
        adminRoleRequired.add("/units/getAllUnits");
        adminRoleRequired.add("/units/getAllUnits/export");
        adminRoleRequired.add("/transactions/getAllTransactions");
        adminRoleRequired.add("/transactions/getAllTransactions/export");

        return adminRoleRequired;
    }



    private boolean checkIfCustomerHasRoleRequired(HttpServletRequest request, Role roleRequired) {

        boolean hasRoleRequired = false;

        switch (roleRequired) {
            case NONE:
                hasRoleRequired = true;
                break;

            case USER:
                if (jwtService.validateUser(request)) {
                    hasRoleRequired = true;
                }
                break;

            case ADMIN:
                if (jwtService.validateAdmin(request)) {
                    hasRoleRequired = true;
                }
                break;
        }

        return hasRoleRequired;
    }
}
