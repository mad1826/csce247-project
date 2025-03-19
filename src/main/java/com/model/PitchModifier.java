package com.model;

/**
 * Represent's a modifier for a pitch
 * @author Michael Davis
 */
public enum PitchModifier {
	NONE("None", ""),
	SHARP("Sharp", "#"),
	FLAT("Flat", "b");

	public final String name;
	public final String abbreviatedName;

    private PitchModifier(String name,String abbreviatedName) {
        this.name = name;
        this.abbreviatedName = abbreviatedName;
    }
}