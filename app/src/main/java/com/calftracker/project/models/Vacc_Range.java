package com.calftracker.project.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vacc_Range
{
	private int[] span;
	private ArrayList<Integer> spanArrayList;
	private List<Integer> spanList;

	/**
	 * @param span
	 */
	public Vacc_Range(int[] span) {
		super();
		this.span = span;
		spanArrayList = new ArrayList<Integer>();
		for(int i = 0; i < span.length; i++){
			spanArrayList.add(span[i]);
		}
	}

	/**
	 * @return the span
	 */
	@Exclude
	public int[] getSpan() {

		span = new int[spanArrayList.size()];
		for(int i = 0; i < spanArrayList.size(); i++){
			span[i] = spanArrayList.get(i);
		}

		return span;
	}

	/**
	 * @return spanArrayList for firebase use
	 */
	public ArrayList<Integer> getSpanArrayList(){ return spanArrayList; }


	/**
	 * @param span the span to set
	 */
	public void setSpan(int[] span) {

		this.span = span;
		for(int i = 0; i < span.length; i++){
			spanArrayList.set(i, span[i]);
		}
	}
}
