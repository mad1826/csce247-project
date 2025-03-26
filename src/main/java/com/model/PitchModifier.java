package com.model;

/**
 * Represent's a modifier for a pitch
 * @author Michael Davis
 */
public enum PitchModifier {
	NONE("None", ""),
	SHARP("Sharp", "#"),
	FLAT("Flat", "b");

	/**
	 * The pitch's name
	 */
	public final String name;
	/**
	 * The pitch's abbreviated name
	 */
	public final String abbreviatedName;

	/**
	 * Creates a new PitchModifier instance
	 * @param name the modifier's name
	 * @param abbreviatedName the modifier's abbreviation
	 */
    private PitchModifier(String name,String abbreviatedName) {
        this.name = name;
        this.abbreviatedName = abbreviatedName;
    }
}