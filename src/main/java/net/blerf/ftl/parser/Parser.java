package net.blerf.ftl.parser;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Parser {

	private final byte[] intbuf = new byte[4];

	/**
	 * Reads a little-endian int from a stream, as a boolean.
	 */
	protected boolean readBool(InputStream in) throws IOException {
		int i = readInt(in);

		if(!(i==1 || i==0))
			throw new RuntimeException("Not a bool: "+ i);

		return i == 1;
	}

	/**
	 * Reads a little-endian int from a stream.
	 */
	protected int readInt(InputStream in) throws IOException {
		int numRead;
		int offset = 0;
		while (offset < intbuf.length && (numRead = in.read(intbuf, offset, intbuf.length)) >= 0)
			offset += numRead;

		if (offset < intbuf.length)
			throw new RuntimeException("End of stream reached before reading enough bytes for an int");

		int v = 0;
		for (int i = 0; i < intbuf.length; i++) {
			v |= (((int)intbuf[i]) & 0xff) << (i*8);
		}

		return v;
	}

	/**
	 * Reads a little-endian int length + ascii string from a stream.
	 */
	protected String readString(InputStream in) throws IOException {
		int length = readInt(in);

		// Avoid allocating a rediculous array size.
		// But InputStreams don't universally track position/size.
		// And available() might only mean blocking, not the end.
		// So try some special cases...
		if (in instanceof FileInputStream) {
			FileInputStream fin = (FileInputStream)in;
			long position = fin.getChannel().position();
			if (position + length  > fin.getChannel().size())
				throw new RuntimeException("Expected string length ("+ length +") would extend beyond the end of the stream, from current position ("+ position +")");
		}
		else {
			// Call available on streams that really end.
			int remaining = -1;
			if (in instanceof ByteArrayInputStream) {
				remaining = ((ByteArrayInputStream)in).available();
			}
			if (remaining != -1 && length > remaining)
				throw new RuntimeException("Expected string length ("+ length +") would extend beyond the end of the stream");
		}

		int numRead;
		int offset = 0;
		byte[] strBytes = new byte[length];
		while (offset < strBytes.length && (numRead = in.read(strBytes, offset, strBytes.length)) >= 0)
			offset += numRead;

		if (offset < strBytes.length)
			throw new RuntimeException("End of stream reached before reading enough bytes for string of length "+ length);

		return new String(strBytes, "US-ASCII");
	}

}
