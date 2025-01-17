package com.grupo1.demo.Services;
import com.grupo1.demo.Models.Sistema;
import com.grupo1.demo.Repositories.SistemaRepository;
import com.grupo1.demo.Repositories.UserRepository;
import com.grupo1.demo.dto.UsuarioDTO;

import jakarta.annotation.PostConstruct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataLoaderService {
    
    @Autowired
    SistemaRepository sistemaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Metodo que permite cargar datos iniciales en la base de datos, pero solo si la tabla está vacia
     * (es decir, la tabla no tiene sistemas previos).
     */
    @PostConstruct
    public void loadData() {

        // Verificamos si la base de datos ya tiene registros en la tabla "Sistema"
        if (sistemaRepository.count() == 0) {

             // Si no hay registros, creamos nuevas instancias de la entidad "Sistema"
            Sistema sistema1 = new Sistema("cuentas");
            Sistema sistema2 = new Sistema("yimeil");
            Sistema sistema3 = new Sistema("draiv");
            Sistema sistema4 = new Sistema("k-lendar");
            sistemaRepository.save(sistema1);
            sistemaRepository.save(sistema2);
            sistemaRepository.save(sistema3);
            sistemaRepository.save(sistema4);
        }

        if (userRepository.count() == 0) {
            UsuarioDTO adminDTO = new UsuarioDTO();
            adminDTO.setUsername("admin@gugle.com");
            adminDTO.setPassword("Admin1234!"); 
            adminDTO.setFirstName("Admin");
            adminDTO.setLastName("User");
            adminDTO.setSistemaIds(List.of(1L, 2L, 3L, 4L)); // IDs de los sistemas creados anteriormente
    
            // Crear y guardar el usuario usando el servicio
            userService.addUser(adminDTO);

            // Usuario John Doe
            UsuarioDTO johnDTO = new UsuarioDTO();
            johnDTO.setUsername("johndoe@gugle.com");
            johnDTO.setPassword("Johndoe123!");
            johnDTO.setFirstName("John");
            johnDTO.setLastName("Doe");
            johnDTO.setSistemaIds(List.of(2L , 3L , 4L));

            userService.addUser(johnDTO);

            //Usuario Alice Smith
            UsuarioDTO aliceDTO = new UsuarioDTO();
            aliceDTO.setUsername("alicesmith@gugle.com");
            aliceDTO.setPassword("AliceSmith2024!");
            aliceDTO.setFirstName("Alice");
            aliceDTO.setLastName("Smith");
            aliceDTO.setSistemaIds(List.of(1L, 3L, 4L));

            userService.addUser(aliceDTO);

            //Usuario Bob Johnson
            UsuarioDTO bobDTO = new UsuarioDTO();
            bobDTO.setUsername("bobjohnson@gugle.com");
            bobDTO.setPassword("BobJohnson2023!");
            bobDTO.setFirstName("Bob");
            bobDTO.setLastName("Johnson");
            bobDTO.setSistemaIds(List.of(1L));

            userService.addUser(bobDTO);

            //Usuario Carol White
            UsuarioDTO carolDTO = new UsuarioDTO();
            carolDTO.setUsername("carolwhite@gugle.com");
            carolDTO.setPassword("CarolWhite2022!");
            carolDTO.setFirstName("Carol");
            carolDTO.setLastName("White");
            carolDTO.setSistemaIds(List.of(2L , 4L));

            userService.addUser(carolDTO);
        }
    }
}

