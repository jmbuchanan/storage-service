package com.storage.site.config


import com.storage.site.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class JwtFilterTest extends Specification {

    def jwtService = Mock(AuthService)

    def jwtFilter = new JwtFilter(jwtService)

    def request = new MockHttpServletRequest()
    def response = new MockHttpServletResponse()
    def chain = new MockFilterChain()

    def "whitelisted resource requires no authorization"() {
        given:
        request.setRequestURI("/login")

        when:
        chain.doFilter(request, response) >> null
        jwtFilter.doFilterInternal(request, response, chain)

        then:
        response.getStatus() != HttpStatus.FORBIDDEN.value()
    }
}
