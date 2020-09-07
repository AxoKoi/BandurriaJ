package com.axokoi.BandurriaJ.model;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

@Component
public class DiscService implements SmartSearchService<Disc> {

	private static DiscRepository discRepository;

	public DiscService(DiscRepository discRepository) {
		this.discRepository = discRepository;
	}

	@Transactional
	public Disc findById(Long id) {
		//todo should probably replace this with a jpql query someday
		Disc disc = discRepository.findById(id).orElseThrow();
		// Fetch lazy data
		disc.getTracks();
		disc.getBand();
		return disc;
	}

	@Override
	public List<Disc> smartSearch(String inputSearch) {
		List<Disc> discs = discRepository.findByNameContainingIgnoreCase(inputSearch);
		discs.addAll(discRepository.findByCommentContainingIgnoringCase(inputSearch));
		return discs;
	}

	@Transactional
	public Disc findCdByTrack(Track track) {
		return IterableUtils.toList(discRepository.findAll()).stream()
				.filter(x -> x.getTracks().stream().anyMatch(t -> t.getId().equals(track.getId())))
				.findAny().orElseThrow();
	}
}
