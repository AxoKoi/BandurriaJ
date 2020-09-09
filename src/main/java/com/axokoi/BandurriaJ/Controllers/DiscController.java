package com.axokoi.BandurriaJ.Controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.services.dataaccess.BandService;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.services.dataaccess.DiscService;
import com.axokoi.BandurriaJ.views.DiscView;

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
