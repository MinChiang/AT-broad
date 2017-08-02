package com.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

public class JAXBUtil {
	private JAXBContext jaxbContext;

	public static class CollectionWrapper {
		@XmlAnyElement(lax = true)
		protected Collection<?> collection;
	}

	public JAXBUtil(Class<?>... types) {
		try {
			Class<?>[] classes = new Class<?>[types.length + 1];
			System.arraycopy(types, 0, classes, 0, types.length);
			classes[types.length] = CollectionWrapper.class;
			jaxbContext = JAXBContext.newInstance(classes);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public Marshaller createMarshaller(String encoding) {
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			if (encoding != null && !"".equals(encoding)) {
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			}
			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public Unmarshaller createUnmarshaller() {
		try {
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public String toXml(Object root) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller("UTF-8").marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public String toXml(Collection<?> root, String rootName, String encoding) {
		try {
			CollectionWrapper wrapper = new CollectionWrapper();
			wrapper.collection = root;
			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
					CollectionWrapper.class, wrapper);
			StringWriter writer = new StringWriter();
			createMarshaller(encoding).marshal(wrapperElement, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T toObject(String xml) {
		try {
			StringReader reader = new StringReader(xml);
			return (T) createUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public Collection toCollection(String xml) {
		try {
			StreamSource ss = new StreamSource(new StringReader(xml));
			CollectionWrapper collectionWrapper = ((CollectionWrapper) createUnmarshaller()
					.unmarshal(ss, CollectionWrapper.class).getValue());
			return collectionWrapper == null ? null : collectionWrapper.collection;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
