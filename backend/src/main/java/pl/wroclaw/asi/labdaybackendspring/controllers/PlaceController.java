package pl.wroclaw.asi.labdaybackendspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wroclaw.asi.labdaybackendspring.model.Place;
import pl.wroclaw.asi.labdaybackendspring.services.PlaceService;
import pl.wroclaw.asi.labdaybackendspring.services.ValidationErrorService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;

import static pl.wroclaw.asi.labdaybackendspring.services.LastUpdateServiceImpl.lastUpdate;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/admin/api/places")
public class PlaceController {


    private final PlaceService placeService;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public PlaceController(PlaceService placeService, ValidationErrorService validationErrorService) {
        this.placeService = placeService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping
    public ResponseEntity addPlace(@Valid @RequestBody Place place, BindingResult result){

        ResponseEntity<?> errors = validationErrorService.mapValidationService(result);

        if(errors != null)
            return errors;

        lastUpdate.setUpdatedAt(String.valueOf(new Timestamp(new Date().getTime())));
        return new ResponseEntity<>(placeService.saveOrUpdatePlace(place), HttpStatus.CREATED);

    }

    @DeleteMapping("/{placeId}")
    public ResponseEntity<?> deletePlaceById(@PathVariable("placeId") Integer placeId){
        placeService.deletePlace(placeId);

        lastUpdate.setUpdatedAt(String.valueOf(new Timestamp(new Date().getTime())));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllPlaces(){
        return new ResponseEntity<>(placeService.findAllPlaces(),HttpStatus.OK);
    }
}
