package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.cdreader.CdReadingFacade;
import com.axokoi.bandurriaj.services.tagging.TaggingFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@Component
public class MenuBarController {

    private final CdReadingFacade cdReadingFacade;
    private final TaggingFacade taggingFacade;
    private final LoadedCdController loadedCdController;

    public MenuBarController(CdReadingFacade cdReadingFacade, TaggingFacade taggingFacade, LoadedCdController loadedCdController) {
        this.cdReadingFacade = cdReadingFacade;
        this.taggingFacade = taggingFacade;
        this.loadedCdController = loadedCdController;
    }


    public void handleReadCd(File selectedFile) {
        //todo make this works
        //todo quid if it's is a wrong path?
        String cdId = cdReadingFacade.readCdId(FileToCDPathConverter.convert(selectedFile));
        log.info("Read cdId: {}", cdId);
        //todo here we should also see if the band already exists in the repo
        List<Disc> loadedCds = taggingFacade.getDisc(cdId);
        log.info("Cd tagged was: {}", loadedCds);
        loadedCdController.refreshView(loadedCds);
    }

    static class FileToCDPathConverter {

        private FileToCDPathConverter(){}

        static String convert(File file) {
            String rawPath = file.getPath();
            //todo we need to figure it out how can we better handle this.
            //maybe take the last sr0?for linux
            if (SystemUtils.IS_OS_WINDOWS) {
                return "";
                //todo we have the spring profiles,
                // todo Maybe add a detection if profile is isolated not throw new UnsupportedOperationException("Windows not yet supported");

            } else if (SystemUtils.IS_OS_LINUX) {
                return "/dev/cdrom";
            } else {
                throw new UnsupportedOperationException("Other SO not yet supported");
            }
        }
    }
}
