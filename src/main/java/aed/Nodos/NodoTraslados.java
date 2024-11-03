package aed.Nodos;

import aed.Traslado;

public class NodoTraslados{
    private Traslado InfoTraslado;
    private int Handle_Redituble;
    private int Handle_Antiguedad;

    public NodoTraslados(Traslado obj){
        this.InfoTraslado = obj;
    }

    public void setRedituable(int handle){
        this.Handle_Redituble = handle;

    }
    public void setAntiguedad(int handle){
        this.Handle_Antiguedad = handle;   
    }

    public int getAntiguedad(){
        return this.Handle_Antiguedad;
    }

    public int getRedituble(){
        return this.Handle_Redituble;
    }

    public Traslado getInfoTraslado(){
        return this.InfoTraslado;
    }
}
