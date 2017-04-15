package app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Aplicacion entity.
 */
public class AplicacionDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AplicacionDTO aplicacionDTO = (AplicacionDTO) o;

        if ( ! Objects.equals(id, aplicacionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AplicacionDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
