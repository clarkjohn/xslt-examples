package com.clarkjohn.xslt;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Objects;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

/**
 * Basic Java xslt transformer example
 */
public class XSLTTransformer {

	private XSLTTransformer() {
	}
	
	public static void main(final String[] args) throws IOException, URISyntaxException, TransformerException {

		System.out.println(transform("soap_extract_body/input.txt", "soap_extract_body/transform.xslt"));
	}

	public static String transform(final String inputFilePath, final String xsltFilePath) throws IOException, TransformerException {

		Objects.requireNonNull(inputFilePath);
		Objects.requireNonNull(xsltFilePath);

		String input = toString(inputFilePath);
		// System.out.println("input=" + input);

		String xslt = toString(xsltFilePath);
		// System.out.println("xslt=" + xslt);

		StringWriter stringWriter = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new StringReader(xslt)));
		transformer.transform(new StreamSource(new StringReader(input)), new StreamResult(stringWriter));

		return stringWriter.toString();
	}

	public static String toString(final String filePath) throws IOException {
		return CharStreams.toString(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath), Charsets.UTF_8));
	}
}
