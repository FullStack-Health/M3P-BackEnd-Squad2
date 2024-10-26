package br.com.pvv.senai.repository;

import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    Usuario usuario = new Usuario();

    @BeforeEach
    void setup(){
        usuario.setId(1L);
        usuario.setEmail("usuario@teste.com");
        usuario.setPassword("12345678");
        usuario.setPerfil(Perfil.ADMIN);
    }

    @Test
    @DisplayName("Deve lançar um erro ao salvar pessoa usuária vazia no repositório")
    void saveException(){
        assertThrows(Exception.class, () -> userRepository.save(new Usuario()));
    }

    @Test
    @DisplayName("Deve salvar pessoa usuária no repositório e retornar pessoa salva")
    void save(){
        assertEquals(userRepository.save(usuario), usuario);
    }

    @Test
    @DisplayName("Deve verificar se uma pessoa usuária existe")
    void exists(){
        userRepository.saveAndFlush(usuario);
        Example<Usuario> example = Example.of(usuario);
        assertTrue(userRepository.exists(example));
    }

    //TODO findOne

    @Test
    @DisplayName("Deve retornar uma lista de todas pessoas usuárias")
    void findAll() {
        userRepository.saveAndFlush(usuario);
        assertTrue(userRepository.findAll().size() > 0);
    }

    @Test
    @DisplayName("Deve retornar uma pessoa usuária através do Id")
    void findById() {
        userRepository.saveAndFlush(usuario);
        assertEquals(userRepository.findById(1L), usuario);
    }
    
    //TODO existsById

    //TODO delete

}