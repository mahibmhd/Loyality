package com.loyalty.dxvalley.services;

import java.util.List;

import com.loyalty.dxvalley.models.Settings;

public interface SettingsService {
    Settings addSettings (Settings settings);
    Settings editSettings (Settings settings);
    List<Settings> getSettings();
    Settings getSettingsById(Long settingId);

}
