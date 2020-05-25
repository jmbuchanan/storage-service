package com.storage.site.config;

import com.storage.site.service.CustomerService;
import com.storage.site.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    private enum Role {
        NONE, USER, ADMIN;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        boolean customerIsAuthorizedToAccessResource = checkIfCustomerIsAuthorizedToAccessResource(request);

        if (customerIsAuthorizedToAccessResource) {
            chain.doFilter(request, response);
        } else {
            response.setStatus(403);
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
