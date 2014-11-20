package com.appxcraft.msquaresample.Common;

import java.util.ArrayList;

public class Entity implements java.io.Serializable, Comparable {

    public static final long serialVersionUID = 6973140709130151231L;

	public String getMyID(){
		return myID;
	}
	public void setMyID(String value){	
		this.myID = value;
	}
	
	public String getMyValue01(){
		return myValue01;
	}	
	public void setMyValue01(String value){	
		this.myValue01 = value;
	}
	
	public String getMyValue02(){
		return myValue02;
	}	
	public void setMyValue02(String value){	
		this.myValue02 = value;
	}
	
	public String getMyValue03(){
		return myValue03;
	}
	
	public void setMyValue03(String value){	
		this.myValue03 = value;
	}
	
	public String getMyValue04(){
		return myValue04;
	}
	
	public void setMyValue04(String value){	
		this.myValue04 = value;
	}
	
	public String getMyValue05(){
		return myValue05;
	}
	public void setMyValue05(String value){	
		this.myValue05 = value;
	}
	
	public String getMyValue06(){
		return myValue06;
	}
	public void setMyValue06(String value){	
		this.myValue06 = value;
	}
	
	public String getMyValue07(){
		return myValue07;
	}
	public void setMyValue07(String value){	
		this.myValue07 = value;
	}
	
	public String getMyValue08(){
		return myValue08;
	}
	public void setMyValue08(String value){	
		this.myValue08 = value;
	}
	
	public String getMyValue09(){
		return myValue09;
	}
	public void setMyValue09(String value){	
		this.myValue09 = value;
	}
	
	public String getMyValue10(){
		return myValue10;
	}
	public void setMyValue10(String value){	
		this.myValue10 = value;
	}
	public String getMyValue11(){
		return myValue11;
	}
	public void setMyValue11(String value){	
		this.myValue11 = value;
	}
	
	public String getMyValue12(){
		return myValue12;
	}
	public void setMyValue12(String value){	
		this.myValue12 = value;
	}
	
	public String getMyPhotoPath1(){
		return myPhotoPath1;
	}
	public void setMyPhotoPath1(String value){	
		this.myPhotoPath1 = value;
	}
	
	public String getMyPhotoPath2(){
		return myPhotoPath2;
	}
	public void setMyPhotoPath2(String value){	
		this.myPhotoPath2 = value;
	}

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getMyValue14() {
        return myValue14;
    }

    public void setMyValue14(String myValue14) {
        this.myValue14 = myValue14;
    }

    public String getMyValue15() {
        return myValue15;
    }

    public void setMyValue15(String myValue15) {
        this.myValue15 = myValue15;
    }

    public String getMyValue16() {
        return myValue16;
    }

    public void setMyValue16(String myValue16) {
        this.myValue16 = myValue16;
    }

    public String getMyValue17() {
        return myValue17;
    }

    public void setMyValue17(String myValue17) {
        this.myValue17 = myValue17;
    }

    private String myID;
	private String myValue01;
	private String myValue02;
	private String myValue03;
	private String myValue04;
	private String myValue05;
	private String myValue06;
	private String myValue07;
	private String myValue08;
	private String myValue09;
	private String myValue10;
	private String myValue11;
	private String myValue12;

    private double accuracy;
    private String myValue14;
    private String myValue15;
    private String myValue16;
    private String myValue17;

	
	private String myPhotoPath1;	
	private String myPhotoPath2;

    public ArrayList<Entity> tags;

    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && object instanceof Entity)
        {
            isEqual = this.myValue01.equals (((Entity) object).getMyValue01()) &&
                    this.myValue02.equals( ((Entity) object).getMyValue02())&&
                    this.myValue03.equals(((Entity) object).getMyValue03());
        }

        return isEqual;
    }

    public int compareTo(Object o1) {

        //Sorting logic
        if(this.accuracy >((Entity) o1).getAccuracy() )
            return 1;
        else if(this.accuracy < ((Entity) o1).getAccuracy())
            return -1;
        else
            return 0;

    }
}
