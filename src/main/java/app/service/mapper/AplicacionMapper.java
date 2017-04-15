package app.service.mapper;

import app.domain.*;
import app.service.dto.AplicacionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Aplicacion and its DTO AplicacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AplicacionMapper {

    AplicacionDTO aplicacionToAplicacionDTO(Aplicacion aplicacion);

    List<AplicacionDTO> aplicacionsToAplicacionDTOs(List<Aplicacion> aplicacions);

    Aplicacion aplicacionDTOToAplicacion(AplicacionDTO aplicacionDTO);

    List<Aplicacion> aplicacionDTOsToAplicacions(List<AplicacionDTO> aplicacionDTOs);
}
