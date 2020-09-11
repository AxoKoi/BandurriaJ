package com.axokoi.bandurriaj.cd;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class CdReader {
	public String parseToString() {
		Tika tika = new Tika();

		try (InputStream stream = CdReader.class.getResourceAsStream("test.doc")) {
			return tika.parseToString(stream);
		} catch (TikaException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public Metadata parseToStringUsingParser() {

		try (InputStream stream = CdReader.class.getResourceAsStream("test.doc")) {
			AudioParser parser = new AudioParser();
			Metadata metadata = new Metadata();
			ContentHandler contentHandler = new ToXMLContentHandler();

			ParseContext context = new ParseContext();
			parser.parse(stream, contentHandler, metadata, context);
			return metadata;
		} catch (TikaException | SAXException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
