package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.model.Track;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
		disc.getArtists();
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

//IRO Make test for this.
	public List<Disc> findAllDiscByArtist(Artist artist) {
		List<Disc> allDiscs = IterableUtils.toList(discRepository.findAll());
		List<Disc> mainArtist = allDiscs.stream()
				.filter(disc -> disc.getArtists().stream().map(Artist::getMbIdentifier).collect(Collectors.toList()).contains(artist.getMbIdentifier()))
				.collect(Collectors.toList());

		List<Disc> secondaryArtist = allDiscs.stream().filter(
				disc -> disc.getArtists().stream().flatMap(x -> x.getComposingArtists().stream())
						.map(Artist::getMbIdentifier).collect(Collectors.toList()).contains(artist.getMbIdentifier())
		).collect(Collectors.toList());

		List<Disc> results = new ArrayList<>();
		results.addAll(mainArtist);
		results.addAll(secondaryArtist);
		return results;
	}


	public void save(Disc disc){
		discRepository.save(disc);
	}

	public Optional<Disc> findByNameIgnoreCase(String name) {
		return discRepository.findByNameIgnoreCase(name);
	}
}
