package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.ArtistRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ArtistService implements SmartSearchService<Artist> {

	private final ArtistRepository artistRepository;

	public ArtistService(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	public  Optional<Artist> getById(Long id) {
		return artistRepository.findById(id);
	}

	@Override
	public List<Artist> smartSearch(String inputSearch) {
		List<Artist> result = artistRepository.findByNameContainingIgnoreCase(inputSearch);
		result.addAll(artistRepository.findByCommentContainingIgnoreCase(inputSearch));
		return result;
	}

	public List<Artist> findAll(){
		return IterableUtils.toList(artistRepository.findAll());
	}

	public Artist findById(Long id) {
		return artistRepository.findById(id).orElseThrow();
	}

	public void save(Artist artist){
		artistRepository.save(artist);
	}

	public Optional<Artist> findByMbIdentifier(String mbIdentifier) {
		return artistRepository.findByMbIdentifier(mbIdentifier);
	}
}
