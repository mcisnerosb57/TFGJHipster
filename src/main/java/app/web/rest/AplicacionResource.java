package app.web.rest;

import com.codahale.metrics.annotation.Timed;
import app.domain.Aplicacion;

import app.repository.AplicacionRepository;
import app.web.rest.util.HeaderUtil;
import app.web.rest.util.PaginationUtil;
import app.service.dto.AplicacionDTO;
import app.service.mapper.AplicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Aplicacion.
 */
@RestController
@RequestMapping("/api")
public class AplicacionResource {

    private final Logger log = LoggerFactory.getLogger(AplicacionResource.class);
        
    @Inject
    private AplicacionRepository aplicacionRepository;

    @Inject
    private AplicacionMapper aplicacionMapper;

    /**
     * POST  /aplicacions : Create a new aplicacion.
     *
     * @param aplicacionDTO the aplicacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aplicacionDTO, or with status 400 (Bad Request) if the aplicacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aplicacions")
    @Timed
    public ResponseEntity<AplicacionDTO> createAplicacion(@Valid @RequestBody AplicacionDTO aplicacionDTO) throws URISyntaxException {
        log.debug("REST request to save Aplicacion : {}", aplicacionDTO);
        if (aplicacionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("aplicacion", "idexists", "A new aplicacion cannot already have an ID")).body(null);
        }
        Aplicacion aplicacion = aplicacionMapper.aplicacionDTOToAplicacion(aplicacionDTO);
        aplicacion = aplicacionRepository.save(aplicacion);
        AplicacionDTO result = aplicacionMapper.aplicacionToAplicacionDTO(aplicacion);
        return ResponseEntity.created(new URI("/api/aplicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aplicacion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aplicacions : Updates an existing aplicacion.
     *
     * @param aplicacionDTO the aplicacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aplicacionDTO,
     * or with status 400 (Bad Request) if the aplicacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the aplicacionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aplicacions")
    @Timed
    public ResponseEntity<AplicacionDTO> updateAplicacion(@Valid @RequestBody AplicacionDTO aplicacionDTO) throws URISyntaxException {
        log.debug("REST request to update Aplicacion : {}", aplicacionDTO);
        if (aplicacionDTO.getId() == null) {
            return createAplicacion(aplicacionDTO);
        }
        Aplicacion aplicacion = aplicacionMapper.aplicacionDTOToAplicacion(aplicacionDTO);
        aplicacion = aplicacionRepository.save(aplicacion);
        AplicacionDTO result = aplicacionMapper.aplicacionToAplicacionDTO(aplicacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aplicacion", aplicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aplicacions : get all the aplicacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of aplicacions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/aplicacions")
    @Timed
    public ResponseEntity<List<AplicacionDTO>> getAllAplicacions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Aplicacions");
        Page<Aplicacion> page = aplicacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aplicacions");
        return new ResponseEntity<>(aplicacionMapper.aplicacionsToAplicacionDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /aplicacions/:id : get the "id" aplicacion.
     *
     * @param id the id of the aplicacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aplicacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/aplicacions/{id}")
    @Timed
    public ResponseEntity<AplicacionDTO> getAplicacion(@PathVariable Long id) {
        log.debug("REST request to get Aplicacion : {}", id);
        Aplicacion aplicacion = aplicacionRepository.findOne(id);
        AplicacionDTO aplicacionDTO = aplicacionMapper.aplicacionToAplicacionDTO(aplicacion);
        return Optional.ofNullable(aplicacionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aplicacions/:id : delete the "id" aplicacion.
     *
     * @param id the id of the aplicacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aplicacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAplicacion(@PathVariable Long id) {
        log.debug("REST request to delete Aplicacion : {}", id);
        aplicacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aplicacion", id.toString())).build();
    }

}
