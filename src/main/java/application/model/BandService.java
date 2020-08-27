package application.model;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class BandService {
	private static BandRepository bandRepository;

	public BandService(BandRepository bandRepository) {
		this.bandRepository = bandRepository;
	}

	//todo these services can be probably be removed, to decide
	public static Optional<Band> findById(Long id) {
		return bandRepository.findById(id);
	}
}
