package com.hamzah.onehandmode;

public class Preset {

	private int id;
	private double left;
	private double right;
	private double top;
	private double bottom;
	
	public Preset(int id, double left, double right, double top, double bottom){
		this.id = id;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	public int getId(){
		return id;
	}
	
	public double getLeft(){
		return left;
	}
	
	public double getRight(){
		return right;
	}
	
	public double getTop(){
		return top;
	}
	
	public double getBottom(){
		return bottom;
	}

}
