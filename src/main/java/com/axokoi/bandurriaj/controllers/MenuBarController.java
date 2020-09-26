package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.model.BandRepository;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.services.cdreader.CdReadingFacade;
import com.axokoi.bandurriaj.services.tagging.TaggingFacade;
import com.axokoi.bandurriaj.views.MenuBarView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class MenuBarController {

    private final MenuBarView menuBarView;
    private final CdReadingFacade cdReadingFacade;
    private final TaggingFacade taggingFacade;
    private final DiscController discController;
    private final DiscRepository discRepository;
    private final BandRepository bandRepository;

    public MenuBarController(MenuBarView menuBarView, CdReadingFacade cdReadingFacade, TaggingFacade taggingFacade, DiscController discController, DiscRepository discRepository, BandRepository bandRepository) {
        this.menuBarView = menuBarView;
        this.cdReadingFacade = cdReadingFacade;
        this.taggingFacade = taggingFacade;
        this.discController = discController;
        this.discRepository = discRepository;
        this.bandRepository = bandRepository;
    }


    public void handleReadCd(File selectedFile) {
        //todo make this works
        //todo quid if it's is a wrong path?
        String cdId = cdReadingFacade.readCdId(FileToCDPathConverter.convert(selectedFile));
        log.info("Read cdId: {}", cdId);
        //todo here we should also see if the band already exists in the repo
        Disc disc = taggingFacade.getDisc(cdId);
        log.info("Cd tagged was: {}", disc);
        bandRepository.save(disc.getBand());
        discRepository.save(disc);
        //todo display select one from the list
        discController.displayViewCenter(disc);
    }
    ///run/user/1000/gvfs/cdda:host=sr0
    static class FileToCDPathConverter{

        static String convert(File file){
            String rawPath = file.getPath();
            //todo we need to figure it out how can we better handle this.
            //maybe take the last sr0?for linux
            if(SystemUtils.IS_OS_WINDOWS){
                throw new UnsupportedOperationException("Windows not yet supported");

            }
            else if(SystemUtils.IS_OS_LINUX){
                return "/dev/cdrom";
            }
            else{
                throw new UnsupportedOperationException("Other SO not yet supported");
            }
        }
    }
}
