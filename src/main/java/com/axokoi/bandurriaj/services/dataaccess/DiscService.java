package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.model.Track;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class DiscService implements SmartSearchService<Disc> {

	private final DiscRepository discRepository;

	public DiscService(DiscRepository discRepository) {
		this.discRepository = discRepository;
	}


	public Disc findById(Long id) {
		//todo should probably replace this with a jpql query someday
		Disc disc = discRepository.findById(id).orElseThrow();
		// Fetch lazy data
		disc.getTracks().size();
		disc.getCreditedArtists();
		return disc;
	}

	@Override
	public List<Disc> smartSearch(String inputSearch) {
		List<Disc> discs = discRepository.findByNameContainingIgnoreCase(inputSearch);
		discs.addAll(discRepository.findByCommentContainingIgnoringCase(inputSearch));
		return discs;
	}


	public Disc findCdByTrack(Track track) {
		return IterableUtils.toList(discRepository.findAll()).stream()
				.filter(x -> x.getTracks().stream().anyMatch(t -> t.getId().equals(track.getId())))
				.findAny().orElseThrow();
	}


	public List<Disc> findAllDiscByArtist(Artist artist) {

		List<Disc> allDiscs = IterableUtils.toList(discRepository.findAll());
		return allDiscs.stream().
				filter(disc -> disc.getAllArtist().stream().map(Artist::getMbIdentifier).anyMatch(x -> artist.getMbIdentifier().equals(x)))
				.collect(Collectors.toList());
	}


	public void save(Disc disc){
		discRepository.save(disc);
	}

	public Optional<Disc> findByNameIgnoreCase(String name) {
		return discRepository.findByNameIgnoreCase(name);
	}

	public Optional<Disc> findByDiscId(String discId) {
		return discRepository.findByDiscId(discId);
	}

}
