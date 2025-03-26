package com.model;

/**
 * musical instruments with their properties
 * @author Ryan Smith
 */
public enum InstrumentType {
    PIANO("Piano", 0, 0, ""),
    GUITAR("Guitar", 0, 0, ""),
    VOCALS("Vocals", 0, 0, ""),
    BASS("Bass", 0, 0, ""),
    KEYBOARD("Keyboard", 0, 0, ""),
    DRUMS("Drums", 0, 0, "");

	/**
	 * The instrument's name
	 */
    private final String name;
	/**
	 * The instrument's minimum range
	 */
    private final int rangeMin;
	/**
	 * The instrument's maximum range
	 */
    private final int rangeMax;
	/**
	 * The path to the instrument's sounds
	 */
    private final String noteSoundsPath;

    /**
     * Creates a new instrument type with its properties
     *
     * @param name           display name of the instrument
     * @param rangeMin       minimum note range
     * @param rangeMax       maximum note range
     * @param noteSoundsPath path to instrument sound files
     */
    private InstrumentType(String name, int rangeMin, int rangeMax, String noteSoundsPath) {
        this.name = name;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.noteSoundsPath = noteSoundsPath;
    }

    /**
     * @return the display name of the instrument
     */
    public String getName() {
        return name;
    }

    /**
     * @return the minimum note range
     */
    public int getRangeMin() {
        return rangeMin;
    }

    /**
     * @return the maximum note range
     */
    public int getRangeMax() {
        return rangeMax;
    }

    /**
     * @return the path to instrument sound files
     */
    public String getNoteSoundsPath() {
        return noteSoundsPath;
    }
} 