package aed.nodos;

public class Ciudad {
    int _id;
    int _ganancia;
    int _perdida;
    int _superavit;

    public Ciudad(int num){
        _id = num;
        _ganancia = 0;
        _perdida = 0;
        _superavit = 0;
    }

    public void setPerdida(int perdida) {
        this._perdida = perdida;
    }
    public void setGanancia(int ganancia) {
        this._ganancia = ganancia;
    }
    public void setSuperavit(int superhabit) {
        this._superavit = superhabit;
    }

    public int getId() {
        return _id;
    }
    public int getGanancia() {
        return _ganancia;
    }
    public int getPerdida() {
        return _perdida;
    }
    public int getSuperavit() {
        return _superavit;
    }
}
