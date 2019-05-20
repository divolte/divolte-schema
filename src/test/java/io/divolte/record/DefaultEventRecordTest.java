package io.divolte.record;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;


class DefaultEventRecordTest {
    private static DefaultEventRecord createMinimalRecord() {
        final Instant timestamp = Instant.parse("2018-04-13T14:45:03.000Z");
        return DefaultEventRecord.newBuilder()
                          .setDetectedCorruption(false)
                          .setDetectedDuplicate(false)
                          .setFirstInSession(true)
                          .setTimestamp(timestamp)
                          .setClientTimestamp(timestamp)
                          .setRemoteHost("localhost")
                          .build();
    }

    @Test
    void testMinimalRecord() {
        final DefaultEventRecord record = createMinimalRecord();
        assertNotNull(record);
    }

    @Test
    void testRecordEquality() {
        final DefaultEventRecord record = createMinimalRecord();
        final DefaultEventRecord equalRecord = createMinimalRecord();
        final DefaultEventRecord differentRecord =
                DefaultEventRecord.newBuilder(record).setDetectedDuplicate(true).build();
        assertNotSame(record, equalRecord);
        assertEquals(record, equalRecord);
        assertNotEquals(record, differentRecord);
    }

    @Test
    void testRecordCanBeEncoded() throws IOException {
        final DefaultEventRecord record = createMinimalRecord();
        final ByteBuffer buffer = record.toByteBuffer();
        assertNotEquals(0, buffer.limit());
    }

    @Test
    void testRecordRoundTripViaEncoding() throws IOException {
        final DefaultEventRecord initialRecord = createMinimalRecord();
        final DefaultEventRecord clonedRecord = DefaultEventRecord.fromByteBuffer(initialRecord.toByteBuffer());
        assertEquals(initialRecord, clonedRecord);
    }

    @Test
    void testRecordRoundTripViaExternal() throws IOException {
        final DefaultEventRecord initialRecord = createMinimalRecord();
        final byte[] initialRecordBytes;
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream();
             final ObjectOutput oos = new ObjectOutputStream(os)) {
            initialRecord.writeExternal(oos);
            oos.flush();
            initialRecordBytes = os.toByteArray();
        }
        final DefaultEventRecord clonedRecord;
        try (final ByteArrayInputStream is = new ByteArrayInputStream(initialRecordBytes);
             final ObjectInput ois = new ObjectInputStream(is)) {
            clonedRecord = new DefaultEventRecord();
            clonedRecord.readExternal(ois);
        }
        assertEquals(initialRecord, clonedRecord);
    }
}
