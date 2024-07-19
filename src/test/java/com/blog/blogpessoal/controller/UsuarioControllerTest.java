package com.blog.blogpessoal.controller;

import com.blog.blogpessoal.model.Usuario;
import com.blog.blogpessoal.repository.UsuarioRepository;
import com.blog.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @BeforeAll
    void start(){
        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L,"root","root@root",
                "rootroot"," "));
    }

    @Test
    @DisplayName("Cadastrar Usuario")
    public void deveCriarUmUsuario(){
        HttpEntity<Usuario> bodyRequest = new HttpEntity<Usuario>(new Usuario(0L,"Rodrigo",
                "roh@roh", "rodrod","-"));

        ResponseEntity<Usuario> bodyResponse = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, bodyRequest, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST,bodyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Não deve duplicar Usuario")
    public void naoDeveReplicarUsuario(){
        usuarioService.cadastrarUsuario(new Usuario(0L,"Maria Da Silva",
                "MariadaSilva@email.com.br", "12345678","-"));

        HttpEntity<Usuario> bodyRequest = new HttpEntity<Usuario>(new Usuario(0L,"Maria Da Silva",
                "MariadaSilva@email.com.br", "12345678","-"));

        ResponseEntity<Usuario> bodyResponse = testRestTemplate
                .exchange("/usuarios/cadastrar",HttpMethod.POST,bodyRequest, Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST,bodyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar usuario")
    public void deveAtualizarUmUsuario(){
        Optional<Usuario>usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,"Carlos Da Silva",
                "CarlosdaSilva@email.com.br", "12345678","-"));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
                "Carlos Silveira",
                "CarlosdaSilveira@email.com.br", "12345678","-");

        HttpEntity<Usuario> bodyRequest = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> bodyResponse = testRestTemplate
                .withBasicAuth("root@root","rootroot")
                .exchange("/usuarios/atualizar",HttpMethod.PUT,bodyRequest, Usuario.class);

        assertEquals(HttpStatus.OK,bodyResponse.getStatusCode());
    }

    @Test
    @DisplayName("listar todos os usuario")
    public void deveMostrarTodosUsuarios(){
        usuarioService.cadastrarUsuario(new Usuario(0L,"Maria Da Silva",
                "MariadaSilva@email.com.br", "12345678","-"));

        usuarioService.cadastrarUsuario(new Usuario(0L,"Mario  Silva",
                "MarioSilva@email.com.br", "12345678","-"));

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("root@root","rootroot")
                .exchange("/usuarios/all",HttpMethod.GET,null, String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

}
