package br.com.ruben.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.ruben.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

import static br.com.ruben.todolist.utils.BCryptUtils.verificaSenha;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var serveletPah = request.getServletPath();

        if (serveletPah.startsWith("/tasks/")) {

            Autenticacao autenticacao = new Autenticacao(request.getHeader("Authorization"));

            // validar usuário
            var user = this.userRepository.findByUsername(autenticacao.getUsername());

            // usuario não existe
            if (user == null) {
                response.sendError(401);
            }

            // senha incorreta
            if (!verificaSenha(autenticacao.getPassword(), user.getPassword())) {
                response.sendError(401);
            }

            // Usuario autenticado
            request.setAttribute("idUser",user.getId());

        }

        filterChain.doFilter(request, response);
    }
}
