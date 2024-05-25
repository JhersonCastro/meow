package com.versalles.emrms.Exceptions;

public class BlankJTextField extends  Exception{
    private int index;
    public BlankJTextField(int index){
        super("Espacio en blanco");
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
