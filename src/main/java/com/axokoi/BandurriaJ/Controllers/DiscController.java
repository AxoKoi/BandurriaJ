package com.axokoi.BandurriaJ.Controllers;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.model.DiscRepository;
import com.axokoi.BandurriaJ.views.DiscView;

@Component
public class DiscController {

	private final DiscRepository discRepository;

	private final DiscView discView;

	public DiscController(DiscRepository discRepository, DiscView discView) {
		this.discRepository = discRepository;
		this.discView = discView;
	}

	@Transactional
	public void refreshView(Disc disc) {
		Disc discToDisplay = discRepository.findById(disc.getId()).get();
		discView.refresh(discToDisplay);
	}

	public void refreshViewWithName(String discName) {
		Disc discToDisplay = discRepository.findByName(discName);
		if (discToDisplay != null) {
			discView.refresh(discToDisplay);
		}
	}
}
