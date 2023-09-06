package com.loyalty.dxvalley.services;

import java.util.List;


import com.loyalty.dxvalley.models.Levelss;


public interface LevelssService {
    
    Levelss addLevelss (Levelss levelss);
    Levelss editLevelss (Levelss levelss);
    List<Levelss> getLevels ();
    Levelss getLevelById(Long levelId);
   
    
}
