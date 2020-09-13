package com.axokoi.bandurriaj.services.dataaccess;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.BandRepository;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.model.Track;

@Component
public class DiscService implements SmartSearchService<Disc> {

	private DiscRepository discRepository;

	private BandRepository bandRepository;

	public DiscService(DiscRepository discRepository, BandRepository bandRepository) {
		this.discRepository = discRepository;
		this.bandRepository = bandRepository;
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

	@Transactional
	public List<Disc> findAllDiscByArtist(Artist artist) {

		return IterableUtils.toList(bandRepository.findAll()).stream()
				.filter(band -> band.getArtists().stream().map(Artist::getId).anyMatch(x -> x.equals(artist.getId())))
				.map(Band::getDiscs)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
}
