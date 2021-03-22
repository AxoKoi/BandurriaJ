package com.axokoi.bandurriaj.services.dataaccess;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.model.Track;

@Component
public class DiscService implements SmartSearchService<Disc> {

	private final DiscRepository discRepository;
	private final ArtistService artistService;

	public DiscService(DiscRepository discRepository, ArtistService artistService) {
		this.discRepository = discRepository;
		this.artistService = artistService;
	}

	@Transactional
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

	@Transactional
	public Disc findCdByTrack(Track track) {
		return IterableUtils.toList(discRepository.findAll()).stream()
				.filter(x -> x.getTracks().stream().anyMatch(t -> t.getId().equals(track.getId())))
				.findAny().orElseThrow();
	}

	@Transactional
	public List<Disc> findAllDiscByArtist(Artist artist) {

		if(artist.getType() == Artist.Type.GROUP){
			return artistService.findById(artist.getId()).getDiscs();
		}
		if(artist.getType()==Artist.Type.SINGLE){
			//Find disc where the artist is principal artist
			List<Disc> discs = artistService.findById(artist.getId()).getDiscs();

			//IRO maybe add artistservice find all groups
			List<Disc> participatedDisc = artistService.findAll().stream().filter(a -> a.getType() == Artist.Type.GROUP)
					.filter(a -> a.getComposingArtists()
							.stream()
							.map(Artist::getMbIdentifier)
							.collect(Collectors.toList())
							.contains(artist.getMbIdentifier())).flatMap(x -> x.getDiscs().stream()).collect(Collectors.toList());
		discs.addAll(participatedDisc);
		return discs;
		}
		return Collections.emptyList();
	}
}
