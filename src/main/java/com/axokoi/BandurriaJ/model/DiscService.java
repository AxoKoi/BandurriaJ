package com.axokoi.BandurriaJ.model;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class DiscService {

	private static DiscRepository discRepository;

	public DiscService(DiscRepository discRepository){
		this.discRepository = discRepository;
	}

	public static Optional<Disc> findById(Long id){
		return discRepository.findById(id);
	}
}
