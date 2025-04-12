package io.cdap.wrangler.api.parser;

import java.util.HashMap;
import java.util.Map;

public class ByteSize extends Token {

    private static final Map<String, Long> UNIT_MULTIPLIERS = new HashMap<>();

    static {
        UNIT_MULTIPLIERS.put("B", 1L);
        UNIT_MULTIPLIERS.put("KB", 1024L);
        UNIT_MULTIPLIERS.put("MB", 1024L * 1024);
        UNIT_MULTIPLIERS.put("GB", 1024L * 1024 * 1024);
        UNIT_MULTIPLIERS.put("TB", 1024L * 1024 * 1024 * 1024);
    }

    private final long bytes;

    public ByteSize(String input) {
        super(Type.BYTE_SIZE, input);
        input = input.trim().toUpperCase();

        // Regex to separate number and unit
        String numberPart = input.replaceAll("[^0-9.]", "");
        String unitPart = input.replaceAll("[0-9.]", "");

        if (!UNIT_MULTIPLIERS.containsKey(unitPart)) {
            throw new IllegalArgumentException("Invalid byte unit: " + unitPart);
        }

        double value = Double.parseDouble(numberPart);
        this.bytes = (long) (value * UNIT_MULTIPLIERS.get(unitPart));
    }

    public long getBytes() {
        return bytes;
    }
}
