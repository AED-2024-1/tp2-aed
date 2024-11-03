package aed.Nodos;

public class NodoCiudad{
    private int id;
    private int ganancia;
    private int perdida;
    private int superhabit;
    private int handle_superhabit;

    public NodoCiudad(int num){
        id = num;
        ganancia = 0;
        perdida = 0;
        superhabit= 0;
    }

    public int getGanancia() {
        return ganancia;
    }
    public int getPerdida() {
        return perdida;
    }
    public int getSuperhabit() {
        return superhabit;
    }
    public int getHandle_superhabit() {
        return handle_superhabit;
    }
    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }
    public void setPerdida(int perdida) {
        this.perdida = perdida;
    }
    public void setSuperhabit(int superhabit) {
        this.superhabit = superhabit;
    }
    public void setHandle_superhabit(int handle_superhabit) {
        this.handle_superhabit = handle_superhabit;
    }
}