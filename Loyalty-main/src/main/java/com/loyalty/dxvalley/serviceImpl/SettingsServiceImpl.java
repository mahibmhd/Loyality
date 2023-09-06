package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.Settings;
import com.loyalty.dxvalley.repositories.SettingsRepository;
import com.loyalty.dxvalley.services.SettingsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    @Autowired
    private final SettingsRepository settingsRepository;
    @Override
    public Settings addSettings(Settings settings) {
        return settingsRepository.save(settings);
    }

    @Override
    public Settings editSettings(Settings settings) {
        return settingsRepository.save(settings);
    }

    @Override
    public List<Settings> getSettings() {
        return settingsRepository.findAll();
    }

    @Override
    public Settings getSettingsById(Long settingId) {
        return settingsRepository.findBySettingId(settingId);
    }
    
}
