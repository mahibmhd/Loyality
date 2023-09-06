package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.Levelss;
import com.loyalty.dxvalley.repositories.LevelssRepository;
import com.loyalty.dxvalley.services.LevelssService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class LevelssServiceImpl implements LevelssService {
@Autowired
private final LevelssRepository levelsRepository;

    @Override
    public List<Levelss> getLevels() {
       return levelsRepository.findAll();
    }

    @Override
    public Levelss addLevelss(Levelss levelss) {
      return levelsRepository.save(levelss);
    }

    @Override
    public Levelss editLevelss(Levelss levelss) {
       return levelsRepository.save(levelss);
    }

    @Override
    public Levelss getLevelById(Long levelId) { 
        return levelsRepository.findByLevelId(levelId);
    }
    
}
