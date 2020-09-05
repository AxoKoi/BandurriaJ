package com.axokoi.BandurriaJ.model;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class DiscService {

	private static DiscRepository discRepository;

	public DiscService(DiscRepository discRepository) {
		this.discRepository = discRepository;
	}

	@Transactional
	public Disc findById(Long id) {
		//todo should probably replace this with a jpql query someday
		Disc disc = discRepository.findById(id).orElseThrow();
		// Fetch lazy data
		disc.getTracks().size();
		disc.getBand();
		return disc;
	}
}
