package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
      
    Settings findBySettingId(Long settingId);

}
