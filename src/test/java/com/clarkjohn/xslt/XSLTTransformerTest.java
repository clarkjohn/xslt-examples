package com.clarkjohn.xslt;

import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;

import junit.framework.TestCase;

public class XSLTTransformerTest extends TestCase {

	@Test
	public void testXSLTExamples() throws Exception {

		// basic example
		// https://www.ibm.com/support/knowledgecenter/en/ssw_ibm_i_73/rzasp/rzaspxml0648.htm
		assertTrue(xsltTransformFiles("basic_ibm_example"));

		// extract body from soap example
		// https://stackoverflow.com/questions/8072467/xslt-to-get-the-xml-from-the-body-of-a-soap-message
		assertTrue(xsltTransformFiles("soap_extract_body"));
	}

	private boolean xsltTransformFiles(final String resourcesFolderName) throws Exception {

		System.out.println("Testing transformation in folder=" + resourcesFolderName);
		
		String output = XSLTTransformer.transform(resourcesFolderName + "/input.txt", resourcesFolderName + "/transform.xslt").trim();
		String expectedOutput = XSLTTransformer.toString(resourcesFolderName + "/output.txt").trim();

		Diff diff = DiffBuilder
				.compare(output)
				.withTest(expectedOutput)
				.withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
				.checkForSimilar()
				.ignoreComments()
				.ignoreWhitespace()
				.build();
		
		System.out.println("folder=" + resourcesFolderName + " has xmlunit differences=" + diff + "\n");

		return !diff.hasDifferences();
	}

}
