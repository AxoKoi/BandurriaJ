package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.Searchable;

@FunctionalInterface
public interface Converter<T, S extends Searchable> {

	S convert(T t);
}
