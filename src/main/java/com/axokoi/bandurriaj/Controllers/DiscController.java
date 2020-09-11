package com.axokoi.bandurriaj.Controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.services.dataaccess.BandService;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import com.axokoi.bandurriaj.views.DiscView;

@Component
public class DiscController {

	@Autowired
	private  DiscView discView;
	@Autowired
	private  DiscService discService;
	@Autowired
	private  BandService bandService;



	@Transactional
	public void refreshView(Disc disc) {
		Disc discToDisplay = discService.findById(disc.getId());
		discView.refresh(discToDisplay);
	}

	public Disc fetchDiscToDisplay(Disc discToDisplay) {
		return discService.findById(discToDisplay.getId());
	}

	public Band fetchBandToDisplay(Band band) {
		return bandService.findById(band.getId());
	}
}
