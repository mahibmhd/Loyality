package com.loyalty.dxvalley.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loyalty.dxvalley.models.CreateResponse;
import com.loyalty.dxvalley.models.Levelss;
import com.loyalty.dxvalley.services.LevelssService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("api/levels")
@RequiredArgsConstructor

public class LevelssController {
   
    @Autowired
    private final LevelssService levelssService;
 
    @GetMapping("/getLevels")
    List<Levelss> getLevels(){
        return this.levelssService.getLevels();  
    }

    @GetMapping("/{levelId}")
        Levelss getLevelss(@PathVariable Long levelId) {
            return levelssService.getLevelById(levelId);
        }
  



    @PostMapping("/addLevels")
    public ResponseEntity<CreateResponse> addLevelss (@RequestBody Levelss levelss) {
        levelssService.addLevelss(levelss);
        CreateResponse response = new CreateResponse("Success","Levelss created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    
    @PutMapping("/edit/{levelId}")
    Levelss editLevelss(@RequestBody Levelss tempLevelss, @PathVariable Long levelId) {
        Levelss levelss = this.levelssService.getLevelById(levelId);
        levelss.setLevelName(tempLevelss.getLevelName());
        levelss.setDescription(tempLevelss.getDescription());
        levelss.setMaxValue(tempLevelss.getMaxValue());
        levelss.setMinValue(tempLevelss.getMinValue());
        levelss.setIcon(tempLevelss.getIcon());
        levelss.setColour(tempLevelss.getColour());
        return levelssService.editLevelss(levelss);
    }

  

}
