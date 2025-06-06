package com.pms.sdp.controller;

import com.pms.sdp.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/properties")
@RequiredArgsConstructor
public class PublicPropertyController {
    
    private final PropertyService propertyService;
    
    /**
     * Get all available properties (public access)
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllAvailableProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minBedrooms,
            @RequestParam(required = false) Integer minBathrooms
    ) {
        List<Map<String, Object>> properties = propertyService.getAvailableProperties(
                location, propertyType, minPrice, maxPrice, minBedrooms, minBathrooms);
        return ResponseEntity.ok(properties);
    }
    
    /**
     * Get property details by id (public access)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPropertyDetails(@PathVariable Long id) {
        try {
            Map<String, Object> property = propertyService.getPropertyDetailsById(id);
            return ResponseEntity.ok(property);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Property not found");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
}