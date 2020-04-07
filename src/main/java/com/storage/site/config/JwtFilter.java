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

    private enum RoleRequired {
        NONE, USER, ADMIN;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        RoleRequired roleRequired = findRoleRequired(request);

        boolean filterRequest = false;

        switch (roleRequired) {
            case NONE:

                filterRequest = true;
                break;

            case USER:

                if (jwtService.validateUser(request)) {
                    filterRequest = true;
                }
                break;

            case ADMIN:
                if (jwtService.validateAdmin(request)) {
                    filterRequest = true;
                }
                break;
        }

        if (filterRequest) {
            chain.doFilter(request, response);
            return;

        } else {

            response.setStatus(403);

        }
    }

    private RoleRequired findRoleRequired(HttpServletRequest request) {

        List<String> noRoleRequired = new ArrayList<>();

        noRoleRequired.add("/login");
        noRoleRequired.add("/customers/addCustomer");

        List<String> adminRoleRequired = new ArrayList<>();

        adminRoleRequired.add("/customers/getAllCustomers");
        adminRoleRequired.add("/units/getAllUnits");
        adminRoleRequired.add("/transactions/getAllTransactions");
        adminRoleRequired.add("/customers/getAllCustomers/export");
        adminRoleRequired.add("/units/getAllUnits/export");
        adminRoleRequired.add("/transactions/getAllTransactions/export");

        if (noRoleRequired.contains(request.getRequestURI())) {
            return RoleRequired.NONE;
        }
        if (adminRoleRequired.contains(request.getRequestURI())) {
            return RoleRequired.ADMIN;
        }

        return RoleRequired.USER;
    }

}
