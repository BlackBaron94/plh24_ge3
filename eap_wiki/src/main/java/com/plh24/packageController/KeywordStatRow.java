package com.plh24.packageController;

/** Keyword + count για το Stats tab. */
public final class KeywordStatRow {
	private final String keyword;
	private final int count;

	public KeywordStatRow(String keyword, int count) {
		this.keyword = keyword;
		this.count = count;
	}

	public String keyword() { return keyword; }
	public int count() { return count; }
}
