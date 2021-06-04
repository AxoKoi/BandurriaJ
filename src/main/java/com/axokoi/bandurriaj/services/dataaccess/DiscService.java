package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.*;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class DiscService implements SmartSearchService<Disc> {

	private final TrackRepository trackRepository;
	private final DiscRepository discRepository;
	private final CatalogueService catalogueService;

	public DiscService(TrackRepository trackRepository, DiscRepository discRepository, CatalogueService catalogueService) {
		this.trackRepository = trackRepository;
		this.discRepository = discRepository;
		this.catalogueService = catalogueService;
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

	public Optional<Disc> findByBusinessIdentifier(String businessIdentifier) {
		return discRepository.findByBusinessIdentifier(businessIdentifier);

	}

	public Optional<Disc> findByExternalIdentifier(ExternalIdentifier externalIdentifier) {
		return IterableUtils.toList(discRepository.findAll()).stream()
				.filter(x -> x.getExternalIdentifiers().stream()
						.anyMatch(y -> y.getType() == externalIdentifier.getType() && y.getIdentifier().equals(externalIdentifier.getIdentifier())))
				.findFirst();
	}

	@Transactional
	public void deleteDisc(Disc entityToDelete) {
		catalogueService.deleteDiscFromCatalogues(entityToDelete);
		discRepository.delete(entityToDelete);
	}

	@Transactional
	public Disc deleteTrackFromCD(Disc disc, Track track) {
		disc.getTracks().remove(track);
		trackRepository.delete(track);
		return discRepository.save(disc);

	}

	public Disc addTrackToCd(Disc disc, Track track) {
		disc.getTracks().add(track);
		return discRepository.save(disc);
	}
}
