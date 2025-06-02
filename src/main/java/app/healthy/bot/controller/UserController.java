package app.healthy.bot.controller;

import app.healthy.bot.dto.LoginRequest;
import app.healthy.bot.dto.UserDto;
import app.healthy.bot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * Controlador REST encargado de exponer las operaciones relacionadas con los usuarios.
 * Provee endpoints para obtener, actualizar, eliminar usuarios y restablecer contraseñas.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    /**
     * Constructor del controlador con inyección del servicio de usuarios.
     *
     * @param userService Servicio que maneja la lógica de negocio relacionada a usuarios
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Obtiene los datos de un usuario por su identificador.
     *
     * @param id ID del usuario a buscar
     * @return ResponseEntity con el usuario en formato DTO y estado 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id ID del usuario a actualizar
     * @param userDto DTO con los nuevos datos del usuario
     * @return ResponseEntity con el usuario actualizado o con el código de error correspondiente:
     *         - 404 si el usuario no existe
     *         - 409 si el correo ya está registrado en otra cuenta
     *         - 500 para otros errores internos
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     * @return ResponseEntity con estado 204 No Content si se eliminó correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Restablece la contraseña de un usuario usando su correo.
     *
     * @param request DTO con el correo del usuario y la nueva contraseña
     * @return ResponseEntity con el mismo correo si se realizó con éxito,
     *         o un mensaje de error en el campo email si no se encontró el usuario
     */
    @PutMapping("/reset-password")
    public ResponseEntity<LoginRequest> resetPassword(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.resetPassword(request));
    }

}
