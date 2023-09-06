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
import com.loyalty.dxvalley.models.Settings;
import com.loyalty.dxvalley.services.SettingsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {
    @Autowired
    private final SettingsService settingsService;

    @PostMapping("/addSettings")
     public ResponseEntity<CreateResponse> addSettings (@RequestBody Settings settings) {
        settingsService.addSettings(settings);
        CreateResponse response = new CreateResponse("Success","Settings created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 

    @GetMapping("/getSettings")
    List<Settings> getSettings() {
        return this.settingsService.getSettings();
    }


    @PutMapping("edit/{settingId}")
    Settings editSettings(@RequestBody Settings tempSettings, @PathVariable Long settingId) {
        Settings settings = this.settingsService.getSettingsById(settingId);
        settings.setExchangeRate(tempSettings.getExchangeRate()); 
        settings.setWithdrawalLimit(tempSettings.getWithdrawalLimit());
        return settingsService.editSettings(settings);
    }


    
    
}
