package com.storage.site.controller;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {
/*
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(HttpServletRequest request) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDetails user = customerService.loadUserByUsername(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String storedPassword = user.getPassword();

        if (passwordEncoder.matches(password, storedPassword)) {
            String token = jwtService.generateToken(user);
            System.out.println("Login controller token generated: " + token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "Authorization=" + token);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    // doesn't do anything right now
    @GetMapping("/getUserDetails")
    public ResponseEntity<String> getUserRole(HttpServletRequest request) {

        String authorization = null;

        Cookie[] cookies = request.getCookies();

        if (cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName() == "Authorization") {
                    authorization = c.getValue();
                }
            }
        } return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

 */
}
