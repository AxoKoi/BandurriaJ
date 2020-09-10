package com.axokoi.BandurriaJ.services.tagging.musicbrainz.converter;

import com.axokoi.BandurriaJ.model.Searchable;

@FunctionalInterface
public interface Converter<T, S extends Searchable> {

	S convert(T t);
}
